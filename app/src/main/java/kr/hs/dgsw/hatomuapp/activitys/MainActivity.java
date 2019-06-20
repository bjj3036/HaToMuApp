package kr.hs.dgsw.hatomuapp.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.rxbinding2.view.RxMenuItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.hatomuapp.R;
import kr.hs.dgsw.hatomuapp.adapters.PostAdapter;
import kr.hs.dgsw.hatomuapp.adapters.SearchAdapter;
import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.beans.ResponseBean;
import kr.hs.dgsw.hatomuapp.beans.UserBean;
import kr.hs.dgsw.hatomuapp.databinding.ActivityMainBinding;
import kr.hs.dgsw.hatomuapp.network.HatomuService;
import kr.hs.dgsw.hatomuapp.network.ServiceControl;
import kr.hs.dgsw.hatomuapp.setting.HatomuTheme;
import kr.hs.dgsw.hatomuapp.viewmodels.PostViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PostAdapter.PostEventListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "HaToMuApp";
    private static final int REQ_WRITE_POST = 1;

    private UserBean loginUser;

    private String token;

    private ActivityMainBinding mainBinding;
    //RecyclerView Adapter
    private PostAdapter postAdapter;
    private SearchAdapter searchAdapter;
    private int lastPostIndex;
    private int topPostIndex = 0;
    private HatomuService service;

    private InputMethodManager imm;

    //RecyclerView에 Databinding 할 List
    private List<PostViewModel> postBeans;

    //Search View at the Tool bar
    private SearchView mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HatomuTheme.applyTheme(this);
        loginUser = UserBean.getInstance();
        token = loginUser.getToken();
        //activity_main.xml Binding 객체
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mainBinding.includeAppbar.toolbar);

        this.imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //RecyclerView List Binding
        postBeans = new ArrayList<>();
        mainBinding.setPostList(postBeans);

        //새로고침
        mainBinding.refreshLayout.setOnRefreshListener(this);

        //서버와 통신할 Retrofit 설정
        service = ServiceControl.getHatomuService();

        postAdapter = new PostAdapter(this, getSupportFragmentManager());
        mainBinding.postRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainBinding.postRecyclerView.setAdapter(postAdapter);
        //RecyclerView 끝에 도달하면 불러오기
        mainBinding.postRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //맨끝인지 확인
                if (!recyclerView.canScrollVertically(1)) {
                    if (lastPostIndex <= 0)
                        return;
//                    service.getPostFromLastIndex(lastPostIndex, loginViewer.getUserId()).enqueue(postLoadCallBack);
                    Log.i(TAG, "The End of RecyclerView");
                }
            }
        });

        searchAdapter = new SearchAdapter((v, u) -> {
            Intent i = new Intent(this, StreamerInfoActivity.class);
            i.putExtra(StreamerInfoActivity.EXTRA_CHANNEL_ID, u.getId());
            startActivity(i);
        });
        mainBinding.rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.rcvSearch.setAdapter(searchAdapter);

        //실행하자 마자 최상단 5개 불러오기
        service.getLatestPosts(token)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(r -> {
                    Log.d(TAG, r.toString());
                    return r.getData();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postBeans -> {
                    postAdapter.addItem(postBeans);
                }, t -> {
                    Log.e(TAG, t.getMessage());
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.mSearchView = (SearchView) menu.findItem(R.id.search_menu).getActionView();
        this.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                service.searchUser(s)
                        .subscribeOn(Schedulers.io())
                        .map(r -> r.getData())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(lu -> searchAdapter.setList(lu));
                return false;
            }
        });
        this.mSearchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            mainBinding.rcvSearch.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            mainBinding.viewBackSearch.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            case R.id.posting_menu:
                startWritePostActivity();
                return true;
            case R.id.setting_menu:
                HatomuTheme.toggleTheme(this);
                recreate();
                return true;
            case R.id.search_menu:
                imm.showSoftInput(mSearchView, 0);
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnLogoClick(View v, PostViewModel post) {
        Intent i = new Intent(this, StreamerInfoActivity.class);
        i.putExtra(StreamerInfoActivity.EXTRA_CHANNEL_ID, post.writer.get().getId());
        startActivity(i);
    }

    @Override
    public void OnLikeClick(View v, PostViewModel post) {
        service.likePost(post._id.get(), token)
                .subscribeOn(Schedulers.io())
                .filter(r -> r.getStatus())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    post.isLiked.set(r.getData());
                    Toast.makeText(this, r.getMessage(), Toast.LENGTH_SHORT).show();
                    post.addLikeCnt(r.getData() ? 1 : -1);
                });
    }

    @Override
    public void OnTwitchClick(View v, PostViewModel post) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(post.writer.get().getUrl()));
        startActivity(i);
    }

    @Override
    public void onRefresh() {
        service.getTopIndex().enqueue(new Callback<ResponseBean<Integer>>() {
            @Override
            public void onResponse(Call<ResponseBean<Integer>> call, Response<ResponseBean<Integer>> response) {
                //최근 게시물의 인덱스 갖고 오기
                if (response.isSuccessful())
                    if (response.body().getData() != topPostIndex) {
                        postAdapter.clearPost();
//                        service.getLatestPosts(loginViewer.getUserId()).enqueue(postLoadCallBack);
                    }
            }

            @Override
            public void onFailure(Call<ResponseBean<Integer>> call, Throwable t) {

            }
        });
        mainBinding.refreshLayout.setRefreshing(false);
    }

    private void startWritePostActivity() {
        Intent i = new Intent(this, EditPostActivity.class);
        startActivityForResult(i, REQ_WRITE_POST);
    }
}
