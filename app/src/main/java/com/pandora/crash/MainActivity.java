package com.pandora.crash;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pandora.crash.base.PermissionsActivity;

public class MainActivity extends PermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPermissionsFull() {

    }

    /**
     * 测试Crash
     *
     * @param view
     */
    public void onCrash1(View view) {
        int result = 1000 / 0;
        Log.d(TAG, "onCrash1 , result = " + result);
    }
}
