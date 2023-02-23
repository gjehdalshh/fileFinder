package com.kmu.filefinder.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmu.filefinder.file.dto.FileCategoryDTO;

@Service
public class FileExtractionServiceImpl implements FileExtractionService {

	@Autowired
	private FileServiceImpl fileServiceImpl;

	@Override
	public String extractText(FileCategoryDTO dto) throws IOException {
		if (dto.getFile_extension().equals(".pdf")) {
			return extractTextByPDF(dto.getFile_path());
		} else if (dto.getFile_extension().equals(".docx")) {
			return extractTextByDOCX(dto.getFile_path());
		} else if (dto.getFile_extension().equals(".doc")) {
			return extractTextByDOC(dto.getFile_path());
		}
		return null;
	}

	@Override
	public String extractTextByPDF(String filePath) throws IOException {

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
	public String extractTextByDOCX(String filePath) throws IOException {
		XWPFWordExtractor xw;

		try {
			FileInputStream fs = new FileInputStream(new File(filePath));
			OPCPackage d = OPCPackage.open(fs); // doc 여기서 에러
			xw = new XWPFWordExtractor(d);
			fs.close();
			d.close();
			return xw.getText();
		} catch (Exception e) {
			System.out.println("# DocxFileParser Error :" + e.getMessage());
		}
		return "";
	}
	
	@Override
	public String extractTextByDOC(String filePath) throws IOException {
		try {
			POIFSFileSystem poi = new POIFSFileSystem(new FileInputStream(new File(filePath)));
			HWPFDocument hwp = new HWPFDocument(poi);
		    WordExtractor we = new WordExtractor(hwp);
		 
		    String[] paragraphs = we.getParagraphText();
		    String result = "";
		    for (int i = 0; i < paragraphs.length; i++) {
		        result += paragraphs[i];
		    }
			we.close();
			hwp.close();
			poi.close();
			return result;
		} catch (Exception e) {
			System.out.println("# DocxFileParser Error :" + e.getMessage());
		}
		return "";
	}

	@Override
	public List<FileCategoryDTO> extractSummaryText(FileCategoryDTO dto, String content) throws IOException {
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
				d.close();
				xw.close();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			fs.close();
		} else if (dto.getFile_extension().equals(".doc")) {
			try {
				POIFSFileSystem poi = new POIFSFileSystem(new FileInputStream(file));
				HWPFDocument hwp = new HWPFDocument(poi);
			    WordExtractor we = new WordExtractor(hwp);
			 
			    String[] paragraphs = we.getParagraphText();
			    for (int i = 0; i < paragraphs.length; i++) {
			        text += paragraphs[i];
			    }
				we.close();
				hwp.close();
				poi.close();
			} catch (Exception e) {
				System.out.println("# DocxFileParser Error :" + e.getMessage());
			}
		}

		String[] strArr = text.split("\n"); // 한 줄씩 배열에 담음

		for (int i = 0; i < strArr.length; i++) {
			strArr[i] = strArr[i].replaceAll("(\r\n|\r|\n|\n\r)", " ");
		}

		List<FileCategoryDTO> list = new ArrayList<FileCategoryDTO>();
		list = extractSearchSentence(strArr, dto, content);

		return list;
	}

	@Override
	public List<FileCategoryDTO> extractSearchSentence(String[] strArr, FileCategoryDTO dto, String content) {

		String text = "";
		int size = 0, firstIndex = 0, lastIndex = 0;

		List<FileCategoryDTO> list = new ArrayList<FileCategoryDTO>();
		for (int i = 0; i < strArr.length; i++) {
			size = 0;
			
			if (strArr[i].toLowerCase().contains(content.toLowerCase())) {
				firstIndex = i;
				lastIndex = i;
				text = "";
				if (checkIfEnglishDocument(strArr)) { // 영문 문서
					String lowerCaseTextString = strArr[i].toLowerCase();
					String lowerCaseWord = content.toLowerCase();
					
					int index = lowerCaseTextString
							.indexOf(lowerCaseWord);
					while (index != -1) {
						if (index < 150) { // 첫 문장
							if (strArr[i].length() < 300) { // 문장이 300자 이하
								text = strArr[i].substring(0, strArr[i].length());
							} else { // 문장이 300자 이상
								text = strArr[i].substring(0, 300);
							}
						} else { // 중간 or 마지막 문장
							if (strArr[i].length() - index < 150) { // 마지막 문장
								text = strArr[i].substring(index - 130, strArr[i].length());
								createFileCategoryDTO(list, dto, text);
								break;
							} else { // 중간 문장
								text = strArr[i].substring(index - 150, index + 150);
							}
						}
						createFileCategoryDTO(list, dto, text);
						index = lowerCaseTextString.indexOf(lowerCaseWord, index + content.length());
					}
				} else { // 한글 문서
					text = strArr[i];
					while (size < 180) {
						if (firstIndex != 0) {
							text = strArr[--firstIndex] + text;
						}
						if (lastIndex != strArr.length - 1) {
							text += strArr[++lastIndex];
						}
						size = text.length();
					}
					createFileCategoryDTO(list, dto, text);
				}
			}
		}
		return list;
	}

	public void createFileCategoryDTO(List<FileCategoryDTO> list, FileCategoryDTO dto, String text) {
		fileServiceImpl.increaseCount();
		FileCategoryDTO tempDTO = FileCategoryDTO.builder().category_nm(dto.getCategory_nm())
				.file_extension(dto.getFile_extension()).file_nm(dto.getFile_nm()).file_path(dto.getFile_path())
				.r_dt(dto.getR_dt()).i_category(dto.getI_category()).i_file(dto.getI_file()).build();

		tempDTO.setSummaryText(text);
		list.add(tempDTO);
	}

	public boolean checkIfEnglishDocument(String[] strArr) { // 한글 문서, 영문 문서 판별

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

		if (en > ko) {
			return true;
		}
		return false;
	}
}
