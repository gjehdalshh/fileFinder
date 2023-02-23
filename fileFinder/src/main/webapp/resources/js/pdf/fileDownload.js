function downloadTotalFile(search_count) {
	let category = document.querySelector('#currentPathForDownload').value

	console.log(search_count)
	if (search_count == 0) {
		alert('file does not exist')
		return
	}
	let param = {
		currentPath: category
	}
	downloadAjax(param)
}

function downloadAjax(param) {

	let file_download_wait_div = document.querySelector('#file_download_wait_div')
	file_download_wait_div.style.display = 'block'

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
				file_download_wait_div.style.display = 'none'
				alert('Download Successful')
				break
			case 2:
				file_download_wait_div.style.display = 'none'
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
	let file_download_wait_div = document.querySelector('#file_download_wait_div')
	file_download_wait_div.style.display = 'block'


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
				file_download_wait_div.style.display = 'none'
				break
			case 2:
				alert('Download failed')
				file_download_wait_div.style.display = 'none'
				break
		}
	})
}

