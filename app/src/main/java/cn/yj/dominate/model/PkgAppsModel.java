package cn.yj.dominate.model;

import android.content.pm.PackageInfo;

import java.util.ArrayList;

/**
 * Created by yangjie on 2017/6/16.
 */

public class PkgAppsModel implements Cloneable{
    private ArrayList<PackageInfo> pkgAppsList;

    public PkgAppsModel(ArrayList<PackageInfo> pkgAppsList) {
        this.pkgAppsList = pkgAppsList;
    }

    public ArrayList<PackageInfo> getPkgAppsList() {
        return pkgAppsList;
    }

    public void setPkgAppsList(ArrayList<PackageInfo> pkgAppsList) {
        this.pkgAppsList = pkgAppsList;
    }

    @Override
    public PkgAppsModel clone(){
        PkgAppsModel pkgAppsModel = null;
        try {
            pkgAppsModel = (PkgAppsModel) super.clone();
            pkgAppsModel.pkgAppsList = (ArrayList<PackageInfo>) pkgAppsList.clone();  //对引用类型的域进行克隆
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return pkgAppsModel;
    }
}
