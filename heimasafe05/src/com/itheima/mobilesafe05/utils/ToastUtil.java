package com.itheima.mobilesafe05.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	/**
	 * @param ctx 上下文环境
	 * @param msg 打印内容
	 */
	public static void show(Context ctx,String msg){
		Toast.makeText(ctx, msg, 0).show();
	}
}
