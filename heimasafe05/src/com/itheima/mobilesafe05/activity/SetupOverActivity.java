package com.itheima.mobilesafe05.activity;

import com.itheima.heimasafe05.R;
import com.itheima.mobilesafe05.utils.ConstValue;
import com.itheima.mobilesafe05.utils.SpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SetupOverActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//密码输入成功并且4个导航界面设置完成---->设置完成导功能列表界面
		boolean setup_over=SpUtil.getBoolean(this, ConstValue.SETUP_OVER, false);
		if(setup_over){
			//密码输入成功，并且四个导航界面设置完成直接跳转到设置完成功能列表界面
			setContentView(R.layout.activity_setup_over);
			
			initUI();
		}else{
			//密码输入成功，四个导航界面没有设置完成跳转到导航界面的第一个
			Intent intent = new Intent(this,Setup1Activity.class);
			startActivity(intent);
			finish();
		}
	}

	private void initUI() {
	    TextView tv_phone = (TextView)findViewById(R.id.tv_phone);
	    //设置联系人号码
	    String phone = SpUtil.getString(this,ConstValue.CONTACT_NUMBER,"");
	    tv_phone.setText(phone);
	    //重置设置条目被点击
	    TextView tv_reset_setup = (TextView)findViewById(R.id.tv_reset_setup);
	    tv_reset_setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),Setup1Activity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
