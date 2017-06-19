package cn.yj.dominate;


import cn.yj.dominate.dataBase.DBHelper;
import cn.yj.dominate.dataBase.dao.ConfigDao;
import cn.yj.dominate.model.ConfigModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by yangjie on 2017/6/19.
 */

public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initDataBase();


    }

    /**
     * 创建数据库，这里使用realm
     */
    private void initDataBase() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().schemaVersion(0).name("dominate.realm").build();
        DBHelper.getInstance().setRealmConfig(realmConfiguration);

        ConfigDao configDao = new ConfigDao();
        ConfigModel configModel = configDao.query();
        if (configModel == null) {
            configDao.configure();
        }
        configDao.close();
    }




}
