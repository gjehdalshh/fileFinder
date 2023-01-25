<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/res/css/user/management.css?ver=34">

<div id="title_container">
	<div class="flex_title">
		<div id="title" onclick="goHome()">Gale Document Finder</div>
	</div>
</div>
<div id="entire_container">
	<div id="wholeViewContainer">
		<div id="content_container">
			<div id="middle_title">${current_title }</div>
			<div>
				<c:forEach var="userInfoList" items="${userInfoList}" varStatus="status">
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
						<div class="user_authority_div">
							<div class="user_title_authority">authority</div>
							<c:if test="${userInfoList.user_authority == 0}">
								<div>Manager</div>
							</c:if>
							<c:if test="${userInfoList.user_authority == 1}">
								<div>Member</div>
							</c:if>
						</div>
						<c:if test="${not empty user}">
							<c:if test="${current_title != 'Request for membership' }">
							<div class="select_div">
								<div class="select_sub_div">
									<select name="userAuthority" class="selectUserAuthority">
										<option value="manager" name="manager">Manager</option>
										<option value="member" name="member" selected="selected">Member</option>
										<option value="delete" name="delete">Delete</option>
									</select>
								</div>
								<input class="i_user" type="hidden" value="${userInfoList.i_user }">
								<div class="change_btn" onclick="changeUserAuthority(${status.index})">change</div>
							</div>
							</c:if>
						</c:if>
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
						<input class="sub_category" type="submit"
							value="Member information">
					</form>
				</div>
				<div class="management_category">
					<form action="/user/userRistration" method="get">
						<input class="sub_category" type="submit"
							value="Request for membership">
					</form>
				</div>
			</div>
		</div>
	</div>
</div>


<script defer src="/res/js/user/management.js?ver=22"></script>