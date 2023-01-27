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
	let affiliation = document.querySelector('#join_affiliation').value

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
	} else if(affiliation == '') {
		alert('Please enter your Department')
		return
	}

	let param = {
		user_id: id,
		user_pw: pw,
		user_nm: nm,
		user_email: email,
		user_authority: 1,
		user_department: affiliation
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
				alert("Successfully registered as a member, Please wait for the manager's approval")
				location.href = `/`
				break;
			case 2:
				alert('ID already exists')
				break;
			case 3:
				alert('e-mail already exists')
				break;
			case 4:
				alert('Please fill it out in the form of e-mail')
				break;
		}
	})
}
