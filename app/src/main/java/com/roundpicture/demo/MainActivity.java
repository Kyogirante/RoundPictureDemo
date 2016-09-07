package com.roundpicture.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * 为了便于实例，所有的图片处理都放在activity中进行
 */
public class MainActivity extends AppCompatActivity {

    private ImageView mShaderRoundImg;

    private ImageView mShaderBottomRoundImg;

    private ImageView mXfermodeRoundImg;

    private ImageView mRBitmapDrawableImg;

    private ImageView mShaderCircleImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShaderRoundImg = (ImageView) findViewById(R.id.demo_shader_round_image);
        mShaderBottomRoundImg = (ImageView) findViewById(R.id.demo_shader_bottom_round_image);
        mXfermodeRoundImg = (ImageView) findViewById(R.id.demo_xfmode_round_image);
        mRBitmapDrawableImg = (ImageView) findViewById(R.id.demo_r_bt_dr_round_image);
        mShaderCircleImg = (ImageView) findViewById(R.id.demo_circle_image);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.demo_icon_android_logo);
        Bitmap recBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.demo_icon_rect_android_logo);

        mShaderRoundImg.setImageBitmap(roundBitmapByShader(bitmap,
                (int)getResources().getDimension(R.dimen.round_bitmap_width),
                (int)getResources().getDimension(R.dimen.round_bitmap_height),
                (int)getResources().getDimension(R.dimen.round_bitmap_radius)));

        mShaderBottomRoundImg.setImageBitmap(roundBottomBitmapByShader(bitmap,
                (int)getResources().getDimension(R.dimen.round_bitmap_width),
                (int)getResources().getDimension(R.dimen.round_bitmap_height),
                (int)getResources().getDimension(R.dimen.round_bitmap_radius)));

        mXfermodeRoundImg.setImageBitmap(roundBitmapByXfermode(bitmap,
                (int)getResources().getDimension(R.dimen.round_bitmap_width),
                (int)getResources().getDimension(R.dimen.round_bitmap_height),
                (int)getResources().getDimension(R.dimen.round_bitmap_radius)));

        mRBitmapDrawableImg.setImageDrawable(roundBitmapByBitmapDrawable(bitmap,
                (int)getResources().getDimension(R.dimen.round_bitmap_width),
                (int)getResources().getDimension(R.dimen.round_bitmap_height),
                (int)getResources().getDimension(R.dimen.round_bitmap_radius)));

        mShaderCircleImg.setImageBitmap(circleBitmapByShader(recBitmap,
                (int)getResources().getDimension(R.dimen.round_bitmap_width),
                (int)getResources().getDimension(R.dimen.round_bitmap_width) / 2));
    }

    /**
     * 利用BitmapShader绘制圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    private Bitmap roundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        // 初始化缩放比
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);

        // 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 根据控件大小对纹理图进行拉伸缩放处理
        bitmapShader.setLocalMatrix(matrix);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        // 初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔将纹理图绘制到画布上面
        targetCanvas.drawRoundRect(new RectF(0, 0, outWidth, outWidth), radius, radius, paint);

        return targetBitmap;
    }

    /**
     * 利用BitmapShader绘制底部圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    private Bitmap roundBottomBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        // 初始化缩放比
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);

        // 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // 根据控件大小对纹理图进行拉伸缩放处理
        bitmapShader.setLocalMatrix(matrix);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        // 初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔绘制底部圆角
        targetCanvas.drawRoundRect(new RectF(0, outHeight - 2 * radius, outWidth, outWidth), radius, radius, paint);

        // 利用画笔绘制顶部上面直角部分
        targetCanvas.drawRect(new RectF(0, 0, outWidth, outHeight - radius), paint);

        return targetBitmap;
    }

    /**
     * 利用Xfermode绘制圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    private Bitmap roundBitmapByXfermode(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        // 等比例缩放拉伸
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        Bitmap newBt = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        canvas.drawARGB(0, 0, 0, 0);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        RectF rectF = new RectF(0f, 0f, (float) outWidth, (float) outHeight);

        // 在画布上绘制圆角图
        canvas.drawRoundRect(rectF, radius, radius, paint);

        // 设置叠加模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 在画布上绘制原图片
        Rect ret = new Rect(0, 0, outWidth, outHeight);
        canvas.drawBitmap(newBt, ret, ret, paint);

        return targetBitmap;
    }

    /**
     * 利用RoundedBitmapDrawable绘制圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param outWidth
     *              结果图片宽度，一般为控件的宽度
     * @param outHeight
     *              结果图片高度，一般为控件的高度
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    private Drawable roundBitmapByBitmapDrawable(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        // 等比例缩放拉伸
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        Bitmap newBt = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        // 绘制圆角
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), newBt);
        dr.setCornerRadius(radius);
        dr.setAntiAlias(true);

        return dr;
    }

    /**
     * 利用BitmapShader绘制底部圆角图片
     *
     * @param bitmap
     *              待处理图片
     * @param edgeWidth
     *              正方形控件大小
     * @param radius
     *              圆角半径大小
     * @return
     *              结果图片
     */
    private Bitmap circleBitmapByShader(Bitmap bitmap, int edgeWidth, int radius) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        float btWidth = bitmap.getWidth();
        float btHeight = bitmap.getHeight();
        // 水平方向开始裁剪的位置
        float btWidthCutSite = 0;
        // 竖直方向开始裁剪的位置
        float btHeightCutSite = 0;
        // 裁剪成正方形图片的边长，未拉伸缩放
        float squareWidth = 0f;
        if(btWidth > btHeight) { // 如果矩形宽度大于高度
            btWidthCutSite = (btWidth - btHeight) / 2f;
            squareWidth = btHeight;
        } else { // 如果矩形宽度不大于高度
            btHeightCutSite = (btHeight - btWidth) / 2f;
            squareWidth = btWidth;
        }

        // 设置拉伸缩放比
        float scale = edgeWidth * 1f / squareWidth;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);

        // 将矩形图片裁剪成正方形并拉伸缩放到控件大小
        Bitmap squareBt = Bitmap.createBitmap(bitmap, (int)btWidthCutSite, (int)btHeightCutSite, (int)squareWidth, (int)squareWidth, matrix, true);

        // 初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(squareBt, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(edgeWidth, edgeWidth, Bitmap.Config.ARGB_8888);

        // 初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);

        // 初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 利用画笔绘制圆形图
        targetCanvas.drawRoundRect(new RectF(0, 0, edgeWidth, edgeWidth), radius, radius, paint);

        return targetBitmap;
    }
}
