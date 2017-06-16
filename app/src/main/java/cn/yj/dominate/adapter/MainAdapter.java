package cn.yj.dominate.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cn.yj.dominate.R;

/**
 * Created by yangjie on 2017/6/16.
 */

public class MainAdapter extends BaseAdapter{
    // app名字缓存
    private HashMap<Integer, String> nameCache = new HashMap<Integer, String>();
    // app图标缓存
    private HashMap<Integer, Drawable> iconCache = new HashMap<Integer, Drawable>();

    private List<PackageInfo>  pkgAppsList;
    private Context context;
    private PackageManager packageManager;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    public MainAdapter(Context context, PackageManager packageManager, List<PackageInfo> pkgAppsList) {
        this.pkgAppsList = pkgAppsList;
        this.context = context;
        this.packageManager = packageManager;
    }

    @Override
    public int getCount() {
        return pkgAppsList == null ? 0 : pkgAppsList.size();
    }

    @Override
    public Object getItem(int position) {
        return pkgAppsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main, null, false);
            holder = new ViewHolder();
            holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
            holder.tvName = ((TextView)convertView.findViewById(R.id.tv_app_name));
            holder.tvPackageName = ((TextView)convertView.findViewById(R.id.tv_app_package));
            holder.btnLauncher = (Button) convertView.findViewById(R.id.btn_launcher);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // app名字
        if (nameCache.get(position) == null) {
            getName((Activity) context, position, holder.tvName);
        } else {
            holder.tvName.setText(nameCache.get(position));
        }

        // app图标
        if (iconCache.get(position) == null) {
            getIcon((Activity) context, position, holder.ivIcon);
        } else {
            holder.ivIcon.setImageDrawable(iconCache.get(position));
        }

        // app包名
        holder.tvPackageName.setText(pkgAppsList.get(position).packageName);

        // 点击启动APP
        holder.btnLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过包名唤醒APP
                if (context != null) {
                    Intent LaunchIntent = packageManager.getLaunchIntentForPackage(pkgAppsList.get(position).packageName);
                    context.startActivity(LaunchIntent);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvPackageName;
        ImageView ivIcon;
        Button btnLauncher;
    }


    /**
     * 获取app名字
     * @param position 位置
     * @param textView 控件
     */
    private void getName(final Activity context, final int position, final TextView textView) {
        textView.setTag(position);
        new Thread(new Runnable() {
            @Override
            public void run() {

                final String name = (String) pkgAppsList.get(position).applicationInfo.loadLabel(packageManager);

                // 因为view.post()不能保证100%执行runnable方法体，因此使用下面的这种方式
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (textView.getTag().equals(position)) {
                            textView.setText(name);
                        }
                        nameCache.put(position, name);
                    }
                });
            }
        }).start();
    }

    /**
     * 获取app图标
     * @param position 位置
     * @param imageView 控件
     */
    private void getIcon(final Activity context, final int position, final ImageView imageView) {
        imageView.setTag(position);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Drawable drawable =  pkgAppsList.get(position).applicationInfo.loadIcon(packageManager);

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (imageView.getTag().equals(position)) {
                            imageView.setImageDrawable(drawable);
                        }
                        iconCache.put(position, drawable);
                    }
                });
            }
        }).start();
    }
}


