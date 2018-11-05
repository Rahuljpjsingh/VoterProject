package com.shashi.shekher.voter_list;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;

public class VoterListUsingPDFxStreame {

	public static void main(String[] args) throws IOException {

		String FILE_NAME = "E:/study/test/data/FinalRoll_ACNo_117PartNo_203.pdf";
		File f = new File(FILE_NAME);
		System.out.println("Size of file is " + f.length());

		 StringWriter text = new StringWriter();
		Document pdf = PDF.open("E:/study/test/data/FinalRoll_ACNo_117PartNo_203.pdf");
		 pdf.pipe(new OutputTarget(text));
		 String pdfText = text.toString();
		 BufferedWriter bw = null;
		 FileWriter fw = null;
		 fw = new FileWriter("E:/study/test/abcde.txt");
		 bw = new BufferedWriter(fw);
		 bw.write(pdfText);
		 System.out.println("Done");

	}

}
