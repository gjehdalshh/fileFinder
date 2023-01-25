function moveFindId() {
	location.href = `/user/findId`
}
function moveFindPw() {
	location.href = `/user/findPw`
}

function enterkeyId() {
	if (window.event.keyCode == 13) {
		findId()
	}
}
function enterkeyPw() {
	if (window.event.keyCode == 13) {
		findPw()
	}
}
function enterkeyChangePw() {
	if (window.event.keyCode == 13) {
		changePw()
	}
}

function goHome() {
	location.href = `/`
}
function login() {
	location.href = `/user/login`
}

function findId() {
	let nm = document.querySelector('#find_nm')
	let email = document.querySelector('#find_email')
	let showId = document.querySelector('#showId')
	let id_find_text = document.querySelector('#id_find_text')
	let show_id_div = document.querySelector('#show_id_div')
	let login_btn = document.querySelector('#login_btn')

	let param = {
		user_nm: nm.value,
		user_email: email.value
	}

	fetch(`/user/findId`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(param)
	}).then(function(res) {
		return res.json()
	}).then(function(data) {
		if (data.result == null) {
			alert('Member information does not exist')
			return
		}
		show_id_div.style.display = 'block'
		login_btn.style.display = 'block'
		id_find_text.innerText = 'Your ID'
		showId.innerText = data.result
	})
}

function findPw() {
	let nm = document.querySelector('#find_nm')
	let email = document.querySelector('#find_email')
	let id = document.querySelector('#find_id')
	let change_content = document.querySelector('#change_content')
	let content_pw = document.querySelector('#content_pw')
	
	let param = {
		user_nm: nm.value,
		user_email: email.value,
		user_id: id.value
	}

	fetch(`/user/findPw`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(param)
	}).then(function(res) {
		return res.json()
	}).then(function(data) {
		if (data.result == null) {
			alert('Member information does not exist')
			return
		}
		content_pw.style.display = 'none'
		change_content.style.display = 'block'
	})
}

function changePw() {
	let user_id = document.querySelector('#user_id')
	let change_password = document.querySelector('#change_password')
	let change_password_confirm = document.querySelector('#change_password_confirm')
	
	let param = {
		user_id: user_id.value,
		change_pw: change_password.value,
		confirm_change_pw: change_password_confirm.value
	}
	
	fetch(`/user/changePw`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(param)
	}).then(function(res) {
		return res.json()
	}).then(function(data) {
		console.log(data.result)
		switch(data.result) {
			case 1:
				alert('Password change successful')
				location.href=`/`
				break;
			case 2:
				alert('ID does not exist')
				break;
			case 3:
				alert('Confirm password to change')
				break;
		}
	})
}