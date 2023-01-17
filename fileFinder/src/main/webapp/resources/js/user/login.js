function goHome() {
	location.href = `/`
}

function enterkey() {
	if (window.event.keyCode == 13) {
		login()
	}
}

function login() {
	let id = document.querySelector('#login_id')
	let pw = document.querySelector('#login_pw')

	let param = {
		user_id: id.value,
		user_pw: pw.value
	}

	fetch(`/user/login`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(param)
	}).then(function(res) {
		return res.json()
	}).then(function(data) {
		switch (data) {
			case 1:
				alert('Login successful')
				location.href = `/`
				break
			case 2:
				alert('Member information does not exist')
				break
			case 3:
				alert('Waiting for membership registration approval')
				break;
		}
	})
}

function findId() {

}

function findPw() {

}