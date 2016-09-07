package com.roundpicture.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

/**
 * 圆角转换绘制类
 *
 * Created by wangkegang on 2016/09/07 .
 */
public class RoundBitmapTransformation {
    /**
     * 图片圆角半径
     */
    private int mRadius;
    /**
     * 切图类型
     */
    private CornerType mCornerType;

    public RoundBitmapTransformation radius(int radius) {
        this.mRadius = radius;
        return this;
    }

    public RoundBitmapTransformation cornerType(CornerType cornerType) {
        this.mCornerType = cornerType;
        return this;
    }

    public Bitmap roundBitmap(@NonNull Bitmap bitmap, int outWidth, int outHeight) {
        if(bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }

        // 缩放设置
        float widthScale = outWidth * 1.0f / bitmap.getWidth();
        float heightScale = outHeight * 1.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);

        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);

        // 结果图
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);

        // 创建画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        // 创建目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);
        drawBitmap(paint, targetCanvas, outWidth, outHeight);

        return targetBitmap;
    }

    private void drawBitmap(Paint paint, Canvas canvas, int width, int height) {
        switch (mCornerType) {
            case ALL: // 四角圆角
                canvas.drawRoundRect(new RectF(0, 0, width, height), mRadius, mRadius, paint);
                break;
            case LEFT: // 左边两圆角
                canvas.drawRoundRect(new RectF(0, 0, 2 * mRadius, height), mRadius, mRadius, paint);
                canvas.drawRect(new RectF(mRadius, 0, width, height), paint);
                break;
            case RIGHT: // 右边两圆角
                canvas.drawRoundRect(new RectF(width - 2 * mRadius, 0, width, height), mRadius, mRadius, paint);
                canvas.drawRect(new RectF(0, 0, width - mRadius, height), paint);
                break;
            case TOP: // 顶部两圆角
                canvas.drawRoundRect(new RectF(0, 0, width, 2 * mRadius), mRadius, mRadius, paint);
                canvas.drawRect(new RectF(0, mRadius, width, height), paint);
                break;
            case BOTTOM: // 底部两圆角
                canvas.drawRoundRect(new RectF(0, height - 2*mRadius, width, height), mRadius, mRadius, paint);
                canvas.drawRect(new RectF(0, 0, width, height - mRadius), paint);
                break;
            case DIAGONAL_FROM_LEFT_TOP: // 左上到右下对角圆角
                canvas.drawRoundRect(new RectF(0, 0, 2 * mRadius, 2 * mRadius), mRadius, mRadius, paint);
                canvas.drawRoundRect(new RectF(width - 2 * mRadius, height - 2 * mRadius, width, height), mRadius,
                        mRadius, paint);
                canvas.drawRect(new RectF(0, mRadius, width - 2 * mRadius, height), paint);
                canvas.drawRect(new RectF(0 + 2 * mRadius, 0, width, height - mRadius), paint);
                break;
            case DIAGONAL_FROM_RIGHT_TOP: // 右上到左下对面圆角
                canvas.drawRoundRect(new RectF(width - 2 * mRadius, 0, width, 2 * mRadius), mRadius, mRadius, paint);
                canvas.drawRoundRect(new RectF(0, height - 2 * mRadius, 2 * mRadius, height), mRadius, mRadius, paint);
                canvas.drawRect(new RectF(0, 0, width - mRadius, height - mRadius), paint);
                canvas.drawRect(new RectF(mRadius,  mRadius, width, height), paint);
                break;
            case SINGLE_TOP_LEFT: // 左上单个圆角
                canvas.drawRoundRect(new RectF(0, 0, 2 * mRadius, 2 * mRadius), mRadius, mRadius, paint);
                canvas.drawRect(new RectF(0, mRadius,  mRadius, height), paint);
                canvas.drawRect(new RectF(mRadius, 0, width, height), paint);
                break;
            case SINGLE_TOP_RIGHT: // 右上单个圆角
                canvas.drawRoundRect(new RectF(width - 2 * mRadius, 0, width, 2 * mRadius), mRadius,
                        mRadius, paint);
                canvas.drawRect(new RectF(0, 0, width - mRadius, height), paint);
                canvas.drawRect(new RectF(width - mRadius, mRadius, width, height), paint);
                break;
            case SINGLE_BOTTOM_LEFT: // 左下单个圆角
                canvas.drawRoundRect(new RectF(0, height - 2 * mRadius, 2 * mRadius, height),
                        mRadius, mRadius, paint);
                canvas.drawRect(new RectF(0, 0, 2 * mRadius, height - mRadius), paint);
                canvas.drawRect(new RectF(mRadius, 0, width, height), paint);
                break;
            case SINGLE_BOTTOM_RIGHT: // 右下单个圆角
                canvas.drawRoundRect(new RectF(width - 2 * mRadius, height - 2 * mRadius, width, height), mRadius, mRadius, paint);
                canvas.drawRect(new RectF(0, 0, width - mRadius, height), paint);
                canvas.drawRect(new RectF(width - mRadius, 0, width, height - mRadius), paint);
                break;
            case EXCLUDE_TOP_LEFT: // 左上非圆角
                canvas.drawRoundRect(new RectF(0, height - 2 * mRadius, width, height), mRadius, mRadius, paint);
                canvas.drawRoundRect(new RectF(width - 2 * mRadius, 0, width, height), mRadius, mRadius,
                        paint);
                canvas.drawRect(new RectF(0, 0, width - mRadius, height - mRadius), paint);
                break;
            case EXCLUDE_TOP_RIGHT: // 右上非圆角
                canvas.drawRoundRect(new RectF(0, 0, 2 * mRadius, height), mRadius, mRadius,
                        paint);
                canvas.drawRoundRect(new RectF(0, height - 2 * mRadius, width, height), mRadius, mRadius,
                        paint);
                canvas.drawRect(new RectF(mRadius, 0, width, height - mRadius), paint);
                break;
            case EXCLUDE_BOTTOM_LEFT: // 左下非圆角
                canvas.drawRoundRect(new RectF(0, 0, width, 2 * mRadius), mRadius, mRadius, paint);
                canvas.drawRoundRect(new RectF(width - 2 * mRadius, 0, width, height), mRadius, mRadius,
                        paint);
                canvas.drawRect(new RectF(0, 0 + mRadius, width - mRadius, height), paint);
                break;
            case EXCLUDE_BOTTOM_RIGHT: // 右上非圆角
                canvas.drawRoundRect(new RectF(0, 0, width, 2 * mRadius), mRadius, mRadius,
                        paint);
                canvas.drawRoundRect(new RectF(0, 0, 2 * mRadius, height), mRadius, mRadius,
                        paint);
                canvas.drawRect(new RectF(mRadius, mRadius, width, height), paint);
                break;
            default:
                break;
        }
    }

    public enum CornerType {
        ALL, // 四角圆角
        LEFT, RIGHT, TOP, BOTTOM, // 同边圆角
        DIAGONAL_FROM_LEFT_TOP, DIAGONAL_FROM_RIGHT_TOP, // 对角线圆角
        SINGLE_TOP_LEFT, SINGLE_TOP_RIGHT, SINGLE_BOTTOM_LEFT, SINGLE_BOTTOM_RIGHT, // 单个圆角
        EXCLUDE_TOP_LEFT, EXCLUDE_TOP_RIGHT, EXCLUDE_BOTTOM_LEFT, EXCLUDE_BOTTOM_RIGHT // 三个圆角
    }
}
