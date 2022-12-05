package com.kmu.filefinder.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.kmu.filefinder.file.dto.FileCategoryDTO;
import com.kmu.filefinder.file.dto.FileDTO;

@Mapper
public interface FileMapper {
	/* -------------- read -------------- */
	public String getCategoryPathByIcategory(int i_category);
	public List<String> getFileNameList();
	public List<FileCategoryDTO> getFileCategoryInfoList();
	
	public int getIcategoryByCategoryNm(String category_nm);
	public List<Integer> getIcategoryByCategoryTop(int category_top);
	public List<FileCategoryDTO> getFileInfoList(int i_category);
	public FileCategoryDTO getFileSearchInfoListByFileName(String file_nm);
	public String getFilePathByFileName(String file_nm);
	public String getExtensionByFileName(String file_nm);
	public List<String> getFilePath();
	public List<FileCategoryDTO> getFileSearchInfoList();
	/* -------------- create -------------- */
	public int createFile(FileDTO dto);
	
	/* -------------- update -------------- */
	
	/* -------------- delete -------------- */
}
