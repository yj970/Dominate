package cn.yj.dominate.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by yangjie on 2017/6/23.
 */

public class Util {


    /**
     * 得到手机屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 得到手机屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */

    public static int dp2px(Context context, float dpVal)

    {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,

                dpVal, context.getResources().getDisplayMetrics());

    }


    /**
     * sp转px
     *
     * @param context
     * @param dpVal
     * @return
     */

    public static int sp2px(Context context, float spVal)

    {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,

                spVal, context.getResources().getDisplayMetrics());

    }

}
