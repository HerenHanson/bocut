package com.hanson.test.loader;

import java.util.Map;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.hanson.test.config.AppTestConfig;
import com.hanson.test.util.ValidateUtils;

/**
 * 读取配置文件中的信息，写入AppTestConfig相关属性
 * @author mac
 *
 */
@Configuration
@PropertySource(value="classpath:test_config.properties")
public class AppTestConfigLoader implements EnvironmentAware{

	@Override
	public void setEnvironment(Environment env) {
		Map<String, Object> config = new RelaxedPropertyResolver(env).getSubProperties("test.");
		String urlPrefix =  (String) config.get("url.prefix");
		if(ValidateUtils.isEmpty(urlPrefix)){
			//未配置地址前缀
			System.exit(1);
		}else{
			AppTestConfig.urlPrefix = urlPrefix;
		}
		String outputPath = (String) config.get("output.path");
		if(!ValidateUtils.isEmpty(outputPath)){
			AppTestConfig.outputPath = outputPath;
		}
		Object onStartup  =config.get("onStartup");
		if(!ValidateUtils.isEmpty(onStartup)){
			AppTestConfig.testOnStartup = Boolean.valueOf((String)onStartup);
		}
		String appcodeKey = (String) config.get("appcode.key");
		if(!ValidateUtils.isEmpty(appcodeKey)){
			AppTestConfig.appcodeKey = appcodeKey;
		}
		String appmsgKey = (String)config.get("appmsg.key");
		if(ValidateUtils.isNotEmpty(appmsgKey)){
			AppTestConfig.appmsgKey = appmsgKey;
		}
		AppTestConfig.appName = (String) config.get("app.name");
		AppTestConfig.appVersion=(String)config.get("app.version");
		AppTestConfig.tester = (String) config.get("app.tester");
	}

}
