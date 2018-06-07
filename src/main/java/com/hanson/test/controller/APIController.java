package com.hanson.test.controller;

import com.alibaba.fastjson.JSON;
import com.hanson.test.config.AppTestConfig;
import com.hanson.test.domain.BaseParamDomain;
import com.hanson.test.domain.TestBeanComparator;
import com.hanson.test.domain.TestCaseDomain;
import com.hanson.test.domain.TestDomain;
import com.hanson.test.handler.NoComparator;
import com.hanson.test.handler.TestOnStartup;
import com.hanson.test.loader.AppTestResourcesLoader;
import com.hanson.test.output.TestWordReport;
import com.hanson.test.util.DateUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.*;


@Controller
@RequestMapping("/test")
public class APIController {

	@Resource
	private TestOnStartup beginTest;
	@RequestMapping("/")
	public String index(HttpServletRequest request){
		ApplicationContext context =
				AppTestResourcesLoader.getContext();
		String[] beanNames  = context.getBeanNamesForType(TestDomain.class);
//		List<Map<String, Object>> list = 
//				new ArrayList<Map<String,Object>>(beanNames.length);
//		
		Set<TestBeanComparator> set = new TreeSet<TestBeanComparator>(new NoComparator());
		for(String beanName:beanNames){
			TestBeanComparator bean  = new TestBeanComparator();
			bean.setBeanName(beanName);
			TestDomain testConfigation = context.getBean(beanName,TestDomain.class);
			String result = testConfigation.getResult();
			bean.setTestConfig(testConfigation);
			set.add(bean);
		}
		request.setAttribute("list", set);
		return "index";
	}
	
	/**
	 * 去测试api设置参数页面
	 * @param beanName
	 * @return
	 */
	@RequestMapping(value="/toTestSingleApi",method=RequestMethod.GET)
	@ResponseBody
	public String toTestSingleApi(String beanName){
		Map map =new HashMap();
		
		ApplicationContext context = AppTestResourcesLoader.getContext();
		TestDomain testConfigation = context.getBean(beanName,TestDomain.class);
		List<BaseParamDomain> reqList = testConfigation.getRequest();
		map.put("configuration", testConfigation);
		map.put("beanName", beanName);
		map.put("success", true);
		return JSON.toJSONString(map);
	}
	/**
	 * 测试Api接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/testSingleApi",method=RequestMethod.POST)
	@ResponseBody
	public String TestSingleApi(HttpServletRequest request){
		Map nmap = reqMapToMap(request);
		Map re = new HashMap();	
		TestDomain testConfigation = (TestDomain) AppTestResourcesLoader.getBean((String)nmap.get("beanName"));
		nmap.remove("beanName");
		
		TestCaseDomain testcase = testConfigation.getTestCases().get(0);
		testcase.setParam(nmap);
		boolean flag = testConfigation.doTest(testcase);
		testConfigation.setResult(testcase.getResult());
		re.put("flag", flag);
		re.put("result", testcase.getResult());

		re.put("success", true);
		return JSON.toJSONString(re);
	}
	
	@RequestMapping("/output")
	public String output(HttpServletRequest request,String docType,HttpServletResponse response){
		String fileName="";
		Set<TestBeanComparator> set = new TreeSet<TestBeanComparator>();
		List<TestDomain> list = getConfigurationList();
		
		if("docAll".equals(docType)){
			fileName="所有测试用例";
		}else if("docError".equals(docType)){
			fileName="未通过的测试用例";
		}else{
			fileName="需人工审核的用例";
		}
		
		for (TestDomain configuration : list) {
			TestBeanComparator bean  = new TestBeanComparator();
			bean.setTestConfig(configuration);
			bean.statisTestResult();
			if("docError".equals(docType)){
				if(bean.getErrorCount()>0){
					set.add(bean);
					
				}
			}else if("docJudge".equals(docType)){
				if(bean.getJudgeCount()>0){
					set.add(bean);
				}
			}else{
				set.add(bean);
			}
		}
		TestWordReport word = new TestWordReport();
		word.setTitle(fileName);
		word.setProjectInfo(AppTestConfig.appName, AppTestConfig.appVersion, AppTestConfig.tester);
		
		for(TestBeanComparator bean : set){
			word.createConfigation(bean.getTestConfig(),null);
			for(TestCaseDomain testCase : bean.getTestConfig().getTestCases()){
				if("docError".equals(docType)){
					if(!testCase.isSuccess()){
						word.createCase(testCase);
					}
				}else if("docJudge".equals(docType)){
					if(testCase.isJudge()){
						word.createCase(testCase);
					}
				}else{
					word.createCase(testCase);
				}
			}
		}
		
		try {
			fileName=new String((fileName+DateUtils.getCurDate()+".docx").getBytes("utf-8"),"ISO8859-1");
			
			
//		File file = new File(fileName);
			response.addHeader("Content-Disposition","attachment;filename="+fileName);
//		response.addHeader("Content-Length","12" );
			
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/msword;charset=UTF-8");
			
			
			word.getDocument().write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	@RequestMapping("/testAll")
	public String testAll(HttpServletRequest request,String docType,String retest ){
		if("new".equals(retest)){
			beginTest.startTest(AppTestResourcesLoader.getContext());
		}
		Set<TestBeanComparator> set = new TreeSet<TestBeanComparator>();
		List<TestDomain> list = getConfigurationList();
		/*
		 * 获取文档
		 */
		for (TestDomain configuration : list) {
			TestBeanComparator bean  = new TestBeanComparator();
			bean.setTestConfig(configuration);
			bean.statisTestResult();
			if("docError".equals(docType)){
				if(bean.getErrorCount()>0){
					set.add(bean);
					
				}
			}else if("docJudge".equals(docType)){
				if(bean.getJudgeCount()>0){
					set.add(bean);
				}
			}else{
				set.add(bean);
			}
		}
		request.setAttribute("configList", set);
		
		return "report";
	}
	
	/**
	 * 得到所有TestConfigationDomain对象
	 * @return
	 */
	public List<TestDomain> getConfigurationList(){
		List<TestDomain> list = new ArrayList<TestDomain>();
		
		ApplicationContext context = AppTestResourcesLoader.getContext();
		String[] beanNames = context.getBeanNamesForType(TestDomain.class);
		for(String beanName:beanNames){
			TestDomain configuration = context.getBean(beanName, TestDomain.class);
			list.add(configuration);
		}	
		return list;
	}
	/**
	 * 将 request请求中的Map转换成可用Map
	 * @param request
	 * @return
	 */
	public Map reqMapToMap(HttpServletRequest request){
		Map map = request.getParameterMap();
		
		Map nmap = new HashMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			if(value!=""){
				nmap.put(key, value);
			}
		}
		
		return nmap;		
	}
}
