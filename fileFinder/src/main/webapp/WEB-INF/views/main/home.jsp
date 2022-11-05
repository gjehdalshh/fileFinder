<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/res/css/main/home.css?ver=5">

<div>
	<div id="title">file finder</div>
	<div id="wholeViewContainer">
		<div id="left">
			<div onclick="insert_category_open()">카테고리 등록</div>
			<div id="whole_category">분류 전체보기(${totalNumberPosts})</div>
			<!-- top 카테고리 출력 -->
			<c:forEach var="top_category" items="${category}">
				<c:set var="cate" value="${top_category.i_category}"></c:set>
				<c:if test="${top_category.category_order == 1}">
					<div class="large_category_list">${top_category.category_nm}(${top_category.category_count})</div>
				</c:if>
				<!-- sub 카테고리 출력 -->
				<c:forEach var="sub_category" items="${category}">
					<c:if test="${cate == sub_category.category_top}">
						<c:if test="${sub_category.category_order == 2}">
							<div class="small_category_list">${sub_category.category_nm}(${sub_category.category_count})
							</div>
						</c:if>
					</c:if>
				</c:forEach>
			</c:forEach>
			<div onclick="delete_category_open()">카테고리 삭제</div>
		</div>
		<div id="middle">
			<div>
				<div id="middle_title">분류 전체보기 <span id="whole_category_number">${totalNumberPosts}</span></div>
			</div>
		</div>
		<div id="right">
			<div onclick="upload_modal_open()">파일 업로드</div>
			<select>
				<option>=====선택======</option>
				<option selected="selected">전체</option>
				<option>분류</option>
			</select> <input type="text"> <span>검색</span>
		</div>
	</div>
</div>

<!-- 파일 업로드 모달창 -->
<div id="file_modal_div">
	<div class="file_modal_content">
		<div>파일 업로드</div>
		<form id="fileForm" method="post" enctype="multipart/form-data">
			<input type="file" id="fileUpload" name="fileUpload"
				multiple="multiple"> <select id="top_category_choice_upload"
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
		</form>
		<div class=flex>
			<div onclick="upload()">업로드</div>
			<div onclick="upload_modal_close()">취소</div>
		</div>
	</div>
	<div class="file_modal_layer"></div>
</div>

<!-- 카테고리 등록 모달창 -->
<div id="modal_insert">
	<div class="modal_content">
		<div>카테고리 추가</div>
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
			<div onclick="insert_cateogry()">추가</div>
			<div onclick="insert_category_close()">닫기</div>
		</div>
	</div>
	<div class="modal_layer"></div>
</div>

<!-- 카테고리 삭제 모달창 -->
<div id="modal_delete">
	<div class="modal_delete_content">
		<div>카테고리 삭제</div>
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
			<div onclick="delete_category()">삭제</div>
			<div onclick="delete_category_close()">닫기</div>
		</div>
	</div>
	<div class="modal_delete_layer"></div>
</div>

<script defer src="/res/js/main/home.js?ver=68"></script>
<script defer src="/res/js/pdf/pdfUpload.js?ver=58"></script>