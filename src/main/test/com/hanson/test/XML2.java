package com.hanson.test;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

public class XML2 {
	
	Log logger = LogFactory.getLog(XML2.class);
	   @Test
	   public final void testDom4jOutput() throws DocumentException, IOException {

//	      String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><COLS><TASK_ID><![CDATA[123]]></TASK_ID><TASK_NAME><![CDATA[资格审核]]></TASK_NAME></COLS>";
		   String xmlStr = "<xml><response>1111</response><res><id>1</id></res></xml>";
			
		   String encoding = "UTF-8";
	      Document doc = DocumentHelper.parseText(xmlStr);

	      StringWriter writer = new StringWriter();
	      OutputFormat format = OutputFormat.createPrettyPrint();
	      format.setEncoding(encoding);
	     
	      XMLWriter xmlwriter = new XMLWriter(writer, format);
	      xmlwriter.write(doc);
	     
	      logger.info("asXML():\n" + doc.asXML());
	      logger.info("prettyPrint():\n" + writer.toString());
	   }
}
