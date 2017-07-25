package com.itheima.mobilesafe05.activity;

import com.itheima.heimasafe05.R;
import com.itheima.mobilesafe05.utils.ConstValue;
import com.itheima.mobilesafe05.utils.SpUtil;
import com.itheima.mobilesafe05.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Setup4Activity extends BaseActivity {

	private CheckBox cb_box;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup4);
		
		initUI();
	}
	
	private void initUI() {
		
		cb_box = (CheckBox) findViewById(R.id.cb_box);
		//1是否选中状态的回显
		boolean open_security = SpUtil.getBoolean(getApplicationContext(), ConstValue.OPEN_SECURITY, false);
		//2根据状态，修改checkbox后续的文字显示
		cb_box.setChecked(open_security);
		if(open_security){
			cb_box.setText("安全设置已开启");
		}else {
			cb_box.setText("未开启安全设置");
		}
		//3点击过程中，监听选中状态的改变过程
		cb_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cb_box.setText("安全设置已开启");
				} else {
					cb_box.setText("未开启安全设置");
				}
				//存储点击后的状态
				SpUtil.putBoolean(getApplicationContext(), ConstValue.OPEN_SECURITY, isChecked);
			}
		});

	}
	@Override
	protected void showPrePage() {
		Intent intent = new Intent(getApplicationContext(),Setup3Activity.class);
		startActivity(intent);
		finish();
		//平移动画
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
	@Override
	protected void showNextPage() {
		Boolean open_security = SpUtil.getBoolean(getApplicationContext(), ConstValue.OPEN_SECURITY, false);
		if(open_security){
			Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
			startActivity(intent);
			finish();
			SpUtil.putBoolean(getApplicationContext(), ConstValue.SETUP_OVER, true);
		}else {
			ToastUtil.show(getApplicationContext(), "请先开启防盗保护");
		}
	}
}
