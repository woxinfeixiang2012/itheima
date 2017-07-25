package com.itheima.mobilesafe05.receiver;

import com.itheima.mobilesafe05.utils.ConstValue;
import com.itheima.mobilesafe05.utils.SpUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//1,判断是否开启了防盗保护
		Boolean open_security = SpUtil.getBoolean(context, ConstValue.OPEN_SECURITY, false);
		if (open_security) {
			//2,获取短信内容
		   Object[] objects = (Object[])intent.getExtras().get("pdus");
		   //3,循环遍历短信过程
		   for (Object object : objects) {
			 //4,获取短信对象
			 SmsMessage sms = SmsMessage.createFromPdu((byte[])object);
			 //5,获取短信对象的基本信息
			String originatingAddress = sms.getOriginatingAddress();
			String messageBody = sms.getMessageBody();
			//6,判断是否包含播放音乐的关键字
			if(messageBody.contains("#*alarm*#")){
				//7,播放音乐（准备音乐,MediaPlay）
				
				
			}
			   
		   }
		}

	}

}
