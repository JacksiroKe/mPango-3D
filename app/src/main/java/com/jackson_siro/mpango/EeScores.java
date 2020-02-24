package com.jackson_siro.mpango;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.*;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EeScores extends AppCompatActivity {
    private HighScoresView highScoresView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout root = new FrameLayout(this);
        root.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        highScoresView = new HighScoresView(this, null);
        root.addView(highScoresView);

        setContentView(root);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void goWelcome() {
        this.startActivity(new Intent(this, BbWelcome.class));
        this.finish();
    }

    public class HighScoresView extends SurfaceView implements
            OnGestureListener, SurfaceHolder.Callback {
        private GestureDetector mGestureDetector;
        private SurfaceHolder holder;
        private int mode = 1, WIDTH = 480, HEIGHT = 800;
        private Bitmap backgroundBmp, mainmenuBmp, boxBmp, levelLowTitleBmp, levelMidTitleBmp, levelHighTitleBmp;
        private Typeface typeface;

        private final String[] types = {"Kawaida", "Wastani", "Ngumu"};
        private MpangoBest best = new MpangoBest();

        public HighScoresView(Context context, AttributeSet attrs) {
            super(context, attrs);

            mGestureDetector = new GestureDetector(this);
            setFocusable(true);
            setLongClickable(true);
            holder = getHolder();
            holder.addCallback(this);
            backgroundBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.nairobicity);
            DisplayMetrics dm;
            dm = getApplicationContext().getResources().getDisplayMetrics();
            WIDTH = dm.widthPixels;
            HEIGHT = dm.heightPixels;
            boxBmp = Mpango3D.scaleBitmap(context, R.drawable.bigbox, WIDTH * 0.9f, HEIGHT * 0.2f);
            mainmenuBmp = Mpango3D.scaleBitmap(context, R.drawable.mainmenu, WIDTH * 0.35f, HEIGHT * 0.08f);
            levelLowTitleBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.level_low);
            levelMidTitleBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.level_mid);
            levelHighTitleBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.level_high);
            AssetManager am = context.getAssets();
            typeface = Typeface.createFromAsset(am, "ComicSansMS3.ttf");
        }

        void drawFrame(ArrayList<MpangoBest.Record> arr, String type) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                Paint paint = new Paint();
                canvas.drawBitmap(backgroundBmp, null, new RectF(0, 0, WIDTH, HEIGHT), paint);
                canvas.drawBitmap(mainmenuBmp, (WIDTH - mainmenuBmp.getWidth()) * 0.5f, HEIGHT * 0.91f, paint);
                float yPos = 0.0f;
                float xPos = (WIDTH - boxBmp.getWidth()) * 0.9f;
                if (mode == 1) {
                    yPos = 0.3f * HEIGHT - boxBmp.getHeight() * 0.5f;
                    canvas.drawBitmap(boxBmp, (WIDTH - boxBmp.getWidth()) * 0.5f, yPos, paint);
                    canvas.drawBitmap(levelLowTitleBmp, null, new RectF(
                            0.13f * WIDTH + boxBmp.getWidth() * 0.5f - 10,
                            0.28f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.32f * HEIGHT
                    ), paint);
                } else {
                    canvas.drawBitmap(levelLowTitleBmp, null, new RectF(
                            0.18f * WIDTH + 0.5f * boxBmp.getWidth() - 20,
                            0.285f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.315f * HEIGHT
                    ), paint);
                }
                if (mode == 2) {
                    yPos = 0.5f * HEIGHT - boxBmp.getHeight() * 0.5f;
                    canvas.drawBitmap(boxBmp, (WIDTH - boxBmp.getWidth()) * 0.5f, yPos, paint);
                    canvas.drawBitmap(levelMidTitleBmp, null, new RectF(
                            0.13f * WIDTH + boxBmp.getWidth() * 0.5f - 10, 0.48f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10, 0.52f * HEIGHT), paint);
                } else {
                    canvas.drawBitmap(levelMidTitleBmp, null, new RectF(
                            0.18f * WIDTH + 0.5f * boxBmp.getWidth() - 10,
                            0.485f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.515f * HEIGHT
                    ), paint);
                }
                if (mode == 3) {
                    yPos = 0.7f * HEIGHT - boxBmp.getHeight() * 0.5f;
                    canvas.drawBitmap(boxBmp, (WIDTH - boxBmp.getWidth()) * 0.5f, yPos, paint);
                    canvas.drawBitmap(levelHighTitleBmp, null, new RectF(
                            0.2f * WIDTH + boxBmp.getWidth() * 0.5f - 10,
                            0.68f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.72f * HEIGHT
                    ), paint);
                } else {
                    canvas.drawBitmap(levelHighTitleBmp, null, new RectF(
                            0.25f * WIDTH + boxBmp.getWidth() * 0.5f - 10,
                            0.685f * HEIGHT,
                            (WIDTH + boxBmp.getWidth()) * 0.5f - 10,
                            0.715f * HEIGHT
                    ), paint);
                }

                paint.setTypeface(typeface);
                paint.setColor(0xffffffff);
                paint.setFakeBoldText(true);
                paint.setTextSize(40);
                FontMetrics fm = paint.getFontMetrics();
                float fFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                yPos += fFontHeight;
                canvas.drawText(type, xPos + 20, yPos, paint);
                paint.setTextSize(28);
                fm = paint.getFontMetrics();
                float sFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
                yPos += sFontHeight;
                String result = "";
                if (mode != 3) {
                    for (int i = 0; i < 3; i++) {
                        result = (i + 1) + ". ";
                        result += arr.get(i).name + " ( " + String.valueOf(arr.get(i).score) + " )";
                        canvas.drawText(result, xPos + 8, yPos + i  * sFontHeight, paint);
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        result = (i + 1) + ". ";
                        int time = arr.get(i).score;
                        int minutes = time / 60;
                        int seconds = time % 60;
                        result += arr.get(i).name + " ( " + String.valueOf(minutes) + " : "  + String.valueOf(seconds) + ")";
                        result += arr.get(i).name + " ( " +
                        String.valueOf(arr.get(i).score) + " )";
                        canvas.drawText(result, xPos + 8, yPos + i  * sFontHeight, paint);
                    }
                }

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

            if (x >= 0.13f * WIDTH + boxBmp.getWidth() * 0.5f - 10 && x <= (WIDTH + boxBmp.getWidth()) * 0.5f - 10) {
                // if(y >= 0.28f * HEIGHT && y <= 0.32f * HEIGHT)
                if (y >= 0.22f * HEIGHT && y <= 0.38f * HEIGHT) {
                    mode = 1;
                    drawFrame(best.getRecords(0), types[mode - 1]);
                    return false;
                }
                // else if(y >= 0.48f * HEIGHT && y <= 0.52f * HEIGHT)
                else if (y >= 0.42f * HEIGHT && y <= 0.58f * HEIGHT) {
                    mode = 2;
                    drawFrame(best.getRecords(1), types[mode - 1]);
                    return false;
                }
            }
            /*
             * if(x >= 0.2f * WIDTH + boxBmp.getWidth() * 0.5f - 10 && x <=
             * (WIDTH + boxBmp.getWidth()) * 0.5f - 10 && y >= 0.68f * HEIGHT &&
             * y <= 0.72f * HEIGHT)
             */
            if (x >= 0.2f * WIDTH + boxBmp.getWidth() * 0.5f - 10
                    && x <= (WIDTH + boxBmp.getWidth()) * 0.5f - 10
                    && y >= 0.62f * HEIGHT && y <= 0.78f * HEIGHT) {
                mode = 3;
                drawFrame(best.getRecords(2), types[mode - 1]);
                return false;
            }
            if (x >= (WIDTH - mainmenuBmp.getWidth()) * 0.5f
                    && x <= (WIDTH + mainmenuBmp.getWidth()) * 0.5f
                    && y >= HEIGHT * 0.91f
                    && y <= HEIGHT * 0.91f + mainmenuBmp.getHeight()) {
                // drawFrame(best.getRecords(mode - 1), types[mode - 1]);
                goWelcome();
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
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
            drawFrame(best.getRecords(mode - 1), types[mode - 1]);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }

    }
}
