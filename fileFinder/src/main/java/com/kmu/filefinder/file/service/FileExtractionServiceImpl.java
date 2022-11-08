package com.kmu.filefinder.file.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

@Service
public class FileExtractionServiceImpl implements FileExtractionService {

	@Override 
	public String extractSummaryTextByPDF(String filePath) throws IOException {
		File file = new File(filePath);

		PDDocument document = PDDocument.load(file);

		int pdfSize = document.getPages().getCount();

		PDFTextStripper pdfTextStripper = new PDFTextStripper();
		PDDocument pdDocument = new PDDocument();
		
		if (pdfSize > 3) {
			for (int i = 1; i <= 3; i++) {
				PDPage p = document.getPage(i);
				pdDocument.addPage(p);
			}
		} else {
			for (int i = 0; i < pdfSize; i++) {
				PDPage p = document.getPage(i);
				pdDocument.addPage(p);
			}
		}

		String text = pdfTextStripper.getText(pdDocument);
		document.close();

		return text;
	}
}
