package com.hanson.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.hanson.test.util.FormatUtils;

public class xml {

	@Test
	public void doit(){
		String xml = "<xml><response>1111</response><res><id>1</id></res><get><k>v</k></get></xml>";
		String json  = "[{id:1}]";
		System.out.println(FormatUtils.isJSON(json));
	}
	public void handler(){
		String xml = "<xml><response>1111</response><res><id>1</id></res><get><k>v</k></get></xml>";
		Pattern xmlKey = Pattern.compile("<(\\w+)>|</(\\w+)>");
		Matcher mat = xmlKey.matcher(xml);
		List<String> list = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();
		StringBuilder sb = new StringBuilder();
		int indent=0;
		String beforeKey =null;
		while(mat.find()){
			String key1= mat.group(1);
			String key2 = mat.group(2);
			if(null!=key1){
				list.add(key1);
			}else{
				list.add(key2);
			}
		}
		beforeKey = list.get(0);
		
		stack.push(beforeKey);
		
		for(int i=1;i<list.size();i++){
			String key = list.get(i);
			String temp = stack.pop();
			if(key.equals(temp)){
				Pattern  p = getXMLPattern(key);
				Matcher m = p.matcher(xml);
				if(m.find()){
					addIndentBlank(sb, indent);
					sb.append(m.group()).append("\n");
				}else{
				//	indent--;
					addIndentBlank(sb, indent);
					sb.append("</").append(key).append(">\n");
				}
				indent--;
			}else{
				addIndentBlank(sb, indent);
				if(beforeKey!=null){
					sb.append("<").append(temp).append(">\n");
					beforeKey=null;
				}else{
					beforeKey=temp;
				}
				stack.push(temp);
				stack.push(key);
				indent++;
//				if(temp.equals(beforeKey)){
//					indent--;
//					stack.push(key);
//					addIndentBlank(sb, indent);
//					sb.append("</").append(temp).append(">\n");
//				}else{
//					indent++;
//					
//					stack.push(temp);
//					stack.push(key);
//					addIndentBlank(sb, indent);
//					sb.append("<").append(beforeKey).append(">\n");
//					beforeKey=key;
//				}
				
				
			}
			
		}
		System.out.println(stack.size());
		System.out.println(sb);
	}
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('t');
        }
    }
    
    
	private Pattern getXMLPattern(String key){
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(key).append(">");
		sb.append("[\\<\\!\\[CDATA\\[]*[^>]+[\\]\\]\\>]*");
		sb.append("</").append(key).append(">");
		return Pattern.compile(sb.toString());
	}
}
