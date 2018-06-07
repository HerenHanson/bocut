package com.hanson.test.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

public class POIUtil {
	

	
	

	
	  /**
	   * @Description: 保存文档
	   */
	  public static void saveDocument(XWPFDocument document, String savePath)
	      throws Exception {
	    FileOutputStream fos = new FileOutputStream(savePath);
	    document.write(fos);
	    fos.close();
	  }
	
	
	
	
	 /**
	   * 
	   * @Description: 得到Cell的CTTcPr,不存在则新建
	   */
	  public static CTTcPr getCellCTTcPr(XWPFTableCell cell) {
	    CTTc cttc = cell.getCTTc();
	    CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
	    return tcPr;
	  }
	  
	  
	  /**
	   * @Description: 跨列合并
	   */
	  public static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell,
	      int toCell) {
	    for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
	      XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
	      if (cellIndex == fromCell) {
	        // The first merged cell is set with RESTART merge value
	        getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.RESTART);
	      } else {
	        // Cells which join (merge) the first one,are set with CONTINUE
	        getCellCTTcPr(cell).addNewHMerge().setVal(STMerge.CONTINUE);
	      }
	    }
	  }

	  /**
	   * @Description: 跨行合并
	   * @see http://stackoverflow.com/questions/24907541/row-span-with-xwpftable
	   */
	  public static void mergeCellsVertically(XWPFTable table, int col, int fromRow,
	      int toRow) {
	    for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
	      XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
	      if (rowIndex == fromRow) {
	        // The first merged cell is set with RESTART merge value
	        getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.RESTART);
	      } else {
	        // Cells which join (merge) the first one,are set with CONTINUE
	        getCellCTTcPr(cell).addNewVMerge().setVal(STMerge.CONTINUE);
	      }
	    }
	  }
	  
	  
	  public static  void setCellText1(XWPFTableCell cell,String text, String bgcolor, int width) {
	        CTTc cttc = cell.getCTTc();  
	        CTTcPr cellPr = cttc.addNewTcPr();  
	        cellPr.addNewTcW().setW(BigInteger.valueOf(width));  
	        //cell.setColor(bgcolor);  
	        CTTcPr ctPr = cttc.addNewTcPr();  
	        CTShd ctshd = ctPr.addNewShd();  
	        ctshd.setFill(bgcolor);  
	        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);  
	       // cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);  
	        cell.setText(text);        
	        
	    }  
	  
	  

	  

	  
	  
	  /**
	   * @Description: 得到XWPFRun的CTRPr
	   */
	  public static CTRPr getRunCTRPr(XWPFParagraph p, XWPFRun pRun) {
	    CTRPr pRpr = null;
	    if (pRun.getCTR() != null) {
	      pRpr = pRun.getCTR().getRPr();
	      if (pRpr == null) {
	        pRpr = pRun.getCTR().addNewRPr();
	      }
	    } else {
	      pRpr = p.getCTP().addNewR().addNewRPr();
	    }
	    return pRpr;
	  }
	  
	  
	  /**
	   * @Description: 得到段落CTPPr
	   */
	  public static CTPPr getParagraphCTPPr(XWPFParagraph p) {
	    CTPPr pPPr = null;
	    if (p.getCTP() != null) {
	      if (p.getCTP().getPPr() != null) {
	        pPPr = p.getCTP().getPPr();
	      } else {
	        pPPr = p.getCTP().addNewPPr();
	      }
	    }
	    return pPPr;
	  }
	  
	  /**
	   * @Description: 得到单元格第一个Paragraph
	   */
	  public static XWPFParagraph getCellFirstParagraph(XWPFTableCell cell) {
	    XWPFParagraph p;
	    if (cell.getParagraphs() != null && cell.getParagraphs().size() > 0) {
	      p = cell.getParagraphs().get(0);
	    } else {
	      p = cell.addParagraph();
	    }
	    return p;
	  }
	  
	  /**
	   * @Description: 得到CTTrPr,不存在则新建
	   */
	  public static CTTrPr getRowCTTrPr(XWPFTableRow row) {
	    CTRow ctRow = row.getCtRow();
	    CTTrPr trPr = ctRow.isSetTrPr() ? ctRow.getTrPr() : ctRow.addNewTrPr();
	    return trPr;
	  }
	  
	  
	  
	  public static  void setCellText(XWPFTableCell cell,String text, String bgcolor, int width,int fontSize,ParagraphAlignment align) {  

	        CTTc cttc = cell.getCTTc();  
	        CTTcPr cellPr = cttc.addNewTcPr();  
	        cellPr.addNewTcW().setW(BigInteger.valueOf(width));  	        
  
	        CTTcPr ctPr = cttc.addNewTcPr();  
	        CTShd ctshd = ctPr.addNewShd();  
	        ctshd.setFill(bgcolor);  

	        XWPFParagraph paragraph = cell.addParagraph();
	        
	        paragraph.setAlignment(align);
	        paragraph.setVerticalAlignment(TextAlignment.TOP);
	        XWPFRun run = paragraph.createRun();
	       if(fontSize!=0){
	    	   run.setFontSize(fontSize);
	       }	        		
	        run.setText(text);	   
	    }  
	
	  public static  void setCellText(XWPFTableCell cell,String text, String bgcolor, int width) {
		  setCellText( cell, text,  bgcolor,  width,0,ParagraphAlignment.CENTER);
	  }
	  
	  
	  
	  
	  

}
