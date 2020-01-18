package com.addkey.keylibrary.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.addkey.keylibrary.R;


/**
 * @author Created by ligaoyuan
 * @date 2019-05-08
 */
public class ImgSwitchButton extends View {
    private static final String TAG = "ImgSwitchButton";
    private int mOpenBgColor;
    private int mCloseBgColor;
    private int mTextColor;
    private int mCircleRoundColor;

    private int mImgOpen;
    private int mImgClose;

    private Paint mtextPaint;
    private Paint mBorderLinePaint;
    private Paint mBackgroundPaint;
    private Paint mImgPaint;

    private Bitmap mBitmapOpen;
    private Bitmap mBitmapClose;
    private float mClipPathRadius;
    private float mBorderLineRadius;
    private float mImgX;//图片的x坐标
    private int mMinViewWidth;
    private int mMinViewHeight;
    private int mImgWidth;
    private int mImgHeight;

    private Bitmap mShowingBitmap;
    private int mShowBackground;

    private int mLineToBord = 2;//剪切与线的距离
    private SwitchState mSwitchState;
    private ValueAnimator mValueAnimator;
    private boolean isOpen = false;//是否开启
    private boolean canChangeState = true;//是否允许触摸时切换状态

    private boolean firstStateOpen = false;//默认开启状态

    private OnStateChangeListener mOnStateChangeListener;
    private CheckChangePermission mCheckChangePermission;
    public enum SwitchState{
        open,move,close
    }
    public ImgSwitchButton(Context context) {
        super(context);
        initAttrs(context,null);
    }

    public ImgSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public ImgSwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
    }

    public void setFirstStateOpen(boolean firstStateOpen) {
        this.firstStateOpen = firstStateOpen;
        if (firstStateOpen){
            mSwitchState = SwitchState.open;
            mShowingBitmap = mBitmapOpen;
            mShowBackground = mOpenBgColor;
            isOpen = true;
            mImgX = mImgWidth+mLineToBord;
        }else {
            mSwitchState = SwitchState.close;
            mShowingBitmap = mBitmapClose;
            mShowBackground = mCloseBgColor;
            isOpen = false;
            mImgX = mLineToBord;
        }
        postInvalidate();
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        mOnStateChangeListener = onStateChangeListener;
    }

    public void setCheckChangePermission(CheckChangePermission checkChangePermission) {
        mCheckChangePermission = checkChangePermission;
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImgSwitchButton);
        mCircleRoundColor = typedArray.getColor(R.styleable.ImgSwitchButton_circleRoundColor, Color.GRAY);
        mOpenBgColor = typedArray.getColor(R.styleable.ImgSwitchButton_openBackgroundColor, Color.GREEN);
        mCloseBgColor = typedArray.getColor(R.styleable.ImgSwitchButton_closeBackgroundColor, Color.GRAY);
        mTextColor = typedArray.getColor(R.styleable.ImgSwitchButton_textColor, Color.BLACK);
        mImgClose = typedArray.getResourceId(R.styleable.ImgSwitchButton_closeImg,0);
        mImgOpen = typedArray.getResourceId(R.styleable.ImgSwitchButton_openImg,0);
        if (mImgClose == 0||mImgOpen == 0){
            throw new NullPointerException("imgClose and imgOpen can not null!");
        }
        typedArray.recycle();

        mBitmapClose = BitmapFactory.decodeResource(getResources(),mImgClose);
        mBitmapOpen = BitmapFactory.decodeResource(getResources(),mImgOpen);
        mShowingBitmap = mBitmapClose;
        mShowBackground = mCloseBgColor;
        mImgWidth = Math.max(mBitmapClose.getWidth(),mBitmapOpen.getWidth());
        mImgHeight = Math.max(mBitmapOpen.getHeight(),mBitmapClose.getHeight());

        mMinViewWidth = 2*(mImgWidth+2*mLineToBord);
        mMinViewHeight = mImgHeight+2*mLineToBord;

        mtextPaint = new Paint();
        mtextPaint.setAntiAlias(true);
        mtextPaint.setColor(mTextColor);
        mtextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mtextPaint.setTextAlign(Paint.Align.CENTER);

        mBorderLinePaint = new Paint();
        mBorderLinePaint.setColor(mCircleRoundColor);
        mBorderLinePaint.setAntiAlias(true);
        mBorderLinePaint.setStrokeWidth(1);
        mBorderLinePaint.setStyle(Paint.Style.STROKE);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mImgPaint = new Paint();
        mImgPaint.setAntiAlias(true);

        mValueAnimator = new ValueAnimator();
        mValueAnimator.setDuration(500);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (isOpen){
                    mImgX=mImgWidth-(float)animation.getAnimatedValue();
                }else {
                    mImgX=(float)animation.getAnimatedValue();
                }
                postInvalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isOpen){
                    mSwitchState = SwitchState.close;
                    mShowingBitmap = mBitmapClose;
                    mShowBackground = mCloseBgColor;
                    isOpen = false;
                }else {
                    mSwitchState = SwitchState.open;
                    mShowingBitmap = mBitmapOpen;
                    mShowBackground = mOpenBgColor;
                    isOpen = true;
                }
                if (mOnStateChangeListener!=null){
                    mOnStateChangeListener.onStateChange(isOpen);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mSwitchState = SwitchState.move;
            }
        });

        mSwitchState = SwitchState.close;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mMinViewWidth, mMinViewHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mMinViewWidth, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, mMinViewHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBorderLineRadius =mImgHeight/2f;
        mClipPathRadius = mBorderLineRadius+mLineToBord;
        clipViewByPath(canvas);
        drawBackground(canvas);
        drawBorderLine(canvas);
        drawImg(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mValueAnimator != null && mValueAnimator.isRunning()) {
                    mValueAnimator.end();
                    mValueAnimator.cancel();
                }
                if (mCheckChangePermission!=null){
                    mCheckChangePermission.onViewTouchDown();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                changeState();
                break;

        }
        return super.onTouchEvent(event);
    }

    private void changeState() {
        if (canChangeState){
            mValueAnimator.setFloatValues(0,mImgWidth);
            mValueAnimator.start();
        }else {
            if (mOnStateChangeListener!=null){
                mOnStateChangeListener.onError();
            }
        }
    }

    public void openSwitch(){
        if (mSwitchState == SwitchState.close){
            changeState();
        }
    }
    public void closeSwitch(){
        if (mSwitchState == SwitchState.open){
            changeState();
        }
    }

    public void setCanChangeState(boolean canChangeState) {
        this.canChangeState = canChangeState;
    }

    //画开关图
    private void drawImg(Canvas canvas) {
        canvas.drawBitmap(mShowingBitmap,mImgX,mLineToBord,mImgPaint);
    }


    //剪切圆角背景
    private void clipViewByPath(Canvas canvas) {
        Path path = new Path();
        RectF rectFLeft = new RectF(0,0,2*mClipPathRadius,2*mClipPathRadius);
        RectF rectFRight = new RectF(2*mClipPathRadius,0,4*mClipPathRadius,2*mClipPathRadius);
        path.addArc(rectFLeft,90,180);
        path.lineTo(3*mClipPathRadius,0);
        path.addArc(rectFRight,-90,180);
        path.lineTo(mClipPathRadius,2*mClipPathRadius);

        canvas.clipPath(path);
    }

    //绘制背景
    private void drawBackground(Canvas canvas) {
        Path path = new Path();
        RectF rectFLeft = new RectF(mLineToBord,mLineToBord,2*mBorderLineRadius+mLineToBord,2*mBorderLineRadius+mLineToBord);
        RectF rectFRight = new RectF(2*mBorderLineRadius+mLineToBord,mLineToBord,4*mBorderLineRadius+mLineToBord,2*mBorderLineRadius+mLineToBord);
        path.addArc(rectFLeft,90,180);
        path.lineTo(3*mBorderLineRadius+mLineToBord,mLineToBord);
        path.addArc(rectFRight,-90,180);
        path.lineTo(mBorderLineRadius+mLineToBord,2*mBorderLineRadius+mLineToBord);
        LinearGradient linearGradient = new LinearGradient(
                2*mBorderLineRadius,mLineToBord,
                2*mBorderLineRadius,2*mBorderLineRadius+mLineToBord,
                mShowBackground,mShowBackground, Shader.TileMode.CLAMP);

        mBackgroundPaint.setShader(linearGradient);
        canvas.drawPath(path,mBackgroundPaint);
    }

    //绘制边框
    private void drawBorderLine(Canvas canvas) {
        Path path = new Path();
        RectF rectFLeft = new RectF(mLineToBord,mLineToBord,2*mBorderLineRadius+mLineToBord,2*mBorderLineRadius+mLineToBord);
        RectF rectFRight = new RectF(2*mBorderLineRadius+mLineToBord,mLineToBord,4*mBorderLineRadius+mLineToBord,2*mBorderLineRadius+mLineToBord);
        path.addArc(rectFLeft,90,180);
        path.lineTo(3*mBorderLineRadius+mLineToBord,mLineToBord);
        path.addArc(rectFRight,-90,180);
        path.lineTo(mBorderLineRadius+mLineToBord,2*mBorderLineRadius+mLineToBord);
        canvas.drawPath(path,mBorderLinePaint);
    }

    public interface OnStateChangeListener{
        void onStateChange(boolean opened);
        void onError();
    }

    public interface CheckChangePermission{
        void onViewTouchDown();
    }
}
