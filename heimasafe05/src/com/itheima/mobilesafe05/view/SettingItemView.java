package com.itheima.mobilesafe05.view;

import com.itheima.heimasafe05.R;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.itheima.heimasafe05";

	private static final String tag = "SettingItemView";
	
	private CheckBox cb_box;
	private TextView tv_dec;

	private String mDestitle;
	private String mDeson;
	private String mDesoff;
	
	public SettingItemView(Context context) {
		this(context,null);
	}
	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//xml-view 将设置界面的一个条目转换成view对象,直接添加到当前SettingItemView对应的View中去
		View.inflate(context, R.layout.setting_item_view, this);
		
		//自定义组合控件中的标题
		TextView tv_title= (TextView)findViewById(R.id.tv_title);
		tv_dec = (TextView)findViewById(R.id.tv_des);
		cb_box = (CheckBox)findViewById(R.id.cb_box);
		
		//获取自定义以及原生的属性的操作，AttributeSet attrs对象获取
		initAtrrs(attrs);

		//获取布局文件中定义的字符串，赋值给自定义组合控件中的标题
		tv_title.setText(mDestitle);
	}
	/**
	 * @param atrrs 构造方法中获取自定义属性的集合
	 */
	private void initAtrrs(AttributeSet atrrs) {
		
        mDestitle = atrrs.getAttributeValue(NAMESPACE, "destitle");
		mDeson = atrrs.getAttributeValue(NAMESPACE, "deson");
		mDesoff = atrrs.getAttributeValue(NAMESPACE, "desoff");
		
		Log.i(tag, mDestitle);
		Log.i(tag, mDeson);
		Log.i(tag, mDesoff);
	}
	/**
	 * 判断是否开启的方法
	 * @returnn 返回当前SettingItemView是否选中状态，true开启(checkBox.isChecked()==true)，false关闭
	 */
	public boolean isCheck(){
		return cb_box.isChecked();
	}
	/**
	 * @param isCheck 是否作为开启的变量，由点击过程中传递
	 */
	public void setCheck(boolean isCheck){
		//当前条目在选择的过程中，cb_box的选中状体也跟随isCheck变化
		cb_box.setChecked(isCheck);
		if(isCheck)
		{
			//开启
			tv_dec.setText(mDeson);
		}
		else {
			//关闭
			tv_dec.setText(mDesoff);
		}
	}



}
