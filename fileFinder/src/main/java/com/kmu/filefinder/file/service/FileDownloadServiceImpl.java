package com.kmu.filefinder.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.common.paging.Pagination;
import com.kmu.filefinder.file.dto.FileCategoryDTO;
import com.kmu.filefinder.file.mapper.FileMapper;
import com.kmu.filefinder.main.mapper.MainMapper;

@Service
public class FileDownloadServiceImpl implements FileDownloadService{

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private MainMapper mainMapper;

	@Autowired
	private FileExtractionServiceImpl fileExtractionServiceImpl;

	@Autowired
	private FileServiceImpl fileServiceImpl;

	private String downloadPath;

	// 파일 다운로드 기능 - 하나씩 다운
	@Override
	public int fileDownload(FileCategoryDTO fileDTO) throws IOException {
		makeFolder();

		if (fileDTO.getFile_extension().equals(".pdf")) {
			return fileDownloadPdf(fileDTO);
		} else if (fileDTO.getFile_extension().equals(".docx")) {
			return fileDownloadDocx(fileDTO);
		}
		return 2;
	}

	// pdf 다운로드
	@Override
	public int fileDownloadPdf(FileCategoryDTO fileDTO) throws IOException {

		File downloadFile = new File(this.downloadPath + "\\" + fileDTO.getFile_nm() + fileDTO.getFile_extension());
		String path = fileMapper.getFilePathByFileName(fileDTO.getFile_nm());

		FileInputStream input = new FileInputStream(path); // 읽을 파일
		FileOutputStream output = new FileOutputStream(downloadFile); // 생성할 파일

		int c;

		byte[] buf = new byte[150072]; // 버퍼 생성

		/* 미리 만들어 둔 버퍼의 크기만큼 read하고 write */
		while ((c = input.read(buf)) > 0) { // 버퍼의 크기만큼 읽음
			output.write(buf, 0, c); // offset 0부터 버퍼의 크기인 c만큼 write
		}

		input.close();
		output.close();

		return 1;
	}

	// docx 다운로드
	@Override
	public int fileDownloadDocx(FileCategoryDTO fileDTO) {
		// docx 로 다운로드, 텍스트 파일을 docx로 변환하기 때문에 시간이 오래 걸림
		String text = fileDTO.getFullText();
		File downloadFile = new File(this.downloadPath + "\\" + fileDTO.getFile_nm() + ".docx");
		WordprocessingMLPackage wordPackage;
		try {
			wordPackage = WordprocessingMLPackage.createPackage();
			MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
			mainDocumentPart.addParagraphOfText(text);
			wordPackage.save(downloadFile);
		} catch (Docx4JException e) {
			e.printStackTrace();
		}
		return 1;
	}

	// 폴더 생성
	public void makeFolder() {
		this.downloadPath = "C:\\Users\\User\\Downloads\\DocumentDownloads";
		File Folder = new File(downloadPath);

		if (!Folder.exists()) {
			try {
				Folder.mkdir(); // 폴더 생성합니다.
				System.out.println("폴더가 생성되었습니다.");
			} catch (Exception e) {
				e.getStackTrace();
			}
		} else {
			System.out.println("이미 폴더가 생성되어 있습니다.");
		}
	}

	// 분류별 전체 다운로드
	@Override
	public int totalFileDownload(FileCategoryDTO fileDTO) throws IOException {
		this.downloadPath = "C:\\Users\\User\\Downloads\\DocumentDownloads";
		String category = fileDTO.getCurrentPath();
		if (category.equals("mainCategory") || category.equals("entireCategory")) {
			return downloadTotal(fileDTO);
		} else if (category.equals("largeCategory")) {
			return downloadLarge(fileDTO);
		} else if (category.equals("smallCategory")) {
			return downloadSmall(fileDTO);
		} else if (category.equals("searchTitle")) {
			return downloadSearchTitle(fileDTO);
		} else if (category.equals("searchCategory")) {
			return downloadSearchContent(fileDTO);
		}
		return 2;
	}

	// 전체 다운로드
	@Override
	public int downloadTotal(FileCategoryDTO fileDTO) throws IOException {
		int count = mainMapper.getTotalNumberPosts();

		PagingVO pagingVo = createPagingVo(count);
		List<FileCategoryDTO> list = fileMapper.getFileCategoryInfoList(pagingVo);
		downloadProcess(list);

		return 1;
	}

	@Override
	public int downloadLarge(FileCategoryDTO fileDTO) throws IOException {
		String category_nm = fileServiceImpl.getCurrentCategoryName();
		int i_category = fileMapper.getIcategoryByCategoryNm(category_nm);
		int count = mainMapper.getLargeNumberPosts(i_category);

		PagingVO pagingVo = createPagingVo(count);
		List<FileCategoryDTO> list = fileMapper.getFileLargeInfoList(i_category, pagingVo);
		downloadProcess(list);

		return 1;
	}

	@Override
	public int downloadSmall(FileCategoryDTO fileDTO) throws IOException {
		String category_nm = fileServiceImpl.getCurrentCategoryName();
		int i_category = fileMapper.getIcategoryByCategoryNm(category_nm);
		int count = mainMapper.getSmallNumberPosts(i_category);

		PagingVO pagingVo = createPagingVo(count);
		List<FileCategoryDTO> list = fileMapper.getFileSmallInfoList(i_category, pagingVo);
		downloadProcess(list);

		return 1;
	}

	@Override
	public int downloadSearchTitle(FileCategoryDTO fileDTO) throws IOException {
		String searchContent = fileServiceImpl.getSearchContent();
		int count = fileMapper.getFileListCountByName(searchContent);

		PagingVO pagingVo = createPagingVo(count);
		List<FileCategoryDTO> list = fileMapper.getFileListByName(searchContent, pagingVo);
		downloadProcess(list);

		return 1;
	}

	@Override
	public int downloadSearchContent(FileCategoryDTO fileDTO) throws IOException {
		String searchContent = fileServiceImpl.getSearchContent();
		List<FileCategoryDTO> temp = fileMapper.getFileSearchDocxInfo(); // 모든 file을 가져옴
		List<FileCategoryDTO> list = new ArrayList<FileCategoryDTO>();

		for (FileCategoryDTO dto : temp) {
			String text = fileExtractionServiceImpl.extractTextByDOCX(dto.getFile_path());
			if (text.toLowerCase().contains(searchContent.toLowerCase())) {
				list.add(dto);
			}
		}
		downloadProcess(list);

		return 1;
	}

	@Override
	public PagingVO createPagingVo(int count) {
		PagingVO pagingVo = new PagingVO();
		pagingVo.setPagination(new Pagination(0));
		pagingVo.setRecordSize(count);

		return pagingVo;
	}

	@Override
	public void downloadProcess(List<FileCategoryDTO> list) throws IOException {
		for (FileCategoryDTO dto : list) {
			if (dto.getFile_extension().equals(".pdf")) {
				fileDownloadPdf(dto);
			} else if (dto.getFile_extension().equals(".docx")) {
				dto.setFullText(fileExtractionServiceImpl.extractTextByDOCX(dto.getFile_path()));
				fileDownloadDocx(dto);
			}
		}
	}
}
