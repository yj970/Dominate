package cn.yj.dominate.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yangjie on 2017/6/19.
 */

abstract public class BaseActivity extends AppCompatActivity {


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        findView();
        bindData();
        setListener();
    }


    abstract public void findView();
    abstract public void bindData();
    abstract public void setListener();
}
