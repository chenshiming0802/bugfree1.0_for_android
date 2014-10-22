package com.sprcore.android.core.tools.wordpress;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
 
 

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MediaUtils {
    public class RequestCode {
        public static final int ACTIVITY_REQUEST_CODE_PICTURE_LIBRARY = 1000;
        public static final int ACTIVITY_REQUEST_CODE_TAKE_PHOTO = 1100;
        public static final int ACTIVITY_REQUEST_CODE_VIDEO_LIBRARY = 1200;
        public static final int ACTIVITY_REQUEST_CODE_TAKE_VIDEO = 1300;
    }

    public interface LaunchCameraCallback {
        public void onMediaCapturePathReady(String mediaCapturePath);
    }

    public static boolean isValidImage(String url) {
        if (url == null)
            return false;

        if (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".gif"))
            return true;
        return false;
    }

//    private static boolean isDocument(String url) {
//        if (url == null)
//            return false;
//
//        if (url.endsWith(".doc") || url.endsWith(".docx") || url.endsWith(".odt") || url.endsWith(".pdf"))
//            return true;
//        return false;
//    }
//
//    private static boolean isPowerpoint(String url) {
//        if (url == null)
//            return false;
//
//        if (url.endsWith(".ppt") || url.endsWith(".pptx") || url.endsWith(".pps") || url.endsWith(".ppsx") || url.endsWith(".key"))
//            return true;
//        return false;
//    }
//
//    private static boolean isSpreadsheet(String url) {
//        if (url == null)
//            return false;
//
//        if (url.endsWith(".xls") || url.endsWith(".xlsx"))
//            return true;
//        return false;
//    }
//
//    private static boolean isVideo(String url) {
//        if (url == null)
//            return false;
//        if (url.endsWith(".ogv") || url.endsWith(".mp4") || url.endsWith(".m4v") || url.endsWith(".mov") ||
//                url.endsWith(".wmv") || url.endsWith(".avi") || url.endsWith(".mpg") || url.endsWith(".3gp") || url.endsWith(".3g2"))
//            return true;
//        return false;
//    }

//    public static int getPlaceholder(String url) {
//        if (isValidImage(url))
//            return R.drawable.media_image_placeholder;
//        else if(isDocument(url))
//            return R.drawable.media_document;
//        else if(isPowerpoint(url))
//            return R.drawable.media_powerpoint;
//        else if(isSpreadsheet(url))
//            return R.drawable.media_spreadsheet;
//        else if(isVideo(url))
//            return R.drawable.media_movieclip;
//        return 0;
//    }

    /** E.g. Jul 2, 2013 @ 21:57 **/
    public static String getDate(long ms) {
        Date date = new Date(ms);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy '@' HH:mm", Locale.ENGLISH);

        // The timezone on the website is at GMT
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        return sdf.format(date);
    }

    
    public static void launchPictureLibrary(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //AppLockManager.getInstance().setExtendedTimeout();
        fragment.startActivityForResult(Intent.createChooser(intent, "Select photo"), RequestCode.ACTIVITY_REQUEST_CODE_PICTURE_LIBRARY);
    }
  
}
