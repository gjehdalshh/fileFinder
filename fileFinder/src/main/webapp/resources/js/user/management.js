function goHome() {
	location.href = `/`
}

function approval(e) {
	let id = e.querySelector('.id').value

	fetch(`/user/approval`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: id
	}).then(function(res) {
		return res.json()
	}).then(function(data) {
		switch (data) {
			case 1:
				alert('Membership Sign-up Approved')
				location.reload()
				break
		}
	})
}

function unapproval(e) {
	let id = e.querySelector('.id').value

	fetch('/user/unapproval/' + id, {
		method: 'DELETE',
	}).then(function(res) {
		res.text().then(function(text) {
			console.log(text)
			if (text == 1) {
				alert('Membership Sign-up Unapproved')
				location.reload()
			}
		})
	})
}

function changeUserAuthority(index) {

	let selectUserAuthority = document.querySelectorAll('.selectUserAuthority')
	let i_user = document.querySelectorAll('.i_user')
	selectUserAuthority = selectUserAuthority[index].options[selectUserAuthority[index].selectedIndex].value
	console.log(i_user[index].value)
	console.log(selectUserAuthority)
	
	let param = {
		i_user: i_user[index].value,
		user_authority: selectUserAuthority
	}

	fetch(`/user/changeUserAuthority`, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(param)
	}).then(function(res) {
		return res.json()
	}).then(function(data) {
		switch (data) {
			case 1:
				alert('Member authority change complete')
				location.reload()
				break
		}
	})
}


function getElementIndex(element, range) {
	// 추가
	if (!!range) return [].indexOf.call(element, range);
	return [].indexOf.call(element.parentNode.children, element);
}

