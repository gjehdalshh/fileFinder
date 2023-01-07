package com.kmu.filefinder.file.service;

import java.io.IOException;
import java.util.List;

import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.file.dto.FileCategoryDTO;

public interface FileExtractionService {
	String extractText(FileCategoryDTO dto) throws IOException;
	String extractTextByPDF(String filePath) throws IOException;
	String extractTextByDOCX(String filePath) throws IOException;
	List<FileCategoryDTO> extractSearchSentence(String[] strArr, FileCategoryDTO dto, String content);
	List<FileCategoryDTO> extractSummaryText(FileCategoryDTO dto, String content) throws IOException;
}
