<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/res/css/user/login.css?ver=15">

<div id="main_container">
	<div id="login_container">
		<div id="title" onclick="goHome()">Gale Document Finder</div>
		<div id="content">
			<div>
				<input id="login_id" type="text" onkeyup="enterkey()"
					placeholder="ID">
			</div>
			<div>
				<input id="login_pw" type="text" onkeyup="enterkey()"
					placeholder="Password">
			</div>
			<div id="login_btn" onclick="login()">Login</div>
		</div>
		<div class="find_div">
			<div class="find_id" onclick="moveFindId()">Find ID</div>
			<div class="find_pw" onclick="moveFindPw()">Find Password</div>
		</div>
	</div>
</div>

<script defer src="/res/js/user/login.js?ver=5"></script>
<script defer src="/res/js/user/find.js?ver=5"></script>