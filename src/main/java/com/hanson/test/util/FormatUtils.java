package com.hanson.test.util;

import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class FormatUtils {

	static Pattern XML_PATTERN = Pattern.compile("^<[\\s\\S]+>[\\s\\S]*</[\\s\\S]+>$");
	static Pattern JSON_PATTERN = Pattern.compile("(^\\[{1}[\\s\\S]+\\]{1}$)|(^\\{{1}[\\s\\S]+\\}{1}$)");
	
	public static String format(String data){
		 if (null == data || "".equals(data)) return "";
		if(isXML(data)){
			return formatXML(data);
		}else if(isJSON(data)){
			return formatJson(data);
		}
		return data;
		 
	}
	
	public static boolean isJSON(String data){
		Matcher mat = JSON_PATTERN.matcher(data);
		if(mat.find()){
			return true;
		}
		return false;
	}
	public static boolean isXML(String data){
		Matcher mat = XML_PATTERN.matcher(data);
		if(mat.find()){
			return true;
		}
		return false;
	}
	
	public static String formatXML(String xmlStr){
		 if (null == xmlStr || "".equals(xmlStr)) return "";
		  String encoding = "UTF-8";
		  try {
			
			  Document doc = DocumentHelper.parseText(xmlStr);
			  StringWriter writer = new StringWriter();
			  OutputFormat format = OutputFormat.createPrettyPrint();
			  format.setEncoding(encoding);
			  
			  XMLWriter xmlwriter = new XMLWriter(writer, format);
			  xmlwriter.write(doc);
			  return writer.toString();
		} catch (Exception e) {
			// TODO: handle exception
			return xmlStr;
		}
	      
	}
	public static String formatJson(String jsonStr){
		
        if (null == jsonStr || "".equals(jsonStr)) return "";
        if(jsonStr.split(",").length>60)  /*大于60个参数，就不格式化*/
    		return jsonStr;
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
	}
	/**
     * 添加space
     * @param sb
     * @param indent
     * @author  
     * @Date   
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
