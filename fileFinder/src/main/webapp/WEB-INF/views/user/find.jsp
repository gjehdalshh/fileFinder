<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/res/css/user/find.css?ver=21">

<div id="main_container">
	<div id="find_container">
		<div id="title" onclick="goHome()">Gale Document Finder</div>
		<c:if test="${find eq 'id'}">
			<div id="content_id">
				<div>
					<input id="find_nm" type="text" onkeyup="enterkeyId()"
						placeholder="Name">
				</div>
				<div>
					<input id="find_email" type="text" onkeyup="enterkeyId()"
						placeholder="Email">
				</div>
				<div id="find_btn" onclick="findId()">Find ID</div>
				<div id="show_id_div">
					<div id="id_find_text"></div>
					<div id="showId"></div>
				</div>
				<div id="login_btn" onclick="login()">Login</div>
			</div>
		</c:if>
		<c:if test="${find eq 'pw'}">
			<div id="content_pw">
				<div>
					<input id="find_nm" type="text" onkeyup="enterkeyPw()"
						placeholder="Name">
				</div>
				<div>
					<input id="find_email" type="text" onkeyup="enterkeyPw()"
						placeholder="Email">
				</div>
				<div>
					<input id="find_id" type="text" onkeyup="enterkeyPw()"
						placeholder="ID">
				</div>
				<div id="find_btn" onclick="findPw()">Find Password</div>
			</div>
		</c:if>
		<div id="change_content">
			<div>
				<input id="user_id" type="text" onkeyup="enterkeyChangePw()"
					placeholder="ID">
			</div>
			<div>
				<input id="change_password" type="text" onkeyup="enterkeyChangePw()"
					placeholder="Password to change">
			</div>
			<div>
				<input id="change_password_confirm" type="text" onkeyup="enterkeyChangePw()"
					placeholder="Confirm password to change">
			</div>
			<div id="change_btn" onclick="changePw()">Change Password</div>
		</div>
	</div>
</div>

<script defer src="/res/js/user/find.js?ver=22"></script>