package com.hanson.test.output;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import com.hanson.test.domain.TestCaseDomain;
import com.hanson.test.domain.TestDomain;
import com.hanson.test.util.DateUtils;
import com.hanson.test.util.POIUtil;

public class TestWordReport {

//	private String version="1.0";
//	private String author="tester";
	private XWPFDocument document;
	private CTStyles styles;
	public TestWordReport() {
		String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        // 读取模板文档 
		try {
			document = new XWPFDocument();
			XWPFDocument fomat = new XWPFDocument(new FileInputStream(filePath+"format.docx"));  
			styles = fomat.getStyle();
		}catch(XmlException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public XWPFDocument getDocument() {
		return document;
	}
	
	public void setTitle(String title){
		setTitle(title, 20, true);
	}
	public void setTitle(String title,int fontSize,boolean blod){
		XWPFStyles newStyles = document.createStyles(); 
		newStyles.setStyles(styles); 
				
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setStyle("1");
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		
		XWPFRun run = paragraph.createRun();
		
		run.setBold(blod);
		run.setFontSize(fontSize);
		run.setText(title);
	}
	
	public void setProjectInfo(String appName,String version,String author){
		String date = DateUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		XWPFTable table = document.createTable(2,4);

		CTTblPr tablePr = table.getCTTbl().addNewTblPr();
		tablePr.addNewJc().setVal(STJc.RIGHT);
		
		POIUtil.setCellText(table.getRow(0).getCell(0),"项目名称","D4DBED",2200);
		POIUtil.setCellText(table.getRow(0).getCell(1),appName,"D4DBED",2000);
		POIUtil.setCellText(table.getRow(0).getCell(2),"版本号","D4DBED",2000);
		POIUtil.setCellText(table.getRow(0).getCell(3),version,"D4DBED",2000);
		

		POIUtil.setCellText(table.getRow(1).getCell(0),"编制人","D4DBED",2000);
		POIUtil.setCellText(table.getRow(1).getCell(1),author,"D4DBED",2000);
		POIUtil.setCellText(table.getRow(1).getCell(2),"编制时间","D4DBED",2000);
		POIUtil.setCellText(table.getRow(1).getCell(3),date,"D4DBED",2000);

	}
	/**
	 * 创建文档接口标题
	 * @param doc
	 * @param testConfigationDomain
	 */
	public  void createConfigation(TestDomain testConfigationDomain,String word){		
		
		String desc = testConfigationDomain.getDesc();
		String title = testConfigationDomain.getTitle();
		String url = testConfigationDomain.getUrl();
		String code = testConfigationDomain.getNo();
		
		
		

				
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setStyle("1");
		XWPFRun run = paragraph.createRun();		
		run.setText(code+title);
		
		
		XWPFTable table = document.createTable(3,4);
		CTTblPr tablePr = table.getCTTbl().addNewTblPr();
		CTTblWidth tblWidth = tablePr.isSetTblW() ? tablePr.getTblW() : tablePr  
                .addNewTblW();  
		
		//表格宽度
		tblWidth.setW(BigInteger.valueOf(8000));
		tblWidth.setType(STTblWidth.DXA);  
		
		POIUtil.setCellText(table.getRow(0).getCell(0),"功能模块名","D4DBED",2200);
		POIUtil.setCellText(table.getRow(0).getCell(1),title,"D4DBED",2000);
		POIUtil.setCellText(table.getRow(0).getCell(2),"功能编号","D4DBED",2000);
		POIUtil.setCellText(table.getRow(0).getCell(3),code,"D4DBED",2000);
		
		POIUtil.mergeCellsHorizontal(table, 1, 1, 3);
		POIUtil.setCellText(table.getRow(1).getCell(0),"访问路径","D4DBED",2000);
		POIUtil.setCellText(table.getRow(1).getCell(1),url,"D4DBED",2000);
		POIUtil.mergeCellsHorizontal(table, 2, 1, 3);
		POIUtil.setCellText(table.getRow(2).getCell(0),"接口描述","D4DBED",2000);
		POIUtil.setCellText(table.getRow(2).getCell(1),desc,"D4DBED",2000);
		
	}
	
	public void rmConfiguration(){
		List<XWPFTable> tableList = document.getTables();
		XWPFTable table = tableList.get(tableList.size()-1);
		
		List<XWPFTableRow> rows = table.getRows();
		table.removeRow(rows.size()-1);
		table.removeRow(rows.size()-1);
		
		/*List<IBodyElement> elements=doc.getBodyElements();
		doc.removeBodyElement(elements.size()-1);*/
		
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		
		XWPFParagraph paragraph = paragraphs.get(paragraphs.size()-1);
		paragraph.setStyle("");
		paragraph.removeRun(0);
		}
	
	
	/**
	 * 生成测试报告部分
	 * @param doc
	 * @param caseParam
	 */
	public  void createCase(TestCaseDomain caseParam){
				
		
		XWPFTable table = document.createTable(6,4);
		
			
		POIUtil.setCellText(table.getRow(0).getCell(0),"编号","FFFFFF",2200);
		POIUtil.setCellText(table.getRow(0).getCell(1),caseParam.getCode(),"FFFFFF",2000);
		POIUtil.setCellText(table.getRow(0).getCell(2),"是否正确","FFFFFF",2000);
		POIUtil.setCellText(table.getRow(0).getCell(3),caseParam.isSuccess()+"","FFFFFF",2000);
		
		
		
		POIUtil.setCellText(table.getRow(1).getCell(0),"人工校验","FFFFFF",2200);
		POIUtil.setCellText(table.getRow(1).getCell(1),caseParam.isJudge()+"","FFFFFF",2000);
//		POIUtil.setCellText(table.getRow(1).getCell(2),"需要token","FFFFFF",2000);
//		POIUtil.setCellText(table.getRow(1).getCell(3),caseParam.getToken(),"FFFFFF",2000);
//		
		
		POIUtil.mergeCellsHorizontal(table, 3, 1, 3);
		POIUtil.setCellText(table.getRow(3).getCell(0),"目的","FFFFFF",2000);
		POIUtil.setCellText(table.getRow(3).getCell(1),caseParam.getDesc(),"FFFFFF",2000);
		
		POIUtil.mergeCellsHorizontal(table, 4, 0, 3);		
		
		POIUtil.setCellText(table.getRow(4).getCell(0),"请求数据:","FFFFFF",2000);
		POIUtil.setCellText(table.getRow(4).getCell(0),caseParam.getRequestParam(),"FFFFFF",2000,9,ParagraphAlignment.LEFT);
	

		
		
		POIUtil.mergeCellsHorizontal(table, 5, 0, 3);
		
		POIUtil.setCellText(table.getRow(5).getCell(0),"返回数据:","FFFFFF",2000);
		
		POIUtil.setCellText(table.getRow(5).getCell(0),caseParam.getResult(),"FFFFFF",6000,9,ParagraphAlignment.LEFT);
	

		
		
		

	}
	
	
	
	
	
}
