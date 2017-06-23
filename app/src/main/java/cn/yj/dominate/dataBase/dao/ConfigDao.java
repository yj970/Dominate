package cn.yj.dominate.dataBase.dao;

import cn.yj.dominate.model.ConfigModel;
import io.realm.Realm;

/**
 * Created by yangjie on 2017/6/19.
 */

public class ConfigDao extends RealmDao{

    public ConfigDao(){
        super();
    }

    /**
     * 同步查询配置文件
     * @return
     */
    public ConfigModel query() {
        return realm.where(ConfigModel.class).findFirst();
    }


    /**
     * 更新是否显示系统级别APP
     * @param isShowSystemApps
     */
    public void updateIsShowSystemApps(final boolean isShowSystemApps) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ConfigModel configModel = query();
                if (configModel == null) {
                    configure();
                    configModel = query();
                }
                configModel.setShowSystemApps(isShowSystemApps);

            }
        });    }

    /**
     * 创建配置表
     */
    public void configure() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ConfigModel configModel = realm.createObject(ConfigModel.class);
                configModel.setShowSystemApps(false);
            }
        });
    }



}
