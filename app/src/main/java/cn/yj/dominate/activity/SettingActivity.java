package cn.yj.dominate.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.yj.dominate.R;
import cn.yj.dominate.dataBase.dao.ConfigDao;
import cn.yj.dominate.eventBusModel.DataChangeMessage;

/**
 * Created by yangjie on 2017/6/16.
 */

public class SettingActivity extends BaseActivity {

    private Toolbar toolbar;
    private SwitchButton sbShowSystem;
    private ConfigDao configDao;
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
