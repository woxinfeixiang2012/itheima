package com.itheima.mobilesafe05.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.itheima.heimasafe05.R;

import android.R.string;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListActivity extends Activity {

	protected static final String tag = "ContactListActivity";
	private List<HashMap<String, String>> contactList=new ArrayList<HashMap<String,String>>();
	private MyAdapter mMyAdapter;

	private Handler mHandler=new Handler(){

		public void handleMessage(Message msg){
			mMyAdapter = new MyAdapter();
			lv_contact.setAdapter(mMyAdapter);
		}
	};
	private ListView lv_contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		
		initUI();
		initData();
	}
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return contactList.size();
		}

		@Override
		public HashMap<String, String> getItem(int position) {
			// TODO Auto-generated method stub
			return contactList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View  view = View.inflate(getApplicationContext(), R.layout.listview_contact, null);
			TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
			TextView tv_phone = (TextView)view.findViewById(R.id.tv_phone);
						
			tv_name.setText(getItem(position).get("name"));
			tv_phone.setText(getItem(position).get("phone"));
			
			return view;
		}
	}
	 /* 
	  * 获取联系人数据
	 */
	private void initData(){
	  new Thread(){
		  public void run()
		  {
			  //1获取系统内容歇息器对象
			  ContentResolver contentResolver =	getContentResolver();
			  //2查询系统联系人数据库表过程(读取联系人的权限)
			 Cursor cursor = contentResolver.query(
					  Uri.parse("content://com.android.contacts/raw_contacts"),
					  new String[]{"contact_id"},null,null,null);
			 contactList.clear();
			 //3循环游标制止没有数据为止
			 while(cursor.moveToNext()){
				String id = cursor.getString(0);
				Log.i(tag, "id="+id);
				Cursor indexCursor = contentResolver.query(Uri.parse("content://com.android.contacts/data"), 
						new String[]{"data1","mimetype"},
						"raw_contact_id = ?",
						new String[]{id},
						null);
				//循环获取每一个联系人的电话号码和名称，数据类型
				HashMap<String, String> hashMap = new HashMap<String,String>();
				while(indexCursor.moveToNext()){
					String data = indexCursor.getString(0);
					String type = indexCursor.getString(1);
					if(type.equals("vnd.android.cursor.item/phone_v2")){
						if(!TextUtils.isEmpty(data)){
							hashMap.put("phone", data);
						}
					}else if(type.equals("vnd.android.cursor.item/name")){
						if(!TextUtils.isEmpty(data)){
							hashMap.put("name",data);
						}
					}
					
				}
				indexCursor.close();
				contactList.add(hashMap);
			 }
			 cursor.close();
			 //7,消息机制,发送一个空的消息告诉主线程可以是用子线程已经填充好的数据
			 Message msg=Message.obtain();
			 mHandler.sendEmptyMessage(0);
		  }
	  }.start();
	}

	private void initUI() {
	 lv_contact = (ListView)findViewById(R.id.lv_contact);
	 lv_contact.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			//1获取点中条目的索引指向集合中的对象
			if(mMyAdapter!=null){
				HashMap<String, String> hashMap = mMyAdapter.getItem(position);
				//2获取当前条目指向集合对应的电话号码
				String phone = hashMap.get("phone");
				//3，此电话号码需要给第三个导航界面使用
				//4,在结束此界面回到前一个导航界面的时候需要将数据返回回去
				Intent intent = new Intent();
				intent.putExtra("phone", phone);
				setResult(0, intent);
				
				finish();
			}
		}
	});
		
	}
}
