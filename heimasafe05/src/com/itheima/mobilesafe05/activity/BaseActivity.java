package com.itheima.mobilesafe05.activity;

import com.itheima.heimasafe05.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseActivity extends Activity {
	private GestureDetector gestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//2创建手势管理的对象，用作管理OnTouchEvent(event)传递过来的手势
				gestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// 监听手势的移动
						if(e1.getX()-e2.getX()>0){
							//由右向左，移动到下一页
							showNextPage();
						}
						if(e1.getX()-e2.getX()<0){
							//由左向右，移动到上一页
							showPrePage();
						}
						return super.onFling(e1, e2, velocityX, velocityY);
					}
					
				});
	}
	protected abstract void showPrePage();
	protected abstract void showNextPage();
	public void nextPage(View view)
	{
		showNextPage();
	}
	public void prePage(View view)
	{
		showPrePage();
	}
	//1监听屏幕上响应的事件类型按下（1次)移动（多次）,抬起（1次）
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//3接收多种类型的事件，用作处理的方法
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
