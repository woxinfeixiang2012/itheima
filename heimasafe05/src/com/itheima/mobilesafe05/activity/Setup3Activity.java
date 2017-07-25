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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Setup3Activity extends BaseActivity {

	private Button bt_select_number;
	private EditText et_phone_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup3);
		
		initUI();
	}
	private void initUI() {
		et_phone_number = (EditText)findViewById(R.id.et_phone_number);
		//获取联系人电话号码回显过程
		String phone = SpUtil.getString(this,ConstValue.CONTACT_NUMBER,"");
		et_phone_number.setText(phone);
		//选择联系人的按钮
		bt_select_number = (Button)findViewById(R.id.bt_select_number);
		bt_select_number.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//打开联系人列表界面
			 	Intent intent = new Intent(getApplicationContext(),ContactListActivity.class);
			 	startActivityForResult(intent, 0);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null){
			//1返回到当前界面的时候，接受结果的方法
			String phone = data.getStringExtra("phone");
			//2将特殊字符‘-’过滤掉
			phone = phone.replace("-", "").replace(" ","").trim();
			et_phone_number.setText(phone);
			
			//3存储联系人至sp中
			SpUtil.putString(getApplicationContext(), ConstValue.CONTACT_NUMBER, phone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	protected void showPrePage() {
		Intent intent = new Intent(this,Setup2Activity.class);
		startActivity(intent);
		finish();
		//平移动画
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}
	@Override
	protected void showNextPage() {
		// 点击按钮后，需要获取输入框中的联系人，再做下一页的操作
		String phone = et_phone_number.getText().toString();

		if (!TextUtils.isEmpty(phone)) {
			// 跳转到下一页
			Intent intent = new Intent(getApplicationContext(),
					Setup4Activity.class);
			startActivity(intent);
			finish();

			// 如果是输入的电话号码需要保存一下
			SpUtil.putString(getApplicationContext(),
					ConstValue.CONTACT_NUMBER, phone);
		} else {
			ToastUtil.show(getApplicationContext(), "请输入安全电话号码");
		}
		// 平移动画
		overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
	}
}
