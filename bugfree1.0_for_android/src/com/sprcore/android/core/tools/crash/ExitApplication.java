package com.sprcore.android.core.tools.crash;


import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ExitApplication extends Application
{
  private List<Activity> activityList = new LinkedList();
  private static ExitApplication instance;
  private ActivityManager activityManager;
  private Context context;
  public ExitApplication(){
	  
  }

  private ExitApplication(Context context)
  {
    this.context = context;
    this.activityManager = ((ActivityManager)this.context.getSystemService("activity"));
  }

  public static ExitApplication getInstance(Context context)
  {
    if (null == instance)
    {
      instance = new ExitApplication(context);
    }
    return instance;
  }

  public ExitApplication addActivity(Activity activity)
  {
    this.activityList.add(activity);
    return this;
  }

  public void exit()
  {
    for (Activity activity : this.activityList)
    {
      activity.finish();
    }

    //this.context.stopService(new Intent("org.androidpn.client.NotificationService"));

//    System.exit(0);
  }
  @Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//initImageLoader();
		 CrashHandler crashHandler = CrashHandler.getInstance();  
		 crashHandler.init(this); 
	}
   private void initImageLoader(){
		// This configuration tuning is custom. You can tune every option, you may tune some of them, 
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.memoryCacheSize(8 * 1024 * 1024) // 8 Mb
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			//.enableLogging() // Not necessary in common
			.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}