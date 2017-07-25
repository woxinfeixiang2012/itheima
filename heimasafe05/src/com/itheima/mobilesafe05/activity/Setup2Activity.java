package com.itheima.mobilesafe05.activity;

import com.itheima.heimasafe05.R;
import com.itheima.mobilesafe05.utils.ConstValue;
import com.itheima.mobilesafe05.utils.SpUtil;
import com.itheima.mobilesafe05.utils.ToastUtil;
import com.itheima.mobilesafe05.view.SettingItemView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class Setup2Activity extends BaseActivity {
	private SettingItemView siv_sim_bound;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup2);
		
		initUI();
	}
	
	private void initUI() {
		siv_sim_bound = (SettingItemView)findViewById(R.id.siv_sim_bind);
		//1回显(读取已有的绑定状态用作显示,sp是否存储了sim卡的序列号)
		String sim_number = SpUtil.getString(this,ConstValue.SIM_NUMBER,"");
		//2判断序列卡好是否是""
		if(TextUtils.isEmpty(sim_number)){
			siv_sim_bound.setCheck(false);
		}else{
			siv_sim_bound.setCheck(true);
		}
		siv_sim_bound.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//3获取原来的check状态
				boolean isCheck = siv_sim_bound.isCheck();
				//4将原有的状态取反，存储sim卡号，设置给当前条目
				siv_sim_bound.setCheck(!isCheck);
				if(!isCheck){
					//获取sim卡序列号TelephoneManager
					TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
					//获取sim卡序列号
					String simSerialNumberString = manager.getSimSerialNumber();
					SpUtil.putString(getApplicationContext(),ConstValue.SIM_NUMBER,simSerialNumberString);
				}else{
					//将存储序列卡好的节点从sp中删除
					SpUtil.remove(getApplicationContext(),ConstValue.SIM_NUMBER);
				}
			}
		});
	}
	@Override
	protected void showPrePage() {
		Intent intent = new Intent(getApplicationContext(),Setup1Activity.class);
		startActivity(intent);
		finish();
		//平移动画
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
	@Override
	protected void showNextPage() {
		String simNumber = SpUtil.getString(getApplicationContext(), ConstValue.SIM_NUMBER,"");
		if(!TextUtils.isEmpty(simNumber)){
			Intent intent = new Intent(getApplicationContext(),Setup3Activity.class);
			startActivity(intent);
			finish();
		}else{
			ToastUtil.show(this,"请先绑定sim卡");
		}
		//平移动画
		overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
	}
}
