package cn.yj.dominate.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.yj.dominate.R;
import cn.yj.dominate.adapter.MainAdapter;
import cn.yj.dominate.dataBase.dao.ConfigDao;
import cn.yj.dominate.eventBusModel.DataChangeMessage;
import cn.yj.dominate.model.PkgAppsModel;

public class MainActivity extends BaseActivity {


    private ListView lv;
    private Toolbar toolbar;
    private MainAdapter mainAdapter;
    private ConfigDao configDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
    }

    @Override
    public void findView() {
        lv = (ListView) findViewById(R.id.lv);
    }

    @Override
    public void bindData() {
        // 获得应用列表
        PackageManager packageManager = getPackageManager();
        final List<PackageInfo> pkgAppsList = packageManager.getInstalledPackages(0);
        // 是否显示系统级别的App
        configDao = new ConfigDao();
        boolean isShowSystemApp = configDao.query().isShowSystemApps();
        // 填充数据
        mainAdapter = new MainAdapter(this, packageManager, new PkgAppsModel((ArrayList<PackageInfo>) pkgAppsList), isShowSystemApp);
        lv.setAdapter(mainAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        configureToolbar();
    }

    @Override
    public void setListener() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * 配置toolbar属性
     */
    private void configureToolbar() {
        toolbar.setTitle("主宰");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        // 监听方法只有在setSupportActionBar(toolbar);的下方时才生效
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    break;
                }
                return false;
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
    public void dataChange(DataChangeMessage message) {
        mainAdapter.setShowSystemApp(message.isShowSystemData());
        mainAdapter.notifyDataSetChanged();
    }
}
