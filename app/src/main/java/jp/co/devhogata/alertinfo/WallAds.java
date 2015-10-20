package jp.co.devhogata.alertinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jp.co.imobile.sdkads.android.ImobileSdkAd;

public class WallAds extends AppCompatActivity {
    static final String IMOBILE_WALL_PID = "44822";
    static final String IMOBILE_WALL_MID = "214330";
    static final String IMOBILE_WALL_SID = "618476";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_ads);

        // 広告を表示します
        ImobileSdkAd.showAd(this, IMOBILE_WALL_SID);
    }

    @Override
    protected void onDestroy() {
        //Activity廃棄時の後処理
        ImobileSdkAd.activityDestory();
        super.onDestroy();
    }
}
