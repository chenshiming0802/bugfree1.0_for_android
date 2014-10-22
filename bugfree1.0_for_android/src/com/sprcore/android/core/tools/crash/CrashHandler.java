package com.sprcore.android.core.tools.crash;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.sprcore.android.core.tools.PhoneTools;
 

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
   
/** 
 * UncaughtException处理�?当程序发生Uncaught异常的时�?由该类来接管程序,并记录发送错误报�? 
 *  
 * @author zyq
 *  
 */  
public class CrashHandler implements UncaughtExceptionHandler {  
    private static final String TAG = "CrashHandler";  
    private Thread.UncaughtExceptionHandler mDefaultHandler;// 系统默认的UncaughtException处理�?  
    private static CrashHandler INSTANCE = new CrashHandler();// CrashHandler实例   
    private Context mContext;// 程序的Context对象   
    private Map<String, String> info = new HashMap<String, String>();// 用来存储设备信息和异常信�?  
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");// 用于格式化日�?作为日志文件名的�?���?  
    private File dir;
   
    /** 保证只有�?��CrashHandler实例 */  
    private CrashHandler() {  
   
    }  
   
    /** 获取CrashHandler实例 ,单例模式 */  
    public static CrashHandler getInstance() {  
        return INSTANCE;  
    }  
   
    /** 
     * 初始�?
     *  
     * @param context 
     */  
    public void init(Context context) {  
        mContext = context;  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理�?  
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理�?  
    }  
   
    /** 
     * 当UncaughtException发生时会转入该重写的方法来处�?
     */  
    public void uncaughtException(Thread thread, Throwable ex) {  
        if (!handleException(ex) && mDefaultHandler != null) {  
            // 如果自定义的没有处理则让系统默认的异常处理器来处�?  
            mDefaultHandler.uncaughtException(thread, ex);  
        } else {  
            try {  
                Thread.sleep(3000);// 如果处理了，让程序继续运�?秒再�?��，保证文件保存并上传到服务器   
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            } 
            if(thread.currentThread().getName().equals("main")){
            	 ExitApplication.getInstance(mContext).exit();
                  // �?��程序   
                 android.os.Process.killProcess(android.os.Process.myPid());  
                 System.exit(0);  
            }else{
            }

          
        }  
    }  
   
    /** 
     * 自定义错误处�?收集错误信息 发�?错误报告等操作均在此完成. 
     *  
     * @param ex 
     *            异常信息 
     * true 如果处理了该异常信息;否则返回false. 
     */  
    public boolean handleException(Throwable ex) {  
        if (ex == null)  
            return false;  
        new Thread() {  
            public void run() {  
                Looper.prepare();  
//                Toast.makeText(mContext, "很抱�?程序出现异常,即将�?��", 0).show();  
                Looper.loop();  
            }  
        }.start();  
        ex.printStackTrace();
        // 收集设备参数信息   
        collectDeviceInfo(mContext);  
        // 保存日志文件   
        Log.v("保存日志文件", saveCrashInfo2File(ex));
        return true;  
    }  
   
    /** 
     * 收集设备参数信息 
     *  
     * @param context 
     */  
    public void collectDeviceInfo(Context context) {  
        try {  
            PackageManager pm = context.getPackageManager();// 获得包管理器   
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),  
                    PackageManager.GET_ACTIVITIES);// 得到该应用的信息，即主Activity   
            if (pi != null) {  
                String versionName = pi.versionName == null ? "null"  
                        : pi.versionName;  
                String versionCode = pi.versionCode + "";  
                info.put("versionName", versionName);  
                info.put("versionCode", versionCode);  
            }  
        } catch (NameNotFoundException e) {  
            e.printStackTrace();  
        }  
   
        Field[] fields = Build.class.getDeclaredFields();// 反射机制   
        for (Field field : fields) {  
            try {  
                field.setAccessible(true);  
                info.put(field.getName(), field.get("").toString());  
                Log.d(TAG, field.getName() + ":" + field.get(""));  
            } catch (IllegalArgumentException e) {  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
   
    private String saveCrashInfo2File(Throwable ex) {  
        StringBuffer sb = new StringBuffer();  
        for (Map.Entry<String, String> entry : info.entrySet()) {  
            String key = entry.getKey();  
            String value = entry.getValue();  
            sb.append(key + "=" + value + "\r\n");  
        }  
        Writer writer = new StringWriter();  
        PrintWriter pw = new PrintWriter(writer);  
        ex.printStackTrace(pw);  
        Throwable cause = ex.getCause();  
        // 循环�?���?��的异常信息写入writer�?  
        while (cause != null) {  
            cause.printStackTrace(pw);  
            cause = cause.getCause();  
        }  
        pw.close();// 记得关闭   
        String result = writer.toString();  
        sb.append(result);  
        // 保存文件   
        if(PhoneTools.hasSdCard()){
//			fileName= "/mnt/sdcard/commerceOL";
			dir=new File(Environment.getExternalStorageDirectory()+"/sprcore_Error");
		}else{
			dir=new File(Environment.getDataDirectory()+"/data/com.sprcore.mybugfree/sprcore_Error");
		}
        //fileName
        String fileName="error.log";
            try {  
            	 if (!dir.exists()){  
                     dir.mkdir();
            	 }
                 FileOutputStream fos = new FileOutputStream(new File(dir,fileName));  
                Log.e("CrashHandler", dir.toString());  
                fos.write(sb.toString().getBytes());  
                fos.close();  
                return fileName;  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        return null;  
    }  
}  