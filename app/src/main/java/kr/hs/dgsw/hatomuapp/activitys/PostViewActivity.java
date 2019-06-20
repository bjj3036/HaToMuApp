package kr.hs.dgsw.hatomuapp.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.hatomuapp.R;
import kr.hs.dgsw.hatomuapp.adapters.PostAdapter;
import kr.hs.dgsw.hatomuapp.adapters.PostImageAdapter;
import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.beans.ResponseBean;
import kr.hs.dgsw.hatomuapp.beans.UserBean;
import kr.hs.dgsw.hatomuapp.databinding.ActivityPostViewBinding;
import kr.hs.dgsw.hatomuapp.databinding.ItemPostBinding;
import kr.hs.dgsw.hatomuapp.network.HatomuService;
import kr.hs.dgsw.hatomuapp.network.ServiceControl;
import kr.hs.dgsw.hatomuapp.setting.HatomuTheme;
import kr.hs.dgsw.hatomuapp.viewmodels.PostViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewActivity extends AppCompatActivity implements PostAdapter.PostEventListener {

    public static final String EXTRA_POST_ID = "postId";

    private ActivityPostViewBinding binding;

    private PostImageAdapter adapter;

    private HatomuService service;

    private UserBean loginUser;
    private String token;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HatomuTheme.applyTheme(this);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_post_view);
        this.binding.includePost.setPostEvent(this);

        setSupportActionBar(this.binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.loginUser = UserBean.getInstance();
        this.token = this.loginUser.getToken();
        this.postId = getIntent().getStringExtra(EXTRA_POST_ID);

        this.service = ServiceControl.getHatomuService();

        service.getPost(postId, token)
                .subscribeOn(Schedulers.io())
                .filter(r -> r.getStatus())
                .map(r -> r.getData())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> {
                    initViewPager(new PostViewModel(p));
                });
    }

    private void initViewPager(PostViewModel postBean) {
        binding.includePost.setPost(postBean);
        adapter = new PostImageAdapter(getSupportFragmentManager());
        adapter.setList(postBean.images.get());
        TabLayout tabLayout = binding.includePost.tabLayout;
        binding.includePost.imagePager.setAdapter(adapter);
        tabLayout.removeAllTabs();
        if (postBean.images.get().size() != 1) {
            for (int i = 0; i < postBean.images.get().size(); i++) {
                tabLayout.addTab(tabLayout.newTab());
            }
            tabLayout.setVisibility(View.VISIBLE);
        } else {
            tabLayout.setVisibility(View.GONE);
        }

        ViewGroup tabs = (ViewGroup) tabLayout.getChildAt(0);
        for (int i = 0; i < tabs.getChildCount() - 1; i++) {
            View tab = tabs.getChildAt(i);
            //터치 막기
            tab.setOnTouchListener((v, event) -> false);
            //tab들 사이에 margin 설정
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
            layoutParams.setMarginEnd(4);
            tab.setLayoutParams(layoutParams);
            tabLayout.requestLayout();
        }
        binding.includePost.imagePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
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
                    if (v instanceof CompoundButton) {
                        ((CompoundButton) v).setChecked(r.getData());
                        Toast.makeText(this, r.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void OnTwitchClick(View v, PostViewModel post) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(post.writer.get().getUrl()));
        startActivity(i);
    }
}
