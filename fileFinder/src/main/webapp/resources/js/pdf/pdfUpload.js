// 업로드전 path 위치 설정
function changeCategory() {
	let category_choice_upload = document.querySelector('#category_choice_upload')
	console.log(category_choice_upload.value)
	fetch(`/filePath`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(category_choice_upload.value)
	})
}

// 파일 업로드
function upload() {
	let fileUpload = document.querySelector('#fileUpload')

	let files = fileUpload.files
	let formData = new FormData()
	filesArr = Array.prototype.slice.call(files);

	for (var i = 0; i < filesArr.length; i++) {
		formData.append('files', filesArr[i]);
	}

	fetch(`/fileUpload`, {
		method: 'POST',
		headers: {
			contentType: false,               // * 중요 *
			processData: false,               // * 중요 *
			enctype: 'multipart/form-data',  // * 중요 *
		},
		body: formData
	}).then(function(res) {
		res.text().then(function(text) {
			console.log(text)
			if (text == 1) {
				alert('파일이 업로드되었습니다.')
				location.reload()
			} else if (text == 2) {
				alert('카테고리를 선택해주세요.')
			}
		})
	})
}