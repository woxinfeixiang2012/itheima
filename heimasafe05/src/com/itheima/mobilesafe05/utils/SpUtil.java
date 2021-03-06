package com.itheima.mobilesafe05.utils;

import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
  private static SharedPreferences sp;

	/**
	 * 写入boolean变量至sp中
	 * @param ctx 上下文环境
	 * @param key 存储节点名称
	 * @param value 存储节点的值boolean
	 */
	public static void putBoolean(Context ctx,String key,boolean value){
		if(sp==null)
		{
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 读取boolean变量从sp中
	 * @param ctx 上下文环境
	 * @param key 存储节点名称
	 * @param defValue 没有此节点默认值
	 * @param defValue 默认值或者此节点读取到的结果
	 * @return
	 */
	public static boolean getBoolean(Context ctx,String key,boolean defValue){
		if(sp==null)
		{
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}
	/**
	 * 写入String变量到sp中
	 * @param ctx
	 * @param key
	 * @param value
	 */
	public static void putString(Context ctx,String key,String value){
		if(sp==null)
		{
			sp=ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putString(key,value).commit();
	}
	/**
	 * 读取String变量从sp中
	 * @param ctx
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context ctx,String key,String defValue){
		if(sp==null)
		{
			sp=ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}

	/**
	 * 从Sp中移除指定节点key
	 * @param ctx 上下文环境
	 * @param key 需要移除节点的名称
	 */
	public static void remove(Context ctx, String key) {
		if(sp==null)
		{
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().remove(key).commit();
	}
}
