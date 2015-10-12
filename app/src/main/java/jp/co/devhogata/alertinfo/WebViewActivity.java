package jp.co.devhogata.alertinfo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // WebView読み込み
        WebView webview = (WebView)findViewById(R.id.webView);

        // WebViewClientの設定
        webview.setWebViewClient(new WebViewClient() {
            // 新しいURLが指定されたときの処理を定義
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            // ページ読み込み開始時の処理
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i(TAG, "WebView読み込み開始");
            }

            // ページ読み込み完了時の処理
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "WebView読み込み完了");
            }

            // ページ読み込みエラー時の処理
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String url) {
                Log.i(TAG, "WebView通信エラー");
            }
        });

        // load
        webview.loadUrl("http://goo.gl/forms/BZczkBawYM");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューの要素を追加して取得
        MenuItem actionItem = menu.add("Action Button Help Icon");
        // アイコンを設定
        actionItem.setIcon(R.drawable.ic_close_white_24dp);

        // SHOW_AS_ACTION_ALWAYS:常に表示
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }
}
