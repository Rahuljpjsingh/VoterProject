package com.shashi.shekher.voter_list;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.asprise.util.pdf.PDFReader;

public class DataWithAspire {
	public static void main(String []arg) throws IOException{
		PDFReader reader=null;
		try {
			reader = new PDFReader(new File("E:/study/test/data/FinalRoll_ACNo_117PartNo_203.pdf"));
			reader.open(); // open the file.
			int pages = reader.getNumberOfPages();
			 
			for(int i=0; i < pages; i++) {
			   BufferedImage img = reader.getPageAsImage(i);
			   
			   // recognizes both characters and barcodes
			  // String text = new OCR().recognizeAll(image);
			   System.out.println("Page " + i + ": " + img); 
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			reader.close();
			e.printStackTrace();
		}
		 
		
		 
		
		
	}

}
