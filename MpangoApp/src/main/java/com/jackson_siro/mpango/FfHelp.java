package com.jackson_siro.mpango;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.FontMetrics;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class  FfHelp extends AppCompatActivity {
    private HelpView helpView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout root = new FrameLayout(this);
        root.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        helpView = new HelpView(this, null);
        root.addView(helpView);

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

    public class HelpView extends SurfaceView implements OnGestureListener, SurfaceHolder.Callback {
        private GestureDetector mGestureDetector;
        private SurfaceHolder holder;

        private Bitmap backgroundBmp, boxBmp, mainmenuBmp, helpContentBmp;
        private int WIDTH = 480, HEIGHT = 800;
        private Typeface typeface;

        public HelpView(Context context, AttributeSet attrs) {
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
            boxBmp = Mpango3D.scaleBitmap(context, R.drawable.largebox, WIDTH * 0.82f, HEIGHT * 0.80f);
            AssetManager am = context.getAssets();
            typeface = Typeface.createFromAsset(am, "ComicSansMS3.ttf");

            mainmenuBmp = Mpango3D.scaleBitmap(context, R.drawable.mainmenu, WIDTH * 0.35f, HEIGHT * 0.08f);
            helpContentBmp = Bitmap.createBitmap((int) (boxBmp.getWidth() * 0.8f), (int) (HEIGHT * 0.8f), Config.ARGB_8888);

            String subContent = getString(R.string.how_to_play);

            Canvas canvas = new Canvas(helpContentBmp);
            Paint paint = new Paint();
            paint.setColor(0xffffffff);
            paint.setTypeface(typeface);
            paint.setTextSize(60);
            paint.setFakeBoldText(true);
            FontMetrics fm = paint.getFontMetrics();
            float fFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
            float lineSpace = 0.1f * fFontHeight;
            float startPos = 0;
            float baseHeight = -lineSpace;
            baseHeight = Mpango3D.drawContent(subContent, fFontHeight, lineSpace, boxBmp.getWidth() * 0.8f, startPos, baseHeight, canvas, paint);
            paint.setTextSize(35);
            paint.setFakeBoldText(false);
            fm = paint.getFontMetrics();
            fFontHeight = (float) Math.ceil(fm.descent - fm.ascent);
            lineSpace = 0.2f * fFontHeight;

            baseHeight = Mpango3D.drawContent(getString(R.string.how_to_play_desc1), fFontHeight, lineSpace, boxBmp.getWidth() * 0.8f, startPos, baseHeight, canvas, paint);

            Mpango3D.drawContent(getString(R.string.how_to_play_desc2), fFontHeight, lineSpace, boxBmp.getWidth() * 0.8f, startPos, baseHeight, canvas, paint);
        }

        void drawFrame() {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                canvas.drawBitmap(backgroundBmp, null, new RectF(0, 0, WIDTH, HEIGHT), paint);
                canvas.drawBitmap(boxBmp, (WIDTH - boxBmp.getWidth()) / 2.0f, (HEIGHT - boxBmp.getHeight()) / 2.0f, paint);
                canvas.drawBitmap(mainmenuBmp, ( WIDTH - mainmenuBmp.getWidth()) * 0.5f,
                        (3.0f * HEIGHT + boxBmp.getHeight()) * 0.25f - mainmenuBmp.getHeight() * 0.5f, paint);
                canvas.drawBitmap(helpContentBmp, (WIDTH - helpContentBmp.getWidth()) * 0.5f,
                        (HEIGHT - helpContentBmp.getHeight()) * 0.5f, paint);
                /*paint.setStyle(Style.FILL);
                    paint.setColor(0xddffffff);
                    float midPos = (2 * WIDTH + boxBmp.getWidth() + helpContentBmp.getWidth()) * 0.25f;
                    float yPosL = (HEIGHT - boxBmp.getHeight() * 0.9f) * 0.5f;
                    float yPosH = (HEIGHT + boxBmp.getHeight() * 0.9f) * 0.5f;
                    canvas.drawRoundRect(new RectF(midPos - 3,yPosL, midPos + 3, yPosH), 2, 2, paint);
                    paint.setColor(0xdd1010ee);
                    distance = distance < yPosL + 1 ? yPosL + 1 : distance > yPosH - 15 ? yPosH - 15 : distance;
                    canvas.drawRoundRect(new RectF(midPos - 2, distance,
                            midPos + 2, distance + 14), 1, 1, paint);*/
                /*canvas.save();
                    canvas.clipRect((WIDTH - helpContentBmp.getWidth()) * 0.5f, yPosL, (WIDTH + helpContentBmp.getWidth()) * 0.5f, yPosH);
                    canvas.drawBitmap(helpContentBmp, (WIDTH - helpContentBmp.getWidth()) * 0.5f, 2 * yPosL - distance, paint);*/

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
            float yPos = (3.0f * HEIGHT + boxBmp.getHeight()) * 0.25f - mainmenuBmp.getHeight() * 0.5f;
            if (x >= (WIDTH - mainmenuBmp.getWidth()) * 0.5f && x <= (WIDTH + mainmenuBmp.getWidth()) * 0.5f
                    && y >= yPos && y <= yPos + mainmenuBmp.getHeight()) {
                //drawFrame();
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
            /*distance = e2.getY();
               drawFrame();*/
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

        protected void onMpango3DurationChanged(Configuration config) {
        }
    }
}