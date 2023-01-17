<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" href="/res/css/user/join.css?ver=4">

<div id="main_container">
	<div id="join_container">
		<div id="title" onclick="goHome()">Gale Document Finder</div>
		<div id="content">
			<div>
				<input id="join_id" type="text" onkeyup="enterkey()"
					placeholder="ID">
			</div>
			<div>
				<input id="join_pw" type="text" onkeyup="enterkey()"
					placeholder="Password">
			</div>
			<div>
				<input id="join_nm" type="text" onkeyup="enterkey()"
					placeholder="Name">
			</div>
			<div>
				<input id="join_email" type="text" onkeyup="enterkey()"
					placeholder="Email">
			</div>
			<div>
				<input id="join_department" type="text" onkeyup="enterkey()"
					placeholder="Department">
			</div>
			<div id="join_btn" onclick="join()">Sign Up</div>
		</div>
	</div>
</div>


<script defer src="/res/js/user/join.js?ver=3"></script>