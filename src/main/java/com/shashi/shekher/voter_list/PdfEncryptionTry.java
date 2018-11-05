package com.shashi.shekher.voter_list;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDestinationDictionary;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.Matrix;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PdfEncryptionTry {
	public static void main(String[] args) throws IOException {

		String FILE_NAME = "E:/study/test/abc.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Voter List");
		XSSFSheet sheet2 = workbook.createSheet("Last Name Frequency");
		int rowNo = 0;
		try (PDDocument document = PDDocument.load(new File("E:/study/test/abc.pdf"))) {

			document.getClass();
			PDDocumentCatalog catlog= document.getDocumentCatalog();
			PDAcroForm form= catlog.getAcroForm();
			PDDocumentOutline documentoutline= catlog.getDocumentOutline();
			String language=catlog.getLanguage();
			PDMetadata metadata= catlog.getMetadata();
			PDDocumentNameDictionary dictionary= catlog.getNames();
			
			Map<String,Integer> lastNameMap=new LinkedHashMap<String,Integer>();

			if (!document.isEncrypted()) {
				PDFTextStripper tStripper = new PDFTextStripper();
				tStripper.setStartPage(3);
			
				String pdfFileInText = tStripper.getText(document);
//				BufferedWriter bw = null;
//				FileWriter fw = null;
				try {
					// split by whitespace
					Row row = sheet.createRow(rowNo++);
					row.createCell(0).setCellValue("S.No");
					row.createCell(1).setCellValue("House No");
					row.createCell(2).setCellValue("UID");
					row.createCell(3).setCellValue("Father's First Name");
					row.createCell(4).setCellValue("Father's Middle Name");
					row.createCell(5).setCellValue("Father's Last Name");
					row.createCell(6).setCellValue("Husband First Name");
					row.createCell(7).setCellValue("Husband Middle Name");
					row.createCell(8).setCellValue("Husband Last Name");
					row.createCell(9).setCellValue("Voter First Name");
					row.createCell(10).setCellValue("Voter Middle Name");
					row.createCell(11).setCellValue("Voter Last Name");
					row.createCell(12).setCellValue("Gender");
					row.createCell(13).setCellValue("Age");
					String lines[] = pdfFileInText.replaceAll("उम : \\d{2}-\\d{2}-\\d{4} कल ससदरभत आखप कपल पकष ष \\d{2} कर \\d", "ननरररचक कर नरम :").split("ननरररचक कर नरम :");
//					fw = new FileWriter("E:/study/test/abc.txt");
//					bw = new BufferedWriter(fw);
					for (String line : lines) {
						int result=createVoterDetails(sheet, rowNo, line,lastNameMap);
						if(result==1)
						rowNo++;
//						 bw.write(line);
						 //System.out.println(line+System.lineSeparator()+System.lineSeparator());

					}
					rowNo=0;
					Row freqRow = sheet2.createRow(rowNo++);
					freqRow.createCell(0).setCellValue("Last Names");
					freqRow.createCell(1).setCellValue("Frequency");
					for(String lastName:lastNameMap.keySet()){
						Row freqRow1 = sheet2.createRow(rowNo++);
						freqRow1.createCell(0).setCellValue(lastName);
						freqRow1.createCell(1).setCellValue(lastNameMap.get(lastName));
					}
					
					
					try {
			            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
			            workbook.write(outputStream);
			            workbook.close();
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
					System.out.println("Done");
					//bw.write(pdfFileInText);
					// System.out.println("Done2");
				} finally {
					document.close();
				}

			}

		}


	}

	private static int createVoterDetails(XSSFSheet sheet, int rowNo, String line, Map<String, Integer> lastNameMap) {
		String data[] = line.split("\\r?\\n");
		if(data.length!=10){
			return -1;
		}
		if(!StringUtils.equals(StringUtils.trimToEmpty(data[6]), "पपरर") && !StringUtils.equals(StringUtils.trimToEmpty(data[6]), "मनहलर")){
			return -1;
		}
		Row row = sheet.createRow(rowNo);
		row.createCell(0).setCellValue(StringUtils.trimToEmpty(data[9]));
		row.createCell(1).setCellValue(StringUtils.trimToEmpty(data[7]));
		row.createCell(2).setCellValue(StringUtils.trimToEmpty(data[3]));
		boolean father = data[5].contains("नपतर कर नरम : ");
		if (father) {
			String fatherData[] = data[5].replace("नपतर कर नरम : ", "").split(" ");
			setNameData(row, fatherData, 3, 4,lastNameMap);
		} else {
			String husbandData[] = data[5].replace("पनत कर नरम : ", "").split(" ");
			setNameData(row, husbandData, 6, 8,lastNameMap);
		}
		String name[] = data[4].split(" ");
		setNameData(row, name, 9, 11,lastNameMap);

		if (StringUtils.equals(StringUtils.trimToEmpty(data[6]), "पपरर")) {
			row.createCell(12).setCellValue("Male");
		} else if (StringUtils.equals(StringUtils.trimToEmpty(data[6]), "मनहलर")) {
			row.createCell(12).setCellValue("Female");
		} else {
			row.createCell(12).setCellValue("Others");
		}
		row.createCell(13).setCellValue(StringUtils.trimToEmpty(data[8]));
		
		return 1;

	}

	private static void setNameData(Row row, String[] nameData, int startIndex, int endIndex, Map<String, Integer> lastNameMap) {
		if (nameData.length == 2) {
			row.createCell(startIndex++).setCellValue(nameData[0]);
			row.createCell(startIndex++).setCellValue(StringUtils.EMPTY);
			row.createCell(startIndex).setCellValue(nameData[1]);
			if(lastNameMap.get(nameData[1])!=null){
				lastNameMap.put(nameData[1], lastNameMap.get(nameData[1])+1);	
			}else{
				lastNameMap.put(nameData[1],1);	
			}
		} else {

			for (String data : nameData) {
				if(startIndex<=endIndex){
					row.createCell(startIndex++).setCellValue(data);	
				}
			}
			while (startIndex <= endIndex) {
				row.createCell(startIndex++).setCellValue(StringUtils.EMPTY);
			}
			if(nameData.length>2){
				if(lastNameMap.get(nameData[2])!=null){
					lastNameMap.put(nameData[2], lastNameMap.get(nameData[2])+1);	
				}else{
					lastNameMap.put(nameData[2],1);	
				}
			}
		}

	}
}
