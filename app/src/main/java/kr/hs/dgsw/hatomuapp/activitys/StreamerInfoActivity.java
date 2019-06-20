package kr.hs.dgsw.hatomuapp.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.hatomuapp.R;
import kr.hs.dgsw.hatomuapp.adapters.PostPreviewAdapter;
import kr.hs.dgsw.hatomuapp.beans.PostBean;
import kr.hs.dgsw.hatomuapp.beans.ResponseBean;
import kr.hs.dgsw.hatomuapp.databinding.ActivityStreamerInfoBinding;
import kr.hs.dgsw.hatomuapp.network.HatomuService;
import kr.hs.dgsw.hatomuapp.network.ServiceControl;
import kr.hs.dgsw.hatomuapp.setting.HatomuTheme;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StreamerInfoActivity extends AppCompatActivity implements PostPreviewAdapter.OnPreviewClickListener {

    public static final String EXTRA_CHANNEL_ID = "chnnelId";

    private ActivityStreamerInfoBinding binding;

    private HatomuService service;

    private String channelId;

    private PostPreviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HatomuTheme.applyTheme(this);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_streamer_info);
        this.service = ServiceControl.getHatomuService();
        this.channelId = getIntent().getStringExtra(EXTRA_CHANNEL_ID);

        adapter = new PostPreviewAdapter(this);

        service.getUserInfoById(this.channelId)
                .subscribeOn(Schedulers.io())
                .filter(r->r.getStatus())
                .map(r->r.getData())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    StreamerInfoActivity.this.binding.setUser(s);
                    adapter.setPosts(s.getPosts());
                });

        setSupportActionBar(this.binding.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.binding.rvPosts.setLayoutManager(new GridLayoutManager(this, 3));
        this.binding.rvPosts.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnPreviewClick(View v, PostBean postBean) {
        Intent i = new Intent(this, PostViewActivity.class);
        i.putExtra(PostViewActivity.EXTRA_POST_ID, postBean.get_id());
        startActivity(i);
    }
}
