package com.itheima.mobilesafe05.receiver;

import com.itheima.mobilesafe05.utils.ConstValue;
import com.itheima.mobilesafe05.utils.SpUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	private static final String tag = "BootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(tag, "手机重启成功，并且监听到了广播");
		// 1,获取开机后手机的sim卡的序列号
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNumber = tm.getSimSerialNumber()+"xxxxxxxxxx";
		// 2,Sp中存储的sim卡序列号
		String sim_number = SpUtil.getString(context, ConstValue.SIM_NUMBER, "");
		// 3,比对不一致
		if (!simSerialNumber.equals(sim_number)) {
			// 4发送短信给选中联系人的号码
			SmsManager sms= SmsManager.getDefault();
			sms.sendTextMessage("5556", 
					null, 
					"sim change!!!", 
					null, 
					null);
		}

	}

}
