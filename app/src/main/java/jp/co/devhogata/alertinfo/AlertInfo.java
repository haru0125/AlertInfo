package jp.co.devhogata.alertinfo;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class AlertInfo extends AppCompatActivity {
    private static final String TAG = "activity_alert_info";

    // クラス変数
    private ActionBarDrawerToggle mDrawerToggle = null;
    private DrawerLayout mDrawerLayout = null;
    private SeekBar seekBar;
    private TextView txtView;

    /**
     * アクティビティ作成時
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_info);

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
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                    }
                }
        );


        // アプリケーションアイコン制御有効化
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    /**
     * メニュー作成イベント。onResumeの後あたりに呼ばれる
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューをセットする
        getMenuInflater().inflate(R.menu.menu_alert_info, menu);
        return true;
    }

    /**
     * メニュー選択時のイベント
     * @param item
     * @return
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
        Toast.makeText(getApplicationContext(), "Test navimail!", Toast.LENGTH_SHORT).show();
    }

    // アクティビティ
    public void navireview(View view) {
        Toast.makeText(getApplicationContext(), "Test navireview!", Toast.LENGTH_SHORT).show();
    }

    // アクティビティ
    public void naviappli(View view) {
        Toast.makeText(getApplicationContext(), "Test naviappli!", Toast.LENGTH_SHORT).show();
    }

    /**
     * ここからクラス処理用のメソッド
     */
    private String timeDispChg(int progress) {
        int hour = progress / 60;
        int min = progress % 60;
        return hour + "時間" + min + "分";
    }

}
