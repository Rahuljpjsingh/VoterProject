package com.shashi.shekher.voter_list;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Collection;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.layout.Image;

public class UsingCoreJava {
	public static void main(String arg[]) throws IOException{
		String FILE_NAME = "E:/study/test/FinalRoll_ACNo_117PartNo_202.pdf";
		InputStream is = new FileInputStream(FILE_NAME);
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		StringWriter text = new StringWriter();
		 String pdfText = text.toString();
		BufferedWriter bw = null;		
		FileWriter fw = null;
					fw = new FileWriter("E:/study/test/abcde.txt");
					bw = new BufferedWriter(fw);
					String line=br.readLine();
					while(line!=null){
						bw.write(line.getBytes("UTF-32").toString());
					line =br.readLine();
					}
					
					System.out.println("Done");
	}
}
