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


