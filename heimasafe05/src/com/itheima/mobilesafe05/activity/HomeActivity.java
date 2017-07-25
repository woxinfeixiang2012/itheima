package com.itheima.mobilesafe05.activity;

import com.itheima.heimasafe05.R;
import com.itheima.mobilesafe05.utils.ConstValue;
import com.itheima.mobilesafe05.utils.Md5Util;
import com.itheima.mobilesafe05.utils.SpUtil;
import com.itheima.mobilesafe05.utils.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private GridView gv_home;
	private String[] mTitleStrs;
	private int[] mDrawableIds;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//初始化控件
		initUI();
		//初始化数据
		initData();
	}
	private void initData() {
		mTitleStrs = new String[]{
			"手机防盗","通信卫士","软件管理",
			"进程管理","流量统计","手机杀毒",
			"缓存清理","高级工具","设置中心"
		};
		mDrawableIds = new int[]{
				R.drawable.home_safe,R.drawable.home_callmsgsafe,R.drawable.home_apps,
				R.drawable.home_taskmanager,R.drawable.home_netmanager,R.drawable.home_trojan,
				R.drawable.home_sysoptimize,R.drawable.home_tools,R.drawable.home_settings
		};
		//九宫格控件设置数据适配器（等同于ListView数据适配器)
		gv_home.setAdapter(new MyAdapter());
		//注册九宫格单个条目的点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				switch (position) {
				case 0://手机防盗模块
					//开启对话框
					showDialog();
					break;
				case 8://设置中心模块
					Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
				
			}
		});
	}
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			//条目的总数
			return mTitleStrs.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mTitleStrs[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO 条目的索引
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
			
			TextView tv_title=(TextView)view.findViewById(R.id.tv_title);
			ImageView iv_icon=(ImageView)view.findViewById(R.id.iv_icon);
			
			tv_title.setText(mTitleStrs[position]);
			iv_icon.setBackgroundResource(mDrawableIds[position]);
			
			return view;
		}
		
	}

	private void showDialog() {
		//判断本地是否有存储密码
		String psd=SpUtil.getString(this, ConstValue.MOBILE_SAFE_PSD, "");
		if(TextUtils.isEmpty(psd)){
			//1初始设置密码对话框
			showSetPsdDialog();
		}else{
			//2确认密码对话框
			showConfirmPsdDialog();
		}
	}
	private void showConfirmPsdDialog() {
		Builder builder=new AlertDialog.Builder(this);
		final AlertDialog dialog=builder.create();
		
		final View view=View.inflate(this, R.layout.dialog_confirm_psd, null);
		//dialog.setView(view);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		
		Button bt_submit=(Button)view.findViewById(R.id.bt_submit);
		Button bt_cancel=(Button)view.findViewById(R.id.bt_cancel);

		
		bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText et_confirmPsd=(EditText)view.findViewById(R.id.et_confirm_psd);
				String confirmPsd=et_confirmPsd.getText().toString();
				
				if(!TextUtils.isEmpty(confirmPsd)){
					//将存储在sp中32为的md5密码获取出来，然后将输入的密码同样进行md5,然后与sp中存储的密码比对
					String psd=SpUtil.getString(getApplicationContext(), ConstValue.MOBILE_SAFE_PSD, "");
					if(psd.equals(Md5Util.encoder(confirmPsd))){
						//进入安全防盗卫士模块
						Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
						startActivity(intent);
						dialog.dismiss();
					}else{
						ToastUtil.show(getApplicationContext(), "密码输入错误");
					}
						
				}else {
					ToastUtil.show(getApplicationContext(), "请输入密码");
				}
			}
		});
		bt_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	private void showSetPsdDialog() {
		//因为需要自定义对话框的展示样式，所系需要调用dialog.setView(View);
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog=builder.create();
		
		final View view=View.inflate(this, R.layout.dialog_set_psd, null);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		
		Button btn_summit=(Button)view.findViewById(R.id.bt_submit);
		Button btn_cancel=(Button)view.findViewById(R.id.bt_cancel);
		
		btn_summit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//确认按钮
				EditText et_set_psd = (EditText)view.findViewById(R.id.et_set_psd);
				EditText et_confirm_psd = (EditText)view.findViewById(R.id.et_confirm_psd);
				
				String psd=et_set_psd.getText().toString();
				String confirmPsd=et_confirm_psd.getText().toString();
				
				if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)){
					if(TextUtils.equals(psd,confirmPsd)){
						//进入手机防盗模块，开启一个新的Activity
						Intent intent = new Intent(getApplicationContext(),SetupOverActivity.class);
						startActivity(intent);
						//跳转到新的界面后，需要去隐藏对话框
						dialog.dismiss();
						SpUtil.putString(getApplicationContext(),
								ConstValue.MOBILE_SAFE_PSD, 
								Md5Util.encoder(confirmPsd));
					}else{
						ToastUtil.show(getApplicationContext(), "2次输入密码不一致");
					}
				}else{
				    //提示用户密码输入有空的情况
					ToastUtil.show(getApplicationContext(), "请输入密码");
				}

			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//取消按钮
				dialog.dismiss();
			}
		});

		
	}
	private void initUI() {
		gv_home = (GridView)findViewById(R.id.gv_home);
	}

}
