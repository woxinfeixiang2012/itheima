package com.itheima.heimasafe05.activity;

import com.itheima.heimasafe05.R;

import android.R.string;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private TextView tv_version_name;
	private PackageManager pm;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //初始化UI
        initUI();
        //初始化data
        initData();
    }


	/**获取数据
	 * 
	 */
	private void initData() {
		//获取版本名称
		tv_version_name.setText(getVersionName());
		//获取版本号
		
	}

	/**
	 * @return获取应用的版本名称 null表示异常
	 */
	private String getVersionName() {
		pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	 /**
	 * @return获取应用的版本号 0表示异常
	 */
	private int getVersionCode()
	{
		pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**获取UI控件
	 * 
	 */
	private void initUI() {
		tv_version_name = (TextView)findViewById(R.id.tv_version_name);
		
	}

}
