package ll.zhao.triptoyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/4/4.
 */

public class Utils {

    private static final String KEY_PROVIDER = "AndroidKeyStore";
    private static final String ALGORITHM = "AES/CBC/PKSC7Padding";

    public static final String PATTERN_ALISA = "pattern";

    //ロングインflag
    public static final String LOGIN_FLAG = "loginflg";
    //ログイン完了の値
    public static final String LOGIN_DONE = "1";

    public static final int IMAGE_CODE = 1;
    public static final int CROP_CODE = 2;

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density / 2;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density / 2;
        return (int) (pxValue * scale + 0.5f);
    }

    public static int dp2pxS(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density ;
        return (int) (pxValue * scale + 0.5f);
    }

    //    把本地图片毛玻璃化
    public static Bitmap toBlur(Bitmap originBitmap, int scaleRatio) {
        //        int scaleRatio = 10;
        // 增大scaleRatio缩放比，使用一样更小的bitmap去虚化可以到更好的得模糊效果，而且有利于占用内存的减小；
        int blurRadius = 8;//通常设置为8就行。
        //增大blurRadius，可以得到更高程度的虚化，不过会导致CPU更加intensive


//       其中前三个参数很明显，其中宽高我们可以选择为原图尺寸的1/10；
//        第四个filter是指缩放的效果，filter为true则会得到一个边缘平滑的bitmap，
//        反之，则会得到边缘锯齿、pixelrelated的bitmap。
//        这里我们要对缩放的图片进行虚化，所以无所谓边缘效果，filter=false。
        if (scaleRatio <= 0) {
            scaleRatio = 10;
        }
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                originBitmap.getWidth() / scaleRatio,
                originBitmap.getHeight() / scaleRatio,
                false);
        Bitmap blurBitmap = doBlur(scaledBitmap, blurRadius, true);
        return blurBitmap;
    }


    private static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {
        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }


        if (radius < 1) {
            return (null);
        }


        int w = bitmap.getWidth();
        int h = bitmap.getHeight();


        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);


        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;


        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];


        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }


        yw = yi = 0;


        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;


        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;


            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }


    /**
     * AndroidKeyStoreの準備
     */
    public static KeyStore prepareKeyStore(String alias) {
        KeyStore mKeyStore = null;
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            mKeyStore.load(null);
            if (!mKeyStore.containsAlias(alias)) {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA, KEY_PROVIDER);
                keyPairGenerator.initialize(
                        new KeyGenParameterSpec.Builder(
                                alias,
                                KeyProperties.PURPOSE_DECRYPT)
                                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                                .build());
                keyPairGenerator.generateKeyPair();
            }
        } catch (Exception e) {
            HLLog.showLog("Utils","prepareKeyStore",e.getMessage());
        }
        return mKeyStore;
    }

    /**
     * Encrypt string text
     *
     * @param alias key alias
     * @param plainText string to be encrypted
     *
     * @return base64 encoded cipher text
     */
    public static String encryptString(String alias, String plainText) {
        String encryptedText = "";
        KeyStore mKeyStore = prepareKeyStore(alias);
        if(mKeyStore == null){
            return  encryptedText;
        }
        try {
            PublicKey publicKey = mKeyStore.getCertificate(alias).getPublicKey();

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream, cipher);
            cipherOutputStream.write(plainText.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte [] bytes = outputStream.toByteArray();
            encryptedText = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            HLLog.showLog("Utils","encryptString",e.getMessage());
        }
        return encryptedText;
    }

    /**
     * Decrypt base64 encoded cipher text
     *
     * @param alias key alias
     * @param encryptedText base64 encoded cipher text
     *
     * @return plain text string
     */
    public static String decryptString(String alias, String encryptedText) {
        String plainText = "";
        KeyStore mKeyStore = prepareKeyStore(alias);
        if(mKeyStore == null){
            return  encryptedText;
        }
        try {
            PrivateKey privateKey = (PrivateKey) mKeyStore.getKey(alias, null);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(Base64.decode(encryptedText, Base64.DEFAULT)), cipher);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int b;
            while ((b = cipherInputStream.read()) != -1) {
                outputStream.write(b);
            }
            outputStream.close();
            plainText = outputStream.toString("UTF-8");
        } catch (Exception e) {
            HLLog.showLog("Utils","decryptString",e.getMessage());
        }
        return plainText;
    }

    public static void saveDateToShare(Context context,String key,String value){
        SharedPreferences dataStore = context.getSharedPreferences("TripStore", MODE_PRIVATE);
        SharedPreferences.Editor editor = dataStore.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getDateFromShare(Context context,String key){
        SharedPreferences dataStore = context.getSharedPreferences("TripStore", MODE_PRIVATE);
        return dataStore.getString(key,"");
    }

    /**
     * 图片转为二进制数据
     * @param bitmap 画像
     * @return
     */
    public static byte[] bitmabToBytes(Bitmap bitmap){
        //将图片转化为位图
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();
            return imagedata;
        }catch (Exception e){
        }finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

}
