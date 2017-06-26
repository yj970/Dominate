package cn.yj.dominate.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import cn.yj.dominate.R;

/**
 * Created by yangjie on 2017/6/26.
 */

public class HandWritingView extends View {
    private Paint paint;
    Bitmap bitmap;
    Canvas canvas;
    private Path mPath;

    public HandWritingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);//笔头设置为圆形
        paint.setStrokeCap(Paint.Cap.ROUND);//笔尾设置为圆形
        paint.setAntiAlias(true);
        paint.setStrokeWidth(50);

        mPath = new Path();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        bitmap = Bitmap.createBitmap(metrics, metrics.widthPixels, metrics.heightPixels, config);

        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE :
                mPath.lineTo(event.getX(), event.getY());
                break;
        }

        canvas.drawPath(mPath, paint);
        invalidate();
        return true;


}


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, null);


}
}
