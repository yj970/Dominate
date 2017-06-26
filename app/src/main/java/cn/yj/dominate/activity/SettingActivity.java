package cn.yj.dominate.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.yj.dominate.R;
import cn.yj.dominate.dataBase.dao.ConfigDao;
import cn.yj.dominate.eventBusModel.DataChangeMessage;
import cn.yj.dominate.util.Util;

/**
 * Created by yangjie on 2017/6/16.
 */

public class SettingActivity extends BaseActivity {

    private Toolbar toolbar;
    private SwitchButton sbShowSystem;
    private ConfigDao configDao;
    private Button btnInfo;
    private Button btnHandWriting;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        EventBus.getDefault().register(this);

    }

    @Override
    public void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sbShowSystem = (SwitchButton) findViewById(R.id.sb_system_show);
        btnHandWriting = (Button) findViewById(R.id.btn_hand_writing);
        btnInfo = (Button) findViewById(R.id.btn_info);
    }

    @Override
    public void bindData() {
        configureToolbar();

        // 设置是否显示系统级别APP
        configDao = new ConfigDao();
        sbShowSystem.setChecked(configDao.query().isShowSystemApps());
    }


    public void setListener() {
        sbShowSystem.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                configDao.updateIsShowSystemApps(isChecked);
                EventBus.getDefault().post(new DataChangeMessage(isChecked));
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        btnHandWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, HandWritingActivity.class));
            }
        });
    }

    private void showInfo() {
        //获取本机信息
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // 尺寸
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        // 密度
        int d = metrics.densityDpi;
        String dd = "";
        switch (d) {
            case 120:
                dd = "ldpi";
                break;
            case 160:
                dd = "mdpi";
                break;
            case 240:
                dd = "hdpi";
                break;
            case 320:
                dd = "xhdpi";
                break;
            case 400:
                dd = "xxhdpi";
                break;
            case 460:
                dd = "xxxhdpi";
                break;
        }

        // dialog
        Dialog dialog = new Dialog(this, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog);
        View vv = LayoutInflater.from(SettingActivity.this).inflate(R.layout.dialog_info, null);
        ((TextView)vv.findViewById(R.id.w)).setText(widthPixels+"");
        ((TextView)vv.findViewById(R.id.h)).setText(heightPixels+"");
        ((TextView)vv.findViewById(R.id.d)).setText(d+"("+dd+")");
        ((TextView)vv.findViewById(R.id.ver)).setText(Build.VERSION.SDK_INT+"");
        dialog.setContentView(vv);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = Util.dp2px(this, 500);
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    /**
     * 配置toolbar属性
     */
    private void configureToolbar() {
        toolbar.setTitle("设置");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        configDao.close();
    }


    @Subscribe
    public void empty(DataChangeMessage dataChangeMessage){}
}
