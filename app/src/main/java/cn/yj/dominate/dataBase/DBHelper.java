package cn.yj.dominate.dataBase;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DBHelper {
    public static DBHelper dbHelper;
    private RealmConfiguration realmConfiguration;

    /**
     * 单例模式
     * @return
     */
    public static DBHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
        }
        return dbHelper;
    }

    /**
     * 设置配置
     * @param realmConfiguration
     */
    public void setRealmConfig(RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    /**
     * 获取数据库对象
     * @return
     */
    public Realm getRealm() {
        return Realm.getInstance(realmConfiguration);
    }

}