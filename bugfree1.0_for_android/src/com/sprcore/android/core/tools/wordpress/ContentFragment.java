package com.sprcore.android.core.tools.wordpress;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.sprcore.android.core.tools.wordpress.MediaUtils.LaunchCameraCallback;
import com.sprcore.android.core.tools.wordpress.MediaUtils.RequestCode;
import com.sprcore.android.mbf.base.AppException;
 

public class ContentFragment {

    
    
    public static void launchCamera(Fragment fragment, LaunchCameraCallback callback) {
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            showSDCardRequiredDialog(fragment.getActivity());
        } else {
            Intent intent = prepareLaunchCameraIntent(callback);
            fragment.startActivityForResult(intent, RequestCode.ACTIVITY_REQUEST_CODE_TAKE_PHOTO);
            //AppLockManager.getInstance().setExtendedTimeout();
        }
    } 
    
    private static void showSDCardRequiredDialog(Activity activity) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle("SD Card Required");
        dialogBuilder.setMessage("A mounted SD card is required to upload media");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setCancelable(true);
        dialogBuilder.create().show();
    }  
    
    private static Intent prepareLaunchCameraIntent(LaunchCameraCallback callback) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        String mediaCapturePath = path + File.separator + "Camera" + File.separator + "mbf-" + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.setData( Uri.fromFile(new File(mediaCapturePath)));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mediaCapturePath)));

        if (callback != null) {
            callback.onMediaCapturePathReady(mediaCapturePath);
        }

        // make sure the directory we plan to store the recording in exists
        File directory = new File(mediaCapturePath).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            try {
                throw new IOException("Path to file could not be created.");
            } catch (IOException e) {
                //AppLog.e(T.POSTS, e);
            	throw new AppException(e);
            }
        }
        return intent;
    }
    
    public static void launchPictureLibrary(Fragment fragment) {
        MediaUtils.launchPictureLibrary(fragment);
        //AppLockManager.getInstance().setExtendedTimeout();
    }
}
