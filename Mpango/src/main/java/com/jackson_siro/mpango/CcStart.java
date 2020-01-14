package com.jackson_siro.mpango;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.*;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;

import androidx.appcompat.app.AppCompatActivity;

public class CcStart extends AppCompatActivity {
    
    private MpangoView mpangoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mpangoView = new MpangoView(this, null);
        setContentView(mpangoView);
    }

    void StartPlaying(int mode) {
        Intent startGame = new Intent(this, DdTorus.class);
        startGame.putExtra("com.jackson_siro.mpango.Mode", mode);
        this.startActivity(startGame);
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            this.startActivity(new Intent(this, BbWelcome.class));
            this.finish();
        }
        return true;
    }

    public class MpangoView extends SurfaceView implements OnGestureListener,
            SurfaceHolder.Callback {
        private GestureDetector mGestureDetector;
        private SurfaceHolder holder;

        private Bitmap backgroundBmp, logoBmp, boxBmp, levelLowTitleBmp, levelMidTitleBmp, levelHighTitleBmp, goLevelLowBmp, goLevelMidBmp, goLevelHighBmp;
        private Typeface typeface;

        private int WIDTH = 480, HEIGHT = 800;

        public MpangoView(Context context, AttributeSet attrs) {
            super(context, attrs);
            mGestureDetector = new GestureDetector(this);
            setFocusable(true);
            setLongClickable(true);
            holder = getHolder();
            holder.addCallback(this);
            DisplayMetrics dm;
            dm = getApplicationContext().getResources().getDisplayMetrics();
            WIDTH = dm.widthPixels;
            HEIGHT = dm.heightPixels;

            backgroundBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.nairobicity);
            logoBmp = Mpango3D.scaleBitmap(context, R.drawable.appicon, 0.7f * WIDTH, 0.3f * HEIGHT);
            boxBmp = Mpango3D.scaleBitmap(context, R.drawable.bigbox, 0.71f * WIDTH, 0.2f * HEIGHT);

            levelLowTitleBmp = Mpango3D.scaleBitmap(context, R.drawable.level_low, WIDTH * 0.32f, HEIGHT * 0.03f);
            levelMidTitleBmp = Mpango3D.scaleBitmap(context,  R.drawable.level_mid, WIDTH * 0.32f, HEIGHT * 0.03f);
            levelHighTitleBmp = Mpango3D.scaleBitmap(context, R.drawable.level_high, WIDTH * 0.25f, HEIGHT * 0.03f);

            goLevelLowBmp = Mpango3D.scaleBitmap(context, R.drawable.tick, WIDTH * 0.17f, WIDTH * 0.17f);
            goLevelHighBmp = Mpango3D.scaleBitmap(context, R.drawable.tick, WIDTH * 0.17f, WIDTH * 0.17f);
            goLevelMidBmp = Mpango3D.scaleBitmap(context, R.drawable.tick, WIDTH * 0.17f, WIDTH * 0.17f);

            AssetManager am = context.getAssets();
            typeface = Typeface.createFromAsset(am, "ComicSansMS3.ttf");
        }

        void drawFrame() {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                Paint paint = new Paint();
                canvas.drawBitmap(backgroundBmp, null, new RectF(0, 0, WIDTH, HEIGHT), paint);
                float yPos = 41.0f;
                canvas.drawBitmap(logoBmp, (WIDTH - logoBmp.getWidth()) / 2.0f, yPos, paint);
                float xPos = (WIDTH - boxBmp.getWidth()) / 2.0f;
                yPos += logoBmp.getHeight();
                float h = (HEIGHT - boxBmp.getHeight() * 3 - yPos) * 0.25f;
                float hL = boxBmp.getHeight() / 9.0f;
                yPos += h;

                canvas.drawBitmap(boxBmp, xPos, yPos, paint);
                canvas.drawBitmap(goLevelHighBmp, xPos + hL, yPos + hL, paint);
                canvas.drawBitmap(levelHighTitleBmp, xPos + hL + goLevelHighBmp.getWidth(), yPos + hL, paint);

                paint.setTextSize(25);
                paint.setStyle(Style.FILL);
                paint.setTypeface(typeface);
                paint.setColor(0xffffffff);
                FontMetrics fm = paint.getFontMetrics();
                float sFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                float lineSpace = sFontHeight / 10.0f;
                float lineWidth = WIDTH - 2.0f * xPos - 2.5f * hL - goLevelLowBmp.getWidth();
                float baseHeight = yPos + hL + levelLowTitleBmp.getHeight();
                float startXPos = xPos + hL + goLevelLowBmp.getWidth();
                Mpango3D.drawContent(getString(R.string.level1), sFontHeight, lineSpace, lineWidth, startXPos, baseHeight, canvas, paint);

                yPos += boxBmp.getHeight() + h;
                canvas.drawBitmap(boxBmp, xPos, yPos, paint);
                canvas.drawBitmap(goLevelMidBmp, xPos + hL, yPos + hL, paint);
                canvas.drawBitmap(levelMidTitleBmp, xPos + hL + goLevelLowBmp.getWidth(), yPos + hL, paint);
                baseHeight = yPos + hL + levelMidTitleBmp.getHeight();
                Mpango3D.drawContent(getString(R.string.level2), sFontHeight, lineSpace, lineWidth, startXPos, baseHeight, canvas, paint);

                yPos += boxBmp.getHeight() + h;
                canvas.drawBitmap(boxBmp, xPos, yPos, paint);
                canvas.drawBitmap(goLevelLowBmp, xPos + hL, yPos + hL, paint);
                canvas.drawBitmap(levelLowTitleBmp, xPos + hL + goLevelLowBmp.getWidth(), yPos + hL, paint);
                baseHeight = yPos + hL + levelHighTitleBmp.getHeight();
                Mpango3D.drawContent(getString(R.string.level3), sFontHeight, lineSpace, lineWidth, startXPos, baseHeight, canvas, paint);

                holder.unlockCanvasAndPost(canvas);
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            mGestureDetector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            float yPos = 41.0f + logoBmp.getHeight();
            float h = (HEIGHT - boxBmp.getHeight() * 3 - yPos) * 0.25f;

            if (x >= (WIDTH - boxBmp.getWidth()) / 2.0f && x <= WIDTH + boxBmp.getWidth() / 2.0f) {
                if (y >= yPos + h && y <= yPos + h + boxBmp.getHeight()) StartPlaying(3);
                else if (y >= yPos + boxBmp.getHeight() + 2.0f * h && y <= yPos + 2.0f * (boxBmp.getHeight() + h)) StartPlaying(2);
                else if (y >= yPos + 2.0f * boxBmp.getHeight() + 3.0f * h && y <= yPos + 4.0f * boxBmp.getHeight() + 3.0f * h) StartPlaying(1);
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawFrame();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }
}