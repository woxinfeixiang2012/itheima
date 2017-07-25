package com.itheima.mobilesafe05.activity;

import com.itheima.heimasafe05.R;
import com.itheima.mobilesafe05.utils.ConstValue;
import com.itheima.mobilesafe05.utils.SpUtil;

import com.itheima.mobilesafe05.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initUpdate();
	}

	/**
	 * 版本更新开关
	 */
	private void initUpdate() {
		final SettingItemView siv_update=(SettingItemView)findViewById(R.id.siv_update);
		//获取已有的开关状态，用作显示
		boolean open_update = SpUtil.getBoolean(this, ConstValue.OPEN_UPDATE, false);
		//是否选中，根据上一次存储的结果去做决定
		siv_update.setCheck(open_update);
		
		siv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果之前是选中的，点击过后，变成未选中
				//如果之前是未选中的，点击过程变成选中的
				
				//获取之前的选中状态
				Boolean isCheck=siv_update.isCheck();
				siv_update.setCheck(!isCheck);
				//将取反后的状态存储到响应的sp中
				SpUtil.putBoolean(getApplicationContext(), ConstValue.OPEN_UPDATE, !isCheck);
				
			}
		});
		
	}

}
