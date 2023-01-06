package com.kmu.filefinder.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.file.dto.FileCategoryDTO;
import com.kmu.filefinder.file.dto.FileDTO;

@Mapper
public interface FileMapper {
	/* -------------- read -------------- */
	public String getCategoryPathByIcategory(int i_category);
	public List<FileCategoryDTO> getFileListByName(@Param("content") String content, @Param("pagingVO") PagingVO pagingVo);
	public int getFileListCountByName(@Param("content") String content);
	public List<String> getFileNameList();
	public List<FileCategoryDTO> getFileCategoryInfoList(PagingVO pagingVo);
	
	public int getIcategoryByCategoryNm(String category_nm);
	public List<Integer> getIcategoryByCategoryTop(int category_top);
	public List<FileCategoryDTO> getFileLargeInfoList(@Param("i_category") int i_category, @Param("pagingVO") PagingVO pagingVo);
	public List<FileCategoryDTO> getFileSmallInfoList(@Param("i_category") int i_category, @Param("pagingVO") PagingVO pagingVo);
	public FileCategoryDTO getFileSearchPdfInfoByFileName(String file_nm);
	public FileCategoryDTO getFileSearchDocxInfoByFileName(String file_nm);
	public String getFilePathByFileName(String file_nm);
	public String getExtensionByFileName(String file_nm);
	public List<String> getFilePath();
	public List<FileCategoryDTO> getFileSearchInfoList();
	/* -------------- create -------------- */
	public int createFile(FileDTO dto);
	
	/* -------------- update -------------- */
	
	/* -------------- delete -------------- */
}
