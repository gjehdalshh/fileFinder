<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/res/css/main/home.css?ver=14">

<input id="contentValue" type="hidden" value="${param.content}">
<input id="user_session" type="hidden" value="${user}">
<input id="currentPath" type="hidden" value="${currentPath}">
<input id="searchContent" type="hidden" value="${searchContent}">
<input id="largeCategory" type="hidden" value="${largeCategory}">
<input id="smallCategory" type="hidden" value="${smallCategory}">
<input id="currentPage" type="hidden" value="${param.page}">
<div id='wrapper'>
	<div id="title_container">
		<div class="flex_title">
			<div id="title" onclick="goHome()">Gale Papers</div>
			<c:if test="${not empty user}">
				<div class="flex">
					<div id="title_user_nm">${user.user_nm}</div>
					<div id="user_logout">
						<a href="/user/logout">logout</a>
					</div>
				</div>
			</c:if>
		</div>
		<c:if test="${empty user}">
			<div class="flex">
				<div id="user_login" onclick="move_login()">Login</div>
				<div id="user_join" onclick="move_join()">Sign Up</div>
			</div>
		</c:if>
		<c:if test="${user.user_authority eq 0}">
			<div class="flex">
				<div id="user_management" onclick="move_user_management()">Member
					Management</div>
				<div id="file_upload" onclick="upload_modal_open()">File
					Upload</div>
				<div id="insert_category" onclick="insert_category_open()">Add
					Category</div>
				<div id="delete_category" onclick="delete_category_open()">Delete
					Category</div>
			</div>
		</c:if>
	</div>
	<div id="entire_container">
		<div id="wholeViewContainer">
			<div id="content_container">
				<c:choose>
					<c:when test="${currentPath eq 'mainCategory' }">
						<div id="middle_title">
							Total Categories <span id="totalNumberPosts">${totalNumberPosts}</span>
							<c:if test="${not empty user}">
								<span class="totalDownload_btn"
									onclick="downloadTotalFile(${totalNumberPosts})">Full
									Download</span>
							</c:if>
							<input id="currentPathForDownload" type="hidden"
								value="${currentPath }">
						</div>
					</c:when>
					<c:when test="${currentPath eq 'entireCategory'}">
						<div id="middle_title">
							Total Categories <span id="totalNumberPosts">${totalNumberPosts}</span>
							<c:if test="${not empty user}">
								<span class="totalDownload_btn" onclick="downloadTotalFile(${totalNumberPosts})">Full
									Download</span>
							</c:if>
							<input id="currentPathForDownload" type="hidden"
								value="${currentPath }">
						</div>
					</c:when>
					<c:when test="${currentPath eq 'largeCategory' }">
						<div id="middle_title">${largeCategory}
							<span id="largeCategoryCount">${largeCategoryCount }</span>
							<c:if test="${not empty user}">
								<span class="totalDownload_btn" onclick="downloadTotalFile(${largeCategoryCount })">Full
									Download</span>
							</c:if>
							<input id="currentPathForDownload" type="hidden"
								value="${currentPath }">
						</div>
					</c:when>
					<c:when test="${currentPath eq 'smallCategory' }">
						<div id="middle_title">${smallCategory}
							<span id="smallCategoryCount">${smallCategoryCount }</span>
							<c:if test="${not empty user}">
								<span class="totalDownload_btn" onclick="downloadTotalFile(${smallCategoryCount })">Full
									Download</span>
							</c:if>
							<input id="currentPathForDownload" type="hidden"
								value="${currentPath }">
						</div>
					</c:when>
					<c:otherwise>
						<div id="middle_title">
							<span id="search_result">'${searchContent}'</span>search result
							<span id="search_count">${searchCount }</span>
							<c:if test="${not empty user}">
								<span class="totalDownload_btn" onclick="downloadTotalFile(${searchCount })">Full
									Download</span>
							</c:if>
							<input id="currentPathForDownload" type="hidden"
								value="${currentPath }">
						</div>
						<c:if test="${searchCount == 0}">
							<div id="nullcontentContainer">
								<ul>
									<li>Check that the words you entered are spelled correctly.</li>
									<li>Try searching with more general words.</li>
									<li>If you are searching for keywords of more than two words, try searching after correctly using spaces.</li>
								</ul>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>
				<c:forEach var="fileCategoryInfoList"
					items="${fileCategoryInfoList}">
					<c:choose>
						<%-- 분류 전체보기 --%>
						<c:when
							test="${currentPath eq 'entireCategory' && fileCategoryInfoList.file_extension eq '.pdf'}">
							<div class="file_open_modal">
								<div class="middle_file_download" onclick="file_download(this)">
									<input id="middle_file_nm_download" type="hidden"
										value="${fileCategoryInfoList.file_nm }"> <input
										id="middle_extension" type="hidden"
										value="${fileCategoryInfoList.file_extension}"> <input
										id="middle_summary_text_download" type="hidden"
										value="${fileCategoryInfoList.summaryText}">
									<c:if test="${not empty user}">
										<button class="download_btn">download</button>
									</c:if>
								</div>
								<div class="middle_file_delete" onclick="file_delete(this)">
									<c:if test="${user.user_authority eq 0 }">
										<button class="category_delete_btn">Delete</button>
									</c:if>
									<input id="middle_file_i_file" type="hidden"
										value=${fileCategoryInfoList.i_file }>
								</div>
								<form class="file_open_form" action="/fileOpen" method="get"
									onclick="fileOpen(this)">
									<div id="middle_content_container">
										<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
										<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
										<div class="flex">
											<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
											<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
											<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
										</div>
										<input id="middle_file_nm_input" name="fileName" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_file_extension_input" name="extension"
											type="hidden" value="${fileCategoryInfoList.file_extension }">
									</div>
								</form>
							</div>
						</c:when>
						<c:when
							test="${currentPath eq 'entireCategory' && fileCategoryInfoList.file_extension eq '.docx'}">
							<div class="file_open_modal">
								<div class="middle_file_download" onclick="file_download(this)">
									<input id="middle_file_nm_download" type="hidden"
										value="${fileCategoryInfoList.file_nm }"> <input
										id="middle_extension" type="hidden"
										value="${fileCategoryInfoList.file_extension}"> <input
										id="middle_summary_text_download" type="hidden"
										value="${fileCategoryInfoList.summaryText }">
									<c:if test="${not empty user}">
										<button class="download_btn">download</button>
									</c:if>

								</div>
								<div class="middle_file_delete" onclick="file_delete(this)">
									<c:if test="${user.user_authority eq 0 }">
										<button class="category_delete_btn">Delete</button>
									</c:if>
									<input id="middle_file_i_file" type="hidden"
										value=${fileCategoryInfoList.i_file }>
								</div>
								<div id="middle_content_container"
									onclick="file_open_modal(this)">
									<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
									<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
									<div class="flex">
										<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
										<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
										<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
									</div>
									<input id="middle_file_nm_input" name="fileName" type="hidden"
										value="${fileCategoryInfoList.file_nm }"> <input
										id="middle_summary_text_input"
										name="middle_summary_text_input" type="hidden"
										value="${fileCategoryInfoList.summaryText }"> <input
										id="middle_full_text" type="hidden" name="middle_full_text"
										value="${fileCategoryInfo.fullText }">
								</div>
							</div>
						</c:when>
						<c:when
							test="${currentPath eq 'entireCategory' && fileCategoryInfoList.file_extension eq '.doc'}">
							<div class="file_open_modal">
								<div class="middle_file_download" onclick="file_download(this)">
									<input id="middle_file_nm_download" type="hidden"
										value="${fileCategoryInfoList.file_nm }"> <input
										id="middle_extension" type="hidden"
										value="${fileCategoryInfoList.file_extension}"> <input
										id="middle_summary_text_download" type="hidden"
										value="${fileCategoryInfoList.summaryText }">
									<c:if test="${not empty user}">
										<button class="download_btn">download</button>
									</c:if>

								</div>
								<div class="middle_file_delete" onclick="file_delete(this)">
									<c:if test="${user.user_authority eq 0 }">
										<button class="category_delete_btn">Delete</button>
									</c:if>
									<input id="middle_file_i_file" type="hidden"
										value=${fileCategoryInfoList.i_file }>
								</div>
								<div id="middle_content_container"
									onclick="file_open_modal(this)">
									<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
									<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
									<div class="flex">
										<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
										<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
										<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
									</div>
									<input id="middle_file_nm_input" name="fileName" type="hidden"
										value="${fileCategoryInfoList.file_nm }"> <input
										id="middle_summary_text_input"
										name="middle_summary_text_input" type="hidden"
										value="${fileCategoryInfoList.summaryText }"> <input
										id="middle_full_text" type="hidden" name="middle_full_text"
										value="${fileCategoryInfo.fullText }">
								</div>
							</div>
						</c:when>
						<%-- 대분류 --%>
						<c:when test="${currentPath eq 'largeCategory'}">
							<c:forEach var="fileCategoryInfo"
								items="${fileCategoryInfoList }">
								<c:if test="${fileCategoryInfo.file_extension eq '.pdf' }">
									<div class="file_open_modal">
										<div class="middle_file_download"
											onclick="file_download(this)">
											<input id="middle_file_nm_download" type="hidden"
												value="${fileCategoryInfo.file_nm }"> <input
												id="middle_extension" type="hidden"
												value="${fileCategoryInfo.file_extension}"> <input
												id="middle_summary_text_download" type="hidden"
												value="${fileCategoryInfo.summaryText }">
											<c:if test="${not empty user}">
												<button class="download_btn">download</button>
											</c:if>

										</div>
										<div class="middle_file_delete" onclick="file_delete(this)">
											<c:if test="${user.user_authority eq 0 }">
												<button class="category_delete_btn">Delete</button>
											</c:if>
											<input id="middle_file_i_file" type="hidden"
												value=${fileCategoryInfo.i_file }>
										</div>
										<form class="file_open_form" action="/fileOpen" method="get"
											onclick="fileOpen(this)">
											<div id="middle_content_container">
												<div class="middle_file_nm">${fileCategoryInfo.file_nm }</div>
												<div class="middle_summary_text">${fileCategoryInfo.summaryText }</div>
												<div class="flex">
													<div class="middle_category_nm">${fileCategoryInfo.category_nm }</div>
													<div class="middle_extension">${fileCategoryInfo.file_extension }</div>
													<div class="middle_r_dt">${fileCategoryInfo.r_dt }</div>
												</div>
												<input id="middle_file_nm_input" name="fileName"
													type="hidden" value="${fileCategoryInfo.file_nm }">
												<input id="middle_file_extension_input" name="extension"
													type="hidden" value="${fileCategoryInfo.file_extension }">
											</div>
										</form>
									</div>
								</c:if>
								<c:if test="${fileCategoryInfo.file_extension eq '.docx' }">
									<div class="file_open_modal">
										<div class="middle_file_download"
											onclick="file_download(this)">
											<input id="middle_file_nm_download" type="hidden"
												value="${fileCategoryInfo.file_nm }"> <input
												id="middle_extension" type="hidden"
												value="${fileCategoryInfo.file_extension}"> <input
												id="middle_summary_text_download" type="hidden"
												value="${fileCategoryInfo.summaryText }">
											<c:if test="${not empty user}">
												<button class="download_btn">download</button>
											</c:if>

										</div>
										<div class="middle_file_delete" onclick="file_delete(this)">
											<c:if test="${user.user_authority eq 0 }">
												<button class="category_delete_btn">Delete</button>
											</c:if>
											<input id="middle_file_i_file" type="hidden"
												value=${fileCategoryInfo.i_file }>
										</div>
										<div id="middle_content_container"
											onclick="file_open_modal(this)">
											<div class="middle_file_nm">${fileCategoryInfo.file_nm }</div>
											<div class="middle_summary_text">${fileCategoryInfo.summaryText }</div>
											<div class="flex">
												<div class="middle_category_nm">${fileCategoryInfo.category_nm }</div>
												<div class="middle_extension">${fileCategoryInfo.file_extension }</div>
												<div class="middle_r_dt">${fileCategoryInfo.r_dt }</div>
											</div>
											<input id="middle_file_nm_input" name="fileName"
												type="hidden" value="${fileCategoryInfo.file_nm }">
											<input id="middle_summary_text_input"
												name="middle_summary_text_input" type="hidden"
												value="${fileCategoryInfo.summaryText }"> <input
												id="middle_full_text" type="hidden" name="middle_full_text"
												value="${fileCategoryInfo.fullText }">
										</div>
									</div>
								</c:if>
								<c:if test="${fileCategoryInfo.file_extension eq '.doc' }">
									<div class="file_open_modal">
										<div class="middle_file_download"
											onclick="file_download(this)">
											<input id="middle_file_nm_download" type="hidden"
												value="${fileCategoryInfo.file_nm }"> <input
												id="middle_extension" type="hidden"
												value="${fileCategoryInfo.file_extension}"> <input
												id="middle_summary_text_download" type="hidden"
												value="${fileCategoryInfo.summaryText }">
											<c:if test="${not empty user}">
												<button class="download_btn">download</button>
											</c:if>

										</div>
										<div class="middle_file_delete" onclick="file_delete(this)">
											<c:if test="${user.user_authority eq 0 }">
												<button class="category_delete_btn">Delete</button>
											</c:if>
											<input id="middle_file_i_file" type="hidden"
												value=${fileCategoryInfo.i_file }>
										</div>
										<div id="middle_content_container"
											onclick="file_open_modal(this)">
											<div class="middle_file_nm">${fileCategoryInfo.file_nm }</div>
											<div class="middle_summary_text">${fileCategoryInfo.summaryText }</div>
											<div class="flex">
												<div class="middle_category_nm">${fileCategoryInfo.category_nm }</div>
												<div class="middle_extension">${fileCategoryInfo.file_extension }</div>
												<div class="middle_r_dt">${fileCategoryInfo.r_dt }</div>
											</div>
											<input id="middle_file_nm_input" name="fileName"
												type="hidden" value="${fileCategoryInfo.file_nm }">
											<input id="middle_summary_text_input"
												name="middle_summary_text_input" type="hidden"
												value="${fileCategoryInfo.summaryText }"> <input
												id="middle_full_text" type="hidden" name="middle_full_text"
												value="${fileCategoryInfo.fullText }">
										</div>
									</div>
								</c:if>
							</c:forEach>
						</c:when>
						<%-- 소분류 --%>
						<c:when test="${currentPath eq 'smallCategory'}">
							<c:if test="${fileCategoryInfoList.file_extension eq '.pdf' }">
								<div class="file_open_modal">
									<div class="middle_file_download" onclick="file_download(this)">
										<input id="middle_file_nm_download" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_extension" type="hidden"
											value="${fileCategoryInfoList.file_extension}"> <input
											id="middle_summary_text_download" type="hidden"
											value="${fileCategoryInfoList.summaryText }">
										<c:if test="${not empty user}">
											<button class="download_btn">download</button>
										</c:if>

									</div>
									<div class="middle_file_delete" onclick="file_delete(this)">
										<c:if test="${user.user_authority eq 0 }">
											<button class="category_delete_btn">Delete</button>
										</c:if>
										<input id="middle_file_i_file" type="hidden"
											value=${fileCategoryInfoList.i_file }>
									</div>
									<form class="file_open_form" action="/fileOpen" method="get"
										onclick="fileOpen(this)">
										<div id="middle_content_container">
											<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
											<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
											<div class="flex">
												<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
												<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
												<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
											</div>
											<input id="middle_file_nm_input" name="fileName"
												type="hidden" value="${fileCategoryInfoList.file_nm }">
											<input id="middle_file_extension_input" name="extension"
												type="hidden"
												value="${fileCategoryInfoList.file_extension }">
										</div>
									</form>
								</div>
							</c:if>
							<c:if test="${fileCategoryInfoList.file_extension eq '.docx' }">
								<div class="file_open_modal">
									<div class="middle_file_download" onclick="file_download(this)">
										<input id="middle_file_nm_download" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_extension" type="hidden"
											value="${fileCategoryInfoList.file_extension}"> <input
											id="middle_summary_text_download" type="hidden"
											value="${fileCategoryInfoList.summaryText }">
										<c:if test="${not empty user}">
											<button class="download_btn">download</button>
										</c:if>

									</div>
									<div class="middle_file_delete" onclick="file_delete(this)">
										<c:if test="${user.user_authority eq 0 }">
											<button class="category_delete_btn">Delete</button>
										</c:if>
										<input id="middle_file_i_file" type="hidden"
											value=${fileCategoryInfoList.i_file }>
									</div>
									<div id="middle_content_container"
										onclick="file_open_modal(this)">
										<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
										<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
										<div class="flex">
											<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
											<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
											<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
										</div>
										<input id="middle_file_nm_input" name="fileName" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_summary_text_input"
											name="middle_summary_text_input" type="hidden"
											value="${fileCategoryInfoList.summaryText }"> <input
											id="middle_full_text" type="hidden" name="middle_full_text"
											value="${fileCategoryInfoList.fullText }">
									</div>
								</div>
							</c:if>
							<c:if test="${fileCategoryInfoList.file_extension eq '.doc' }">
								<div class="file_open_modal">
									<div class="middle_file_download" onclick="file_download(this)">
										<input id="middle_file_nm_download" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_extension" type="hidden"
											value="${fileCategoryInfoList.file_extension}"> <input
											id="middle_summary_text_download" type="hidden"
											value="${fileCategoryInfoList.summaryText }">
										<c:if test="${not empty user}">
											<button class="download_btn">download</button>
										</c:if>

									</div>
									<div class="middle_file_delete" onclick="file_delete(this)">
										<c:if test="${user.user_authority eq 0 }">
											<button class="category_delete_btn">Delete</button>
										</c:if>
										<input id="middle_file_i_file" type="hidden"
											value=${fileCategoryInfoList.i_file }>
									</div>
									<div id="middle_content_container"
										onclick="file_open_modal(this)">
										<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
										<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
										<div class="flex">
											<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
											<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
											<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
										</div>
										<input id="middle_file_nm_input" name="fileName" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_summary_text_input"
											name="middle_summary_text_input" type="hidden"
											value="${fileCategoryInfoList.summaryText }"> <input
											id="middle_full_text" type="hidden" name="middle_full_text"
											value="${fileCategoryInfoList.fullText }">
									</div>
								</div>
							</c:if>
						</c:when>
						<%-- 메인화면 전체카테고리 --%>
						<%-- 검색 시에도 이 카테고리가 뿌려짐 --%>
						<c:otherwise>
							<c:if test="${fileCategoryInfoList.file_extension eq '.pdf'}">
								<div class="file_open_modal">
									<div class="middle_file_download" onclick="file_download(this)">
										<input id="middle_file_nm_download" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_extension" type="hidden"
											value="${fileCategoryInfoList.file_extension}"> <input
											id="middle_summary_text_download" type="hidden"
											value="${fileCategoryInfoList.summaryText }">
										<c:if test="${not empty user}">
											<button class="download_btn">download</button>
										</c:if>

									</div>
									<div class="middle_file_delete" onclick="file_delete(this)">
										<c:if test="${user.user_authority eq 0 }">
											<button class="category_delete_btn">Delete</button>
										</c:if>
										<input id="middle_file_i_file" type="hidden"
											value=${fileCategoryInfoList.i_file }>
									</div>
									<form class="file_open_form" action="/fileOpen" method="get"
										onclick="fileOpen(this)">
										<div id="middle_content_container">
											<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
											<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
											<div class="flex">
												<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
												<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
												<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
											</div>
											<input id="middle_file_nm_input" name="fileName"
												type="hidden" value="${fileCategoryInfoList.file_nm }">
											<input id="middle_file_extension_input" name="extension"
												type="hidden"
												value="${fileCategoryInfoList.file_extension }">
										</div>
									</form>
								</div>
							</c:if>
							<c:if test="${fileCategoryInfoList.file_extension eq '.docx'}">
								<div class="file_open_modal">
									<div class="middle_file_download" onclick="file_download(this)">
										<input id="middle_file_nm_download" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_extension" type="hidden"
											value="${fileCategoryInfoList.file_extension}"> <input
											id="middle_summary_text_download" type="hidden"
											value="${fileCategoryInfoList.summaryText }"> <input
											id="middle_full_text" type="hidden" name="middle_full_text"
											value="${fileCategoryInfoList.fullText }">
										<c:if test="${not empty user}">
											<button class="download_btn">download</button>
										</c:if>

									</div>
									<div class="middle_file_delete" onclick="file_delete(this)">
										<c:if test="${user.user_authority eq 0 }">
											<button class="category_delete_btn">Delete</button>
										</c:if>
										<input id="middle_file_i_file" type="hidden"
											value=${fileCategoryInfoList.i_file }>
									</div>
									<div id="middle_content_container"
										onclick="file_open_modal(this)">
										<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
										<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
										<div class="flex">
											<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
											<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
											<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
										</div>
										<input id="middle_file_nm_input" name="fileName" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_summary_text_input"
											name="middle_summary_text_input" type="hidden"
											value="${fileCategoryInfoList.summaryText }"> <input
											id="middle_full_text" type="hidden" name="middle_full_text"
											value="${fileCategoryInfoList.fullText }">
									</div>
								</div>
							</c:if>
							<c:if test="${fileCategoryInfoList.file_extension eq '.doc'}">
								<div class="file_open_modal">
									<div class="middle_file_download" onclick="file_download(this)">
										<input id="middle_file_nm_download" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_extension" type="hidden"
											value="${fileCategoryInfoList.file_extension}"> <input
											id="middle_summary_text_download" type="hidden"
											value="${fileCategoryInfoList.summaryText }"> <input
											id="middle_full_text" type="hidden" name="middle_full_text"
											value="${fileCategoryInfoList.fullText }">
										<c:if test="${not empty user}">
											<button class="download_btn">download</button>
										</c:if>

									</div>
									<div class="middle_file_delete" onclick="file_delete(this)">
										<c:if test="${user.user_authority eq 0 }">
											<button class="category_delete_btn">Delete</button>
										</c:if>
										<input id="middle_file_i_file" type="hidden"
											value=${fileCategoryInfoList.i_file }>
									</div>
									<div id="middle_content_container"
										onclick="file_open_modal(this)">
										<div class="middle_file_nm">${fileCategoryInfoList.file_nm }</div>
										<div class="middle_summary_text">${fileCategoryInfoList.summaryText }</div>
										<div class="flex">
											<div class="middle_category_nm">${fileCategoryInfoList.category_nm }</div>
											<div class="middle_extension">${fileCategoryInfoList.file_extension }</div>
											<div class="middle_r_dt">${fileCategoryInfoList.r_dt }</div>
										</div>
										<input id="middle_file_nm_input" name="fileName" type="hidden"
											value="${fileCategoryInfoList.file_nm }"> <input
											id="middle_summary_text_input"
											name="middle_summary_text_input" type="hidden"
											value="${fileCategoryInfoList.summaryText }"> <input
											id="middle_full_text" type="hidden" name="middle_full_text"
											value="${fileCategoryInfoList.fullText }">
									</div>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<div class="flex_page">
					<c:if test="${pagingVO.pagination.existPrevPage == true}">
						<div id="first_btn" onclick="movePage(1)">first</div>
						<div id="prev_btn"
							onclick="movePage(${pagingVO.pagination.startPage - 1})">prev</div>
					</c:if>
					<c:forEach var="page" begin="${pagingVO.pagination.startPage}"
						end="${pagingVO.pagination.endPage}">
						<div class="page_value_list" onclick="movePage(${page})">${page}</div>
					</c:forEach>
					<c:if test="${pagingVO.pagination.existNextPage == true}">
						<div id="next_btn"
							onclick="movePage(${pagingVO.pagination.endPage + 1})">next</div>
						<div id="last_btn"
							onclick="movePage(${pagingVO.pagination.totalPageCount})">last</div>
					</c:if>
				</div>
			</div>

			<div id="sub_content_container">
				<div id="search_container">
					<select name="category" class="search_select">
						<option value="searchTitle" selected="selected" name="searchTitle">Title</option>
						<option value="searchCategory" name="searchCategory">Content</option>
					</select> <input id="search_input" type="text" name="content"
						onkeyup="enterkey()"> <span id="search_btn"
						onclick="searchForm()">Submit</span>
				</div>
				<div id="category_list_container">
					<form action="/category" method="get">
						<input id="entire_categoey_submit" type="submit"
							value="Total Categories (${totalNumberPosts})">
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

	<div id="file_modal_docx_div">
		<div class="file_modal_docx_content">
			<div class="file_dcx_title"></div>
			<div class="file_docx_content"></div>
		</div>
		<div onclick="file_modal_docx_close()" class="file_modal_docx__layer"></div>
	</div>


	<!-- 파일 업로드 모달창 -->
	<div id="file_modal_div">
		<div class="file_modal_content">
			<div id="file_upload_title">File Upload</div>
			<form id="fileForm" method="post" enctype="multipart/form-data">
				<div class="filebox">
					<label for="fileUpload">Upload</label> <input type="file"
						id="fileUpload" name="fileUpload" multiple="multiple"
						onchange=changeFileName(this.files)> <span
						class="file_name"></span>
				</div>
				<div id="file_upload_category_container" class="flex">
					<select id="top_category_choice_upload"
						onchange="uploadCategoryChange(this)">
						<option selected="selected" value="0">Main category</option>
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
						<option value="0">Subcategory</option>
					</select>
				</div>
			</form>
			<div class=flex>
				<div id="upload" onclick="upload()">Upload</div>
				<div id="upload_close" onclick="upload_modal_close()">Close</div>
			</div>
		</div>
		<div id="file_upload_wait_div">
			<p>Uploading file</p>
			<p>Please wait a moment</p>
		</div>
		<div class="file_modal_layer"></div>
	</div>
	
	<div id="file_download_wait_div">
			<p>downloading file</p>
			<p>Please wait a moment</p>
		</div>

	<!-- 카테고리 등록 모달창 -->
	<div id="modal_insert">
		<div class="modal_content">
			<div id="category_insert_title">Register Category</div>
			<div>
				<div class="flex">
					<div id="large_category">Main category</div>
					<div id="small_category">Subcategory</div>
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
				<div id="insert_category_btn" onclick="insert_cateogry()">Registration</div>
				<div id="insert_category_close" onclick="insert_category_close()">Close</div>
			</div>
		</div>
		<div class="modal_layer"></div>
	</div>

	<!-- 카테고리 삭제 모달창 -->
	<div id="modal_delete">
		<div class="modal_delete_content">
			<div id="category_delete_title">Delete Category</div>
			<div>
				<div class="flex">
					<div id="large_delete_category">Main category</div>
					<div id="small_delete_category">Subcategory</div>
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
					<select id="top_category_change"
						onchange="top_categoryChange(this)">
						<option value="0">Main category</option>
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
						<option value="0">Subcategory</option>
					</select>
				</div>
			</div>
			<div class="flex">
				<div id="delete_category_btn" onclick="delete_category()">Delete</div>
				<div id="delete_category_close" onclick="delete_category_close()">Close</div>
			</div>
		</div>
		<div class="modal_delete_layer"></div>
	</div>
</div>
<footer>
	<p>If you need any help, please contact Jinsil Choi via
		<span id="footer_email">"diane45661@gmail.com"</span></p>
</footer>

<script defer src="/res/js/main/home.js?ver=34"></script>
<script defer src="/res/js/pdf/pdfUpload.js?ver=25"></script>
<script defer src="/res/js/pdf/fileOpen.js?ver=19"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script defer src="/res/js/pdf/fileDownload.js?ver=34"></script>