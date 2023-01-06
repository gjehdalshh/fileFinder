package com.kmu.filefinder.file.service;

import java.io.IOException;
import java.util.List;

import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.file.dto.FileCategoryDTO;

public interface FileExtractionService {
	String extractSummary(FileCategoryDTO dto) throws IOException;
	String extractSummaryTextByPDF(String filePath) throws IOException;
	String extractSummaryTextByDOCX(String filePath) throws IOException;
	List<FileCategoryDTO> extractSearchSentence(String[] strArr, FileCategoryDTO dto, String content);
	List<FileCategoryDTO> extractContent(FileCategoryDTO dto, String content, PagingVO pagingVo) throws IOException;
}
