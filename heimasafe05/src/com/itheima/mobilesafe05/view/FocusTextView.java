package com.itheima.mobilesafe05.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * @author liwan
 * 能够获取焦点的自定义TextView
 */
public class FocusTextView extends TextView {

	//使用在通过java代码new的方法创建控件
	public FocusTextView(Context context) {
		super(context);
	}
    //由系统xml布局文件调用（带属性+上下文环境构造)
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
    //由系统xml布局文件调用（带属性+上下文环境构造+布局文件中自定义样式构造方法)
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	//重写获取焦点的方法,由系统调用，调用的时候默认就能获取焦点
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}
}
