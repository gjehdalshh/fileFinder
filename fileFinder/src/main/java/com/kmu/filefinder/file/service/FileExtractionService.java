package com.kmu.filefinder.file.service;

import java.io.IOException;

public interface FileExtractionService {
	String extractSummaryTextByPDF(String filePath) throws IOException;
}
