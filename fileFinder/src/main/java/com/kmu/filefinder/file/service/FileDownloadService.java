package com.kmu.filefinder.file.service;

import java.io.IOException;
import java.util.List;

import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.file.dto.FileCategoryDTO;

public interface FileDownloadService {
	int fileDownload(FileCategoryDTO fileDTO) throws IOException;
	int fileDownloadPdf(FileCategoryDTO fileDTO) throws IOException;
	int fileDownloadDocx(FileCategoryDTO fileDTO, String extension);
	
	void makeFolder();
	int totalFileDownload(FileCategoryDTO fileDTO) throws IOException;
	int downloadTotal(FileCategoryDTO fileDTO) throws IOException;
	int downloadLarge(FileCategoryDTO fileDTO) throws IOException;
	int downloadSmall(FileCategoryDTO fileDTO) throws IOException;
	
	int downloadSearchTitle(FileCategoryDTO fileDTO) throws IOException;
	int downloadSearchContent(FileCategoryDTO fileDTO) throws IOException;
	PagingVO createPagingVo(int count);
	void downloadProcess(List<FileCategoryDTO> list) throws IOException;
}


