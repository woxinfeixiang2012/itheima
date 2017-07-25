package com.itheima.mobilesafe05.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	/**
	 * 流转换成字符串
	 * @param is 流对象
	 * @return   流转换成的字符串，返回null表示异常
	 */
	public static String stream2String(InputStream is) {
		//1,在读去的过程中，将读取的内容存储至缓存中，然后一次性转换成字符串返回
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		//2读取流操作，读到没有为止(循环)
		byte[] buffer=new byte[1024];
		//3，记录读取内容的临时变量
		int temp=-1;
		try {
			while((temp=is.read(buffer))!=-1){
				bos.write(buffer,0,temp);
			}
			return bos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
