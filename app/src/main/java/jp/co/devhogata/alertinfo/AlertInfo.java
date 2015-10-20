package jp.co.devhogata.alertinfo;

import android.app.Notification;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

public class AlertInfo extends AppCompatActivity {
    private static final String TAG = "activity_alert_info";

    // クラス変数
    private ActionBarDrawerToggle mDrawerToggle = null;
    private DrawerLayout mDrawerLayout = null;
    private SeekBar seekBar;
    private TextView txtView;
    private int infohour;

    /**
     * アクティビティ作成時
     * @param savedInstanceState comment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Flurry
         */
        // configure Flurry
        FlurryAgent.setLogEnabled(false);
        // init Flurry
        FlurryAgent.init(this, "DNXMTBJ4SPG4Q2H7QD5D");

        // キーボードをクローズする
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_alert_info);

        // nend
//        NendAdView nendAdView = new NendAdView(getApplicationContext(), 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f");

        // 初期化処理
        infohour = 1;

        /**
         * ナビゲーションドロワー関連
         */
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            // ナビゲーションドロワーオープン
            @Override public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.i(TAG, "open!!");
            }
            // クローズ
            @Override public void onDrawerClosed(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.i(TAG, "close!!");
            }

        };
        // ナビゲーションドロワーのリスナー設定
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        /**
         * シークバー関連
         */
        seekBar = (SeekBar)findViewById(R.id.seek);
        txtView = (TextView)findViewById(R.id.textView);
        txtView.setText(timeDispChg(seekBar.getProgress()) + "後に通知");

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        txtView.setText(timeDispChg(progress) + "後に通知");
                        infohour = progress;
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                    }
                }
        );

        /**
         * Edittext処理
         */
        EditText et = (EditText) findViewById(R.id.mainTextView);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // EditTextのフォーカスが外れた場合
                if (hasFocus == false) {
                    // 外れたときの処理を記載する
                }
            }
        });

        // アプリケーションアイコン制御有効化
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }


    /**
     * メニュー作成イベント。onResumeの後あたりに呼ばれる
     * @param menu comment
     * @return comment
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューをセットする
        getMenuInflater().inflate(R.menu.menu_alert_info, menu);

        // メニュー追加
        MenuItem item = menu.add(Menu.NONE, Menu.FIRST, 100, "test");
        item.setIcon(R.drawable.ic_add_alert_white_24dp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    /**
     * メニュー選択時のイベント
     * @param item comment
     * @return comment
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // ActionBarDrawerToggleにandroid.id.home(up ナビゲーション)を渡す。
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case Menu.FIRST:
                Log.i(TAG, "notification add");
                EditText textview = (EditText)findViewById(R.id.mainTextView);
                NotificationUtil.setLocalNotification(getApplicationContext(), textview.getText().toString(), switchStatus(), 12345, infohour);

                // ソフトキーボードを隠す
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // Toastメッセージ表示
                Toast.makeText(this, "通知予約が完了しました", Toast.LENGTH_SHORT).show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // アクティビティとナビゲーションドロワーの状態を同期
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // デバイスの状態変化をナビゲーションドロワーに通知
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // アクティビティ
    public void navimail(View view) {
        // WebViewで起動する
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("URL", "http://goo.gl/forms/BZczkBawYM");
        startActivity(intent);
    }

    // アクティビティ
    public void navireview(View view) {
        // Webブラウザで起動する
        // TODO:URLが決まったら差し替える
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=jp.co.harupark.alertinfo&hl=ja");
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    // アクティビティ
    public void naviappli(View view) {
        Toast.makeText(getApplicationContext(), "Test naviappli!", Toast.LENGTH_SHORT).show();
    }

    /**
     * ここからクラス処理用のメソッド
     */
    private String timeDispChg(int progress) {
        return progress + "時間";
    }

    private int switchStatus() {
        int ret  = Notification.DEFAULT_LIGHTS;
        Switch sw_sound = (Switch)findViewById(R.id.switch_sound);
        if (sw_sound.isChecked()) {
            ret += Notification.DEFAULT_SOUND;
        }
        Switch sw_vib = (Switch)findViewById(R.id.switch_vibrate);
        if (sw_vib.isChecked()) {
            ret += Notification.DEFAULT_VIBRATE;
        }
        return ret;
    }
}
