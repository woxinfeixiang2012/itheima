package com.itheima.mobilesafe05.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.itheima.heimasafe05.R;
import com.itheima.mobilesafe05.utils.ConstValue;
import com.itheima.mobilesafe05.utils.SpUtil;
import com.itheima.mobilesafe05.utils.StreamUtil;
import com.itheima.mobilesafe05.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author liwan
 *
 */
public class SplashActivity extends Activity {
    protected static final String tag = "SplashActivity";
    /*
     * 更新版本的状态码
     */
	protected static final int UPDATE_VERSION = 100;
	/*
	 * 进入应用程序主界面的状态码
	 */
	protected static final int ENTER_HOME = 101;
	/*
	 * url地址出错状态码
	 */
	protected static final int URL_ERROR = 102;
	/*
	 * io异常状态码
	 */
	protected static final int IO_ERROR = 103;
	/*
	 * json解析异常状态码
	 */
	protected static final int JSON_ERROR = 104;
	
	private TextView tv_version_name;
	private RelativeLayout rl_rootLayout;
	private int mLocalVersionCode;
	private String mVersionDec;
	private String mDownLoadUrl;

	private Handler mHandler=new Handler(){
		
		public void handleMessage(Message msg){
			switch (msg.what) {
			case UPDATE_VERSION:
				//弹出对话框提示用户更新
				showUpdateDialog();
				break;
			case ENTER_HOME:
				//进入应用程序主界面，activity跳转过程
				enterHome();
				break;
			case URL_ERROR:
				ToastUtil.show(getApplicationContext(), "url异常");
				enterHome();
				break;
			case IO_ERROR:
				ToastUtil.show(getApplicationContext(), "读取异常");
				enterHome();
				break;
			case JSON_ERROR:
				ToastUtil.show(getApplicationContext(), "json解析异常");
				enterHome();
				break;
			default:
				break;
			}
		}
	};
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //初始化UI
        initUI();
        //初始化data
        initData();
        //初始化动画
        initAnimation();
    }

    private void initAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(3000);
		rl_rootLayout.startAnimation(alphaAnimation);
	}

	/**
     * 弹出提示用户进行更新对话框
     */
    protected void showUpdateDialog() {
    	//对话框是依赖于activity存在的，activity消失，对话框也自动消失
		Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("版本更新");
		//设置描述内容
		builder.setMessage(mVersionDec);
		//确定按钮
		builder.setPositiveButton("立即更新",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//下载apk，apk链接地址，downloadUrl
				downLoadApk();
				
			}
		});
		//取消按钮
		builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//取消对话框，进入主界面
				enterHome();				
			}
		});
		//点击返回键事件监听
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// 用户点击返回键，也能进入主界面
				dialog.dismiss();
				enterHome();
				
			}
		});
		builder.show();
		
	}

	protected void downLoadApk() {
		//apk下载链接地址，下载后放置apk的所在路径
		//1判断sd开是否可用，是否挂载上
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			//2获取sd卡的路径
			String path=Environment.getExternalStorageDirectory().getAbsolutePath()
					+File.separator+"mobilesafe05.apk";
			//3发送请求，获取apk，并且放置到指定路径下
			HttpUtils httpUtils=new HttpUtils();
			//4发送请求，传递参数(下载地址，下载后放置的位置
			httpUtils.download(mDownLoadUrl, path, new RequestCallBack<File>() {
				
				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					// 下载成功（下载过后的放置在sd卡中的apk）
					Log.i(tag, "下载成功");
					File file=responseInfo.result;
					//提示用户进行安装
					installApk(file);
				}
				
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					//下载失败
					Log.i(tag, "下载失败");

				}
				//刚开始下载
				@Override
				public void onStart() {
					super.onStart();
					Log.i(tag, "刚开始下载");

				}
				//下载过程中（下载的apk的总大小，当前的下载位置，是否正在下载）
				@Override
				public void onLoading(long total, long current,boolean isUploading) {
					super.onLoading(total, current, isUploading);
					Log.i(tag, "下载中");
					Log.i(tag, "total="+total);
					Log.i(tag, "current="+current);
				}
			});
		}
	}

	/**
	 * 安装对应apk
	 * @param file 要安装的文件
	 */
	protected void installApk(File file) {
		//系统安装应用界面
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		//文件作为数据源
		//intent.setData(Uri.fromFile(file));
		//设置安装的类型
		//intent.setType("application/vnd.android.package-archive");
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
		startActivityForResult(intent, 0);
	}
	//开启一个activity后，返回结果调用的方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		enterHome();
	}

	/*
     * splash界面进入应用程序主界面
     */
	protected void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		//在開啟的界面，將splash界面關閉(只可見一次)
		finish();
	}

	/*
	 * 获取数据
	 */
	private void initData() {
		//1，应用版本名称
		tv_version_name.setText("版本名称:"+getVersionName());
		//2，获取本地版本号
		mLocalVersionCode = getVersionCode();
		//http://www.oxxx.com/update74.json?key=value 返回200请求成功，流的
		//方式将数据读下来
		//json中内容包含：
		/*更新版本的版本名称
		 * 更新版本的描述信息
		 * 服务器版本号
		 * 新版本apk下载地址
		 */
		if(SpUtil.getBoolean(this, ConstValue.OPEN_UPDATE, false)){
			checkVersion();
		}else {
			//直接进入应用程序主界面，如果直接调用enterHome()就看不到splashActivity界面
			//如果使用Thread.sleep(4000),则会有阻塞主线程的风险，超过5秒直接程序未响应
			//try {
			//	Thread.sleep(10000);
			//} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			mHandler.sendEmptyMessageDelayed(ENTER_HOME, 4000);
		}
		
	}
	
	/*
	 * 检测版本号
	 */
	private void checkVersion() {
	 
		new Thread()
		{
			public void run(){
				//发送请求获取数据，参宿为请求json的链接地址
				//仅限于模拟器访问tomcat
				Message msg=Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					//1封装一个url地址
					URL url=new URL("http://10.0.2.2:8080/update74.json");
					//2开启一个链接
					HttpURLConnection connection=(HttpURLConnection)url.openConnection();
					//3设置常见请求参数(请求头)
					connection.setConnectTimeout(2000);//请求超时
					connection.setReadTimeout(2000);//读取超时
					//默认是get请求方式，connection.setRequestMethod(method);
                    //4获取请求成功响应码
					if(connection.getResponseCode()==200)
					{
						//5以流的方式将数据获取下来
						InputStream is=connection.getInputStream();
						//6将流转换成字符串(工具类封装)
						String json= StreamUtil.stream2String(is);
						Log.i(tag, json);
						//7解析json
						JSONObject jsonObject = new JSONObject(json);
						String versionName = jsonObject.getString("versionName");
						mVersionDec = jsonObject.getString("versionDes");
						String versionCode = jsonObject.getString("versionCode");
						mDownLoadUrl = jsonObject.getString("downloadUrl");
						
						Log.i(tag, versionName);
						Log.i(tag, mVersionDec);
						Log.i(tag, versionCode);
						Log.i(tag, mDownLoadUrl);
						//8比对版本号(服务器版本号>本地版本号提示用户进行更新)
						if(mLocalVersionCode<Integer.parseInt(versionCode))
						{
							//提示用户更新,弹出对话框(UI),要是用消息机制
							msg.what=UPDATE_VERSION;
						}else{
							//进入应用程序主界面
							msg.what=ENTER_HOME;
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					msg.what=URL_ERROR;

				} catch (IOException e) {
					e.printStackTrace();
					msg.what=IO_ERROR;

				} catch (JSONException e) {
					e.printStackTrace();
					msg.what=JSON_ERROR;
				}finally{
					//指定睡眠时间，请求网络的时长超过4秒则不作处理
					//请求网络的时长小于4秒，强制让其睡眠4秒
					long endTime = System.currentTimeMillis();
					if(endTime-startTime<4000)
					{
						try {
							Thread.sleep(4000-(endTime-startTime));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(msg);
				}
			}
		}.start();
	}


	/**
	 * @return获取应用的版本名称 null表示异常
	 */
	private String getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	 /**
	 * @return获取应用的版本号 0表示异常
	 */
	private int getVersionCode()
	{
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/*
	 * 获取UI控件
	 */
	private void initUI() {
		rl_rootLayout = (RelativeLayout) findViewById(R.id.rl_root);
		tv_version_name = (TextView)findViewById(R.id.tv_version_name);
		
	}

}
