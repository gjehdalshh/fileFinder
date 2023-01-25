function downloadTotalFile() {
	let category = document.querySelector('#currentPathForDownload').value
	
	let param = {
		currentPath: category
	}
	downloadAjax(param)
}

function downloadAjax(param) {
	
	fetch(`/totalFileDownload`, {
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
				alert('Download Successful')
				break
			case 2:
				alert('Download failed')
				break
		}
	})
}

function file_download(e) {
	console.log(e)
	let middle_text = e.querySelector('#middle_summary_text_download').value
	let extension = e.querySelector('#middle_extension')
	let nm = e.querySelector('#middle_file_nm_download')
	if (currentPath == "searchCategory") {
		middle_text = e.querySelector('#middle_full_text').value
	}
	console.log(nm)
	console.log(extension)

	let param = {
		file_nm: nm.value,
		file_extension: extension.value,
		fullText: middle_text
	}

	fetch(`/fileDownload`, {
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
				alert('Download Successful')
				break
			case 2:
				alert('Download failed')
				break
		}
	})
}

