package kr.hs.dgsw.hatomuapp.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import kr.hs.dgsw.hatomuapp.R;
import kr.hs.dgsw.hatomuapp.enums.LoginMode;

public class TwitchLoginActivity extends AppCompatActivity {

    private WebView webView;

    private final String LOGIN_URL = "http://hatomu.kro.kr:3000/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitch_login);

        webView = findViewById(R.id.webview_login);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(TwitchLoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.Android.getHtml(location.pathname, document.getElementsByTagName('pre')[0].innerHTML);");
            }
        });

        webView.getSettings().setJavaScriptEnabled(true); //Javascript를 사용하도록 설정

        webView.addJavascriptInterface(new MyJavascriptInterface((path, content) -> {
            if ("/user/redirect".equals(path)) {
                Intent i = new Intent();
                i.putExtra("response", content);
                setResult(RESULT_OK, i);
                finish();
            }
        }), "Android");

        webView.loadUrl(LOGIN_URL);
    }

    public class MyJavascriptInterface {

        private OnContentLoadListener onContentLoadListener;

        public MyJavascriptInterface() {
        }

        public MyJavascriptInterface(OnContentLoadListener olcl) {
            this.onContentLoadListener = olcl;
        }

        public MyJavascriptInterface setOnContentLoadListener(OnContentLoadListener olcl) {
            this.onContentLoadListener = olcl;
            return this;
        }

        @JavascriptInterface
        public void getHtml(String path, String html) { //위 자바스크립트가 호출되면 여기로 html이 반환됨
            onContentLoadListener.OnContentLoad(path, html);
        }
    }

}

// MyJavascriptInterface과 Activity를 연결하기 위한 리스너
interface OnContentLoadListener {
    void OnContentLoad(String path, String content);
}
