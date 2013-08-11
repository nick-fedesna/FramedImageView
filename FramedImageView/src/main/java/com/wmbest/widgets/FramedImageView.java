package com.wmbest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.*;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wmbest.widgets.R;

public class FramedImageView extends ImageView {

    private Shape mShape = Shape.CIRCLE;
    private Drawable mFrame;

    public FramedImageView(Context aContext, AttributeSet aAttrs) {
        super(aContext, aAttrs, 0);
        setup(aContext, aAttrs);
    }

    public FramedImageView(Context aContext, AttributeSet aAttrs, int defStyle) {
        super(aContext, aAttrs, defStyle);
        setup(aContext, aAttrs);
    }

    private void setup(Context aContext, AttributeSet aAttrs) {
        TypedArray a = aContext.obtainStyledAttributes(aAttrs,
                R.styleable.FramedImageView);

        int shape = a.getInt(R.styleable.FramedImageView_shape, 0);
        mShape = Shape.values()[shape];
        mFrame = a.getDrawable(R.styleable.FramedImageView_frame);

        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mShape) {
            case CIRCLE:
                drawCircle(canvas);
                break;
            default:
                super.onDraw(canvas);
        }

        if (mFrame != null) {
            mFrame.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            mFrame.draw(canvas);
        }
    }

    private void drawCircle(Canvas aCanvas) {
        Bitmap src = drawableToBitmap(getDrawable());

        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
         
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        int center = aCanvas.getWidth() / 2;

        aCanvas.drawCircle(center, center, center, paint);
    }

    public static Bitmap drawableToBitmap (Drawable aDrawable) {
        if (aDrawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) aDrawable).getBitmap();
        }

        int width = aDrawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = aDrawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap); 
        aDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        aDrawable.draw(canvas);

        return bitmap;
    }

    public static enum Shape {
        NONE, SQUARE, CIRCLE;
    }
}

