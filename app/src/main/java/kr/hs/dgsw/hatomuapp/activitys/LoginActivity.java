package kr.hs.dgsw.hatomuapp.activitys;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static kr.hs.dgsw.hatomuapp.activitys.MainActivity.TAG;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.hatomuapp.R;
import kr.hs.dgsw.hatomuapp.beans.ResponseBean;
import kr.hs.dgsw.hatomuapp.beans.UserBean;
import kr.hs.dgsw.hatomuapp.databinding.ActivityLoginBinding;
import kr.hs.dgsw.hatomuapp.enums.LoginMode;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQ_LOGIN_USER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setLoginOnclick(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, TwitchLoginActivity.class);
        startActivityForResult(i, REQ_LOGIN_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String LOGIN_FAIL = "트위치 로그인에 실패하였습니다";
        if (resultCode == RESULT_OK) {
            Observable.just(data)
                    .observeOn(Schedulers.io())
                    .filter(d -> d.hasExtra("response"))
                    .map(d -> d.getStringExtra("response"))
                    .map(r -> (ResponseBean<UserBean>) new GsonBuilder().create().fromJson(r, new TypeToken<ResponseBean<UserBean>>() {
                    }.getType()))
                    .filter(rb -> rb.getStatus())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(rb -> {
                        Log.d(TAG, rb.toString());
                        UserBean.setInstance(rb.getData());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }, t -> Log.e(TAG, t.getMessage()));
        } else {
            Toast.makeText(this, LOGIN_FAIL, Toast.LENGTH_LONG).show();
        }
    }
}
