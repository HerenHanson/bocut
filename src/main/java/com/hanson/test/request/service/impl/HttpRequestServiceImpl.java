package com.hanson.test.request.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

import com.hanson.test.request.service.IRequestService;
import com.hanson.test.util.ValidateUtils;

//@Component("defaultRequestService")
public class HttpRequestServiceImpl implements IRequestService {

	@Override
	public String doRequest(String requestUrl, String type, String param)throws Exception {
		StringBuilder sb = new StringBuilder(); 
			if(requestUrl.indexOf("?")>0){
				String _ru = requestUrl.substring(0, requestUrl.indexOf("?")+1);
				String _param = requestUrl.substring(requestUrl.indexOf("?")+1);
				for(String arg : _param.split("&")){
					_ru+=arg.substring(0, arg.indexOf("=")+1)+encode(arg.substring(arg.indexOf("=")+1))+"&";
				}
				requestUrl = _ru;
			}
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(type);
			conn.setRequestProperty("Content-Type","application/json");
//		    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
			conn.setConnectTimeout(5*1000);//超时5s
			conn.setDoOutput(true);  
			if ("GET".equalsIgnoreCase(type))  
				conn.connect();  
			  // 当有数据需要提交时  
            if (null != param) {  
            	 OutputStream outputStream = conn.getOutputStream();  
                 // 注意编码格式，防止中文乱码  
                 outputStream.write(param.getBytes("UTF-8"));  
                 outputStream.close(); 
            } 
            // 将返回的输入流转换成字符串  
            InputStream inputStream = conn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                sb.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            conn.disconnect();  
            return sb.toString();
		
	}
	private static String encode(String p) throws UnsupportedEncodingException{
		
		if(ValidateUtils.isNotEmpty(p)){
			String pa =  URLEncoder.encode(p, "UTF-8");
			return pa;
		}
		return "";
	}

}
