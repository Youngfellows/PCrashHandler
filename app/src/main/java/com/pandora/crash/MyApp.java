package com.pandora.crash;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.pandora.crash.crash.CrashHandler;
import com.pandora.crash.crash.CrashHandler2;


public class MyApp extends Application {

    private String TAG = this.getClass().getSimpleName();


    private static Context mContext;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ANSWER_PHONE_CALLS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate ");
        mContext = this;
        init();
    }

    public static Context getContext() {
        return mContext;
    }

    private void init() {
        initCarshHandler();
        //setCrashHandler();
        if (checkPermissions(needPermissions)) {
            // TODO: 2020/5/10
            Log.d(TAG, "have there are permissions ...");
        }
    }

    private void initCarshHandler() {
        /**
         * 简单初始化
         */
        //		CrashHandler.getInstance().init(this, BuildConfig.DEBUG);

        /**
         * 个性初始化
         */
        CrashHandler.getInstance().init(this, BuildConfig.DEBUG, true, 0, MainActivity.class);


        /**
         * 参数1:this
         * 参数2:是否保存日志到SD卡crash目录，建议设置为BuildConfig.DEBUG，在debug时候保存，方便调试
         * 参数3:是否crash后重启APP
         * 参数4:多少秒后重启app，建议设为0，因为重启采用闹钟定时任务模式，app会反应3秒钟，所以最快也是3-4秒后重启
         * 参数5：重启后打开的第一个activity，建议是splashActivity
         */


        /**
         * 更多的设置方法
         */
        /*
        //自定义Toast
		Toast toast = Toast.makeText(this, "自定义提示信息", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		CrashHandler.setCustomToast(toast);
		//自定义提示信息
		CrashHandler.setCrashTip("自定义提示信息");
		//自定义APP关闭动画
		CrashHandler.setCloseAnimation(android.R.anim.fade_out);
		*/
    }


    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= 23 && getApplicationInfo().targetSdkVersion >= 23) {
            Log.d(TAG, "check permission ... ");
            for (String perm : permissions) {
                if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "checkPermissions fail: " + perm);
                    return false;
                }
            }
        }
        return true;
    }

    private void setCrashHandler() {
        CrashHandler2 crashHandler2 = CrashHandler2.getInstance(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler2);
    }

}
