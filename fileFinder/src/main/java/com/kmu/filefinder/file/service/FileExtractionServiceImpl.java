package com.kmu.filefinder.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmu.filefinder.file.dto.FileCategoryDTO;

@Service
public class FileExtractionServiceImpl implements FileExtractionService {

	@Autowired
	private FileServiceImpl fileServiceImpl;

	@Override
	public String extractSummary(FileCategoryDTO dto) throws IOException {
		if (dto.getFile_extension().equals(".pdf")) {
			return extractSummaryTextByPDF(dto.getFile_path());
		} else if (dto.getFile_extension().equals(".docx")) {
			return extractSummaryTextByDOCX(dto.getFile_path());
		}
		return null;
	}

	@Override
	public String extractSummaryTextByPDF(String filePath) throws IOException {

		File file = new File(filePath);

		PDDocument document = PDDocument.load(file);
		int pdfSize = document.getPages().getCount();

		PDFTextStripper pdfTextStripper = new PDFTextStripper();
		PDDocument pdDocument = new PDDocument();

		if (pdfSize > 3) {
			pdfSize = 4;
		}
		for (int i = 0; i < pdfSize; i++) {
			PDPage p = document.getPage(i);
			pdDocument.addPage(p);
		}

		String text = pdfTextStripper.getText(pdDocument);
		document.close();
		pdDocument.close();

		return text;
	}

	@Override
	public String extractSummaryTextByDOCX(String filePath) throws IOException {
		XWPFWordExtractor xw;

		try {
			FileInputStream fs = new FileInputStream(new File(filePath));

			OPCPackage d = OPCPackage.open(fs);
			xw = new XWPFWordExtractor(d);
			return xw.getText();
		} catch (Exception e) {
			System.out.println("# DocxFileParser Error :" + e.getMessage());
		}
		return "";
	}

	@Override
	public List<FileCategoryDTO> extractContent(FileCategoryDTO dto, String content) throws IOException {
		File file = new File(dto.getFile_path());
		String text = "";
		XWPFWordExtractor xw;

		if (dto.getFile_extension().equals(".pdf")) {
			PDDocument document = PDDocument.load(file);
			text = new PDFTextStripper().getText(document);
			document.close();
		} else if (dto.getFile_extension().equals(".docx")) {
			FileInputStream fs = new FileInputStream(file);
			OPCPackage d;
			try {
				d = OPCPackage.open(fs);
				xw = new XWPFWordExtractor(d);
				text = xw.getText();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
		}

		String[] strArr = text.split("\n");

		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = strArr[i].replaceAll("(\r\n|\r|\n|\n\r)", " ");
		}

		List<FileCategoryDTO> list = new ArrayList<FileCategoryDTO>();
		list = extractSearchSentence(strArr, dto, content);

		return list;
	}

	@Override
	public List<FileCategoryDTO> extractSearchSentence(String[] strArr, FileCategoryDTO dto, String content) {

		String temp = "";
		int size = 0, firstIndex = 0, lastIndex = 0;

		String charCheck = "";
		for (String s : strArr) {
			charCheck += s;
		}
		char[] str = charCheck.toCharArray();
		int en = 0;
		int ko = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] >= 'A' && str[i] <= 'z') {
				en++;
			} else if (str[i] >= '\uAC00' && str[i] <= '\uD7A3') {
				ko++;
				ko++;
			}
		}

		List<FileCategoryDTO> list = new ArrayList<FileCategoryDTO>();
		for (int i = 0; i < strArr.length; i++) {
			size = 0;

			if (strArr[i].contains(content) || strArr[i].toLowerCase().contains(content)
					|| strArr[i].toUpperCase().contains(content)) {
				System.out.println("확인 : " + strArr[i]);
				firstIndex = i;
				lastIndex = i;
				temp = "";
				if (en > ko) {
					if (strArr[i].indexOf(content) > 150) {
						System.out.println("test1");
						temp += strArr[i].substring(strArr[i].indexOf(content) - 150, strArr[i].indexOf(content) + 150);
					} else {
						System.out.println("test2");
						temp += strArr[i].substring(0, strArr[i].length());
					}
				} else {
					temp = strArr[i];
					while (size < 180) {
						if (firstIndex != 0) {
							temp = strArr[--firstIndex] + temp;
						}
						if (lastIndex != strArr.length - 1) {
							temp += strArr[++lastIndex];
						}
						size = temp.length();
					}
				}
				System.out.println("test : " + temp);

				FileCategoryDTO tempDTO = FileCategoryDTO.builder().category_nm(dto.getCategory_nm())
						.file_extension(dto.getFile_extension()).file_nm(dto.getFile_nm()).file_path(dto.getFile_path())
						.r_dt(dto.getR_dt()).i_cateogry(dto.getI_cateogry()).i_file(dto.getI_file()).build();

				tempDTO.setSummaryText(temp);
				list.add(tempDTO);
				fileServiceImpl.increaseCount();
			}
		}
		return list;
	}
}
