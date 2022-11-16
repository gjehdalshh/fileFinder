<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/res/css/main/home.css?ver=20">

<div id="title_container">
	<div id="title" onclick="goHome()">파일 검색기</div>
	<div class="flex">
		<div id="file_upload" onclick="upload_modal_open()">파일 업로드</div>
		<div id="insert_category" onclick="insert_category_open()">카테고리
			등록</div>
		<div id="delete_category" onclick="delete_category_open()">카테고리
			삭제</div>
	</div>
</div>
<div id="entire_container">
	<div id="wholeViewContainer">
		<div id="content_container">
			<c:choose>
				<c:when test="${currentPath eq 'entireCategory'}">
					<div id="middle_title">
						분류 전체보기 <span id="totalNumberPosts">${totalNumberPosts}</span>
					</div>
				</c:when>
				<c:when test="${currentPath eq 'largeCategory' }">
					<div id="middle_title">${largeCategory}</div>
				</c:when>
				<c:when test="${currentPath eq 'smallCategory' }">
					<div id="middle_title">${smallCategory}</div>
				</c:when>
				<c:otherwise>
					<div id="middle_title">
						<span id="search_result">'${currentPath}'</span>의 검색 결과 ${searchCount }
					</div>
				</c:otherwise>
			</c:choose>
			<c:forEach var="fileCategoryInfoList" items="${fileCategoryInfoList}">
				<c:choose>
					<c:when test="${currentPath eq 'entireCategory'}">
						<div id="middle_content_container">
							<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
							<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
							<div class="flex">
								<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
								<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
							</div>
						</div>
					</c:when>
					<c:when test="${currentPath eq 'largeCategory' }">
						<c:forEach var="fileCategoryInfo" items="${fileCategoryInfoList }">
							<div id="middle_content_container">
								<div class="middle_file_nm">${fileCategoryInfo.file_nm }</div>
								<div class="middle_summary_text">${fileCategoryInfo.summaryText }</div>
								<div class="flex">
									<div class="middle_category_nm">${fileCategoryInfo.category_nm }</div>
									<div class="middle_r_dt">${fileCategoryInfo.r_dt }</div>
								</div>
							</div>
						</c:forEach>
					</c:when>
					<c:when test="${currentPath eq 'smallCategory'}">
						<div id="middle_content_container">
							<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
							<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
							<div class="flex">
								<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
								<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
							</div>
						</div>
					</c:when>
					<c:when test="${currentPath eq 'smallCategory'}">
						<div id="middle_content_container">
							<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
							<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
							<div class="flex">
								<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
								<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div id="middle_content_container">
							<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
							<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
							<div class="flex">
								<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
								<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		<div id="sub_content_container">
			<div id="search_container">
				<form action="/search" method="get">
					<select name="category" class="search_select">
						<option value="all" selected="selected">전체</option>
						<option value="title">제목</option>
						<option value="category">분류</option>
					</select> <input id="search_input" type="text" name="content"> <input
						type="submit" id="search_btn" value="검색">
				</form>
			</div>
			<div id="left">
				<form action="/category" method="get">
					<input id="entire_categoey_submit" type="submit"
						value="분류 전체보기 (${totalNumberPosts})">
				</form>
				<!-- top 카테고리 출력 -->
				<c:forEach var="top_category" items="${category}">
					<c:set var="cate" value="${top_category.i_category}"></c:set>
					<c:if test="${top_category.category_order == 1}">
						<form action="/category/${top_category.category_nm}" method="get">
							<input class="large_category_list" type="submit"
								value="${top_category.category_nm} (${top_category.category_count})">
						</form>
					</c:if>
					<!-- sub 카테고리 출력 -->
					<c:forEach var="sub_category" items="${category}">
						<c:if test="${cate == sub_category.category_top}">
							<c:if test="${sub_category.category_order == 2}">
								<form
									action="/category/${top_category.category_nm}/${sub_category.category_nm}"
									method="get">
									<input class="small_category_list" type="submit"
										value="- ${sub_category.category_nm} (${sub_category.category_count})">
								</form>
							</c:if>
						</c:if>
					</c:forEach>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<!-- 파일 업로드 모달창 -->
<div id="file_modal_div">
	<div class="file_modal_content">
		<div id="file_upload_title">파일 업로드</div>
		<form id="fileForm" method="post" enctype="multipart/form-data">
			<div class="filebox">
				<label for="fileUpload">업로드</label> <input type="file"
					id="fileUpload" name="fileUpload" multiple="multiple"
					onchange=changeFileName(this.files)> <span
					class="file_name"></span>
			</div>
			<div id="file_upload_category_container" class="flex">
				<select id="top_category_choice_upload"
					onchange="uploadCategoryChange(this)">
					<option selected="selected" value="0">대분류 선택</option>
					<c:forEach var="top_category_upload" items="${category}">
						<c:if test="${top_category_upload.category_order == 1}">
							<option value="${top_category_upload.i_category}">${top_category_upload.category_nm}</option>
						</c:if>
					</c:forEach>
					<c:forEach var="sub_category_upload" items="${category}">
						<c:if test="${sub_category_upload.category_order == 2}">
							<input class="sub_category_top_upload" type="hidden"
								value="${sub_category_upload.category_top }">
							<input class="sub_category_nm_upload" type="hidden"
								value="${sub_category_upload.category_nm }">
							<input class="sub_category_i_category_upload" type="hidden"
								value="${sub_category_upload.i_category }">
						</c:if>
					</c:forEach>
				</select> <select id="sub_category_choice_upload"
					onchange="subUploadCategoryChange(this)">
					<option value="0">소분류 선택</option>
				</select>
			</div>
		</form>
		<div class=flex>
			<div id="upload" onclick="upload()">업로드</div>
			<div id="upload_close" onclick="upload_modal_close()">취소</div>
		</div>
	</div>
	<div class="file_modal_layer"></div>
</div>

<!-- 카테고리 등록 모달창 -->
<div id="modal_insert">
	<div class="modal_content">
		<div id="category_insert_title">카테고리 등록</div>
		<div>
			<div class="flex">
				<div id="large_category">대분류</div>
				<div id="small_category">소분류</div>
			</div>
			<div id="large_category_div">
				<input type="text" class="large_category_input">
			</div>
			<div id="small_category_div">
				<select id="category_choice">
					<c:forEach var="category" items="${category}">
						<c:if test="${category.category_order == 1}">
							<option value="${category.i_category}">${category.category_nm}</option>
						</c:if>
					</c:forEach>
				</select> <input type="text" class="small_category_input">
			</div>
		</div>
		<div class="flex">
			<div id="insert_category_btn" onclick="insert_cateogry()">추가</div>
			<div id="insert_category_close" onclick="insert_category_close()">닫기</div>
		</div>
	</div>
	<div class="modal_layer"></div>
</div>

<!-- 카테고리 삭제 모달창 -->
<div id="modal_delete">
	<div class="modal_delete_content">
		<div id="category_delete_title">카테고리 삭제</div>
		<div>
			<div class="flex">
				<div id="large_delete_category">대분류</div>
				<div id="small_delete_category">소분류</div>
			</div>
			<div id="large_delete_category_div">
				<select id="large_category_choice">
					<c:forEach var="category" items="${category}">
						<c:if test="${category.category_order == 1}">
							<option value="${category.i_category}">${category.category_nm}</option>
						</c:if>
					</c:forEach>
				</select>
			</div>

			<div id="small_delete_category_div">
				<select onchange="top_categoryChange(this)">
					<option value="0">대분류 선택</option>
					<c:forEach var="top_category" items="${category}">
						<c:if test="${top_category.category_order == 1}">
							<option value="${top_category.i_category}">${top_category.category_nm}</option>
						</c:if>
					</c:forEach>
					<c:forEach var="sub_category" items="${category}">
						<c:if test="${sub_category.category_order == 2}">
							<input class="sub_category_top" type="hidden"
								value="${sub_category.category_top }">
							<input class="sub_category_nm" type="hidden"
								value="${sub_category.category_nm }">
							<input class="sub_category_i_category" type="hidden"
								value="${sub_category.i_category }">
						</c:if>
					</c:forEach>
				</select> <select id="small_category_choice">
					<option value="0">소분류 선택</option>
				</select>
			</div>
		</div>
		<div class="flex">
			<div id="delete_category_btn" onclick="delete_category()">삭제</div>
			<div id="delete_category_close" onclick="delete_category_close()">닫기</div>
		</div>
	</div>
	<div class="modal_delete_layer"></div>
</div>

<script defer src="/res/js/main/home.js?ver=73"></script>
<script defer src="/res/js/pdf/pdfUpload.js?ver=64"></script>