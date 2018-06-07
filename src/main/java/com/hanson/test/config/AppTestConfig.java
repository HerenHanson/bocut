package com.hanson.test.config;
/**
 * 
 * 保存测试环境的一些配置参数，具体配置在properties文件中
 * 也可以将该类定义为接口
 */
public class AppTestConfig {
	public static String outputPath;
	public static boolean testOnStartup=true;
	public static String urlPrefix;
	public static String appcodeKey="appcode";
	public static String appmsgKey = "appmsg";
	
	public static String appName;
	public static String appVersion;
	public static String tester;
	
}
