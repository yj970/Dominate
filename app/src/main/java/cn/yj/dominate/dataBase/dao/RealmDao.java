package cn.yj.dominate.dataBase.dao;

import cn.yj.dominate.dataBase.DBHelper;
import io.realm.Realm;

/**
 * Created by yangjie on 2017/6/19.
 */

abstract public class RealmDao {
    protected Realm realm;

    /**
     * 初始化数据库对象
     */
    public RealmDao() {
            if (realm == null) {
                realm = DBHelper.getInstance().getRealm();
            }
    }


    /**
     * 关闭资源
     */
    public void close() {
        if (realm != null) {
            realm.close();
        }
    }
}
