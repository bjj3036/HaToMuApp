package kr.hs.dgsw.hatomuapp.activitys;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebViewClient;

import kr.hs.dgsw.hatomuapp.R;
import kr.hs.dgsw.hatomuapp.databinding.ActivityTwitchLogoutBinding;

public class TwitchLogoutActivity extends AppCompatActivity {

    private ActivityTwitchLogoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_twitch_logout);
        binding.webviewLogout.setWebViewClient(new WebViewClient());
        binding.webviewLogout.loadUrl("https://www.twitch.tv/?no-mobile-redirect=true&tt_content=channel_nav&tt_medium=twitch_mobileweb");
    }
}
