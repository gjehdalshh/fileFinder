function goHome() {
	location.href = `/`
}

function enterkey() {
	if (window.event.keyCode == 13) {
		join()
	}
}

function join() {
	let id = document.querySelector('#join_id').value
	let pw = document.querySelector('#join_pw').value
	let nm = document.querySelector('#join_nm').value
	let email = document.querySelector('#join_email').value
	let department = document.querySelector('#join_department').value

	if(id == '') {
		alert('Please enter your ID')
		return
	} else if(pw == '') {
		alert('Please enter your Password')
		return
	} else if(nm == '') {
		alert('Please enter your Name')
		return
	} else if(email == '') {
		alert('Please enter your Email')
		return
	} else if(department == '') {
		alert('Please enter your Department')
		return
	}

	let param = {
		user_id: id,
		user_pw: pw,
		user_nm: nm,
		user_email: email,
		user_authority: 1,
		user_department: department
	}

	fetch(`/user/join`, {
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
				alert('회원가입에 성공하였습니다.')
				location.href = `/`
				break;
			case 2:
				alert('아이디가 이미 존재합니다.')
				break;
			case 3:
				alert('이메일이 이미 존재합니다.')
				break;
			case 4:
				alert('이메일 형식으로 작성해주세요.')
				break;
		}
	})
}
