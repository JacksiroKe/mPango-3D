package com.jackson_siro.mpango;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.*;

import java.io.IOException;
import java.io.InputStream;

public class GlobalVars {
    public final static String[] highScoreFiles = {"kawaida.txt",
            "wastani.txt",
            "ngumu.txt"};
    public final static String TORUSDIR = "/AppSmata/Mpango3d";
    public final static String SDCARD = "/sdcard";
    public final static String DATADIR = "../data/data/com.jackson_siro.mpango";
    public static boolean HELP = true;
    public static boolean KEYS = true;
    //public static boolean GHOST = true;
    // public static boolean KEYSPOS = true;
    public static boolean SOUND = true;

    //ç¼©æ”¾ä½�å›¾
    public static Bitmap scaleBitmap(Context context, int bmpId, float destWidth, float destHeight) {
        Bitmap orgBmp = BitmapFactory.decodeResource(
                context.getResources(), bmpId);
        int widthOrg = orgBmp.getWidth();
        int heightOrg = orgBmp.getHeight();
        float scaleWidth = destWidth / widthOrg;
        float scaleHeight = destHeight / heightOrg;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(orgBmp, 0, 0, widthOrg, heightOrg, matrix, true);
    }

    public static String getHelpContent(String name, Context context) {
        String s = "";
        byte[] content;
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(name);
            int size = is.available();
            content = new byte[size];
            is.read(content, 0, size);
            is.close();
            s = new String(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static float drawContent(String content, float fontHeight, float lineSpace,
                                    float lineWidth, float xPos, float h, Canvas canvas, Paint paint) {
        int len = content.length();
        String line = "";
        float wChars = 0;
        int k = 0;
        int i = 0;
        for (; i < len; i++) {
            wChars += paint.measureText(String.valueOf(content.charAt(i)));        //å½“å‰�è¡Œä¸Šå­—ç¬¦çš„æ€»å®½åº¦
            if (i + 1 < len && paint.measureText(String.valueOf(content.charAt(i + 1))) + wChars > lineWidth)//è¶…å‡ºè¡Œå®½
            {
                if (content.charAt(i) != ' ' && content.charAt(i + 1) != ' ')    //å½“å‰�å­—ç¬¦å’Œä¸‹ä¸€ä¸ªå­—ç¬¦éƒ½ä¸�ä¸ºç©ºæ ¼
                {
                    if (content.charAt(i - 1) == ' ')    //å‰�ä¸€ä¸ªå­—ç¬¦ä¸ºç©ºæ ¼
                    {
                        line = content.substring(k, i);
                        wChars = 0;
                        k = i;
                        i--;
                    } else {
                        line = content.substring(k, i) + "-";
                        wChars = 0;
                        k = i;
                        i--;
                    }
                } else {
                    line = content.substring(k, i + 1);
                    wChars = 0;//æ�¢è¡Œ
                    k = i + 1;
                }
                h += lineSpace + fontHeight;
                canvas.drawText(line, xPos, h, paint);
            }
        }
        //ç»˜åˆ¶å‰©ä½™çš„å®½åº¦ä¸�è¶³ä¸€è¡Œçš„å­—ç¬¦
        if (wChars != 0) {
            h += lineSpace + fontHeight;
            canvas.drawText(content.substring(k, i), xPos, h, paint);
        }
        return h;
    }
}