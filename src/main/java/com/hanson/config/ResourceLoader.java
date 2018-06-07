
/*
 * 文件名：ResourceLoader.java
 * 版权：Copyright by www.rsrtech.net
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年9月12日
 */

package com.hanson.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:test-config.xml")
public class ResourceLoader
{
    
}

