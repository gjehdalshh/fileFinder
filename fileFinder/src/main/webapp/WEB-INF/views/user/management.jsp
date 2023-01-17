<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/res/css/user/management.css?ver=21">

<div id="title_container">
	<div class="flex_title">
		<div id="title" onclick="goHome()">Gale Document Finder</div>
	</div>
</div>
<div id="entire_container">
	<div id="wholeViewContainer">
		<div id="content_container">
			<div id="middle_title">Member Information</div>
			<div>
				<c:forEach var="userInfoList" items="${userInfoList}">
					<div class="user_info_list_div">
						<div class="user_id_div">
							<div class="user_title_id">ID</div>
							<div>${userInfoList.user_id}</div>
						</div>
						<div class="user_nm_div">
							<div class="user_title_nm">Name</div>
							<div>${userInfoList.user_nm}</div>
						</div>
						<div class="user_email_div">
							<div class="user_title_email">Email</div>
							<div>${userInfoList.user_email}</div>
						</div>
						<div class="user_department_div">
							<div class="user_title_department">Department</div>
							<div>${userInfoList.user_department}</div>
						</div>
							<c:if test="${userInfoList.user_status eq 0 }">
							<div class="approval_div">
								<div onclick="approval(this)">
									<button class="approval">approval</button>
									<input class="id" type="hidden" value="${userInfoList.user_id}">
								</div>
								<div onclick="unapproval(this)">
									<button class="unapproval">unapproval</button>
									<input class="id" type="hidden" value="${userInfoList.user_id}">
								</div>
							</div>
							</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
		<div id="sub_content_container">
			<div id="category_list_container">
				<div class="management_category">
					<form action="/user/management" method="get">
						<input type="submit" value="유저 정보 확인">
					</form>
				</div>
				<div class="management_category">
					<form action="/user/userRistration" method="get">
						<input type="submit" value="회원가입 승인 대기">
					</form>
				</div>
			</div>
		</div>
	</div>
</div>


<script defer src="/res/js/user/management.js?ver=8"></script>