
// 대분류, 소분류 뿌리기
function uploadCategoryChange(e) {

	let sub_category_top_upload = document.querySelectorAll('.sub_category_top_upload')
	let sub_category_nm_upload = document.querySelectorAll('.sub_category_nm_upload')
	let sub_category_i_category_upload = document.querySelectorAll('.sub_category_i_category_upload')
	let sub_category_choice_upload = document.querySelector('#sub_category_choice_upload')

	sub_category_choice_upload.length = 0;

	for (i = 0; i < sub_category_nm_upload.length; i++) {
		let opt = document.createElement("option");
		if (e.value == sub_category_top_upload.item(i).value) {
			opt.value = sub_category_i_category_upload.item(i).value
			opt.innerHTML = sub_category_nm_upload.item(i).value
			sub_category_choice_upload.append(opt);
		}
	}
	changeCategory()
}

function subUploadCategoryChange(e) {
	changeCategory()
}

// 업로드전 path 위치 설정
function changeCategory() {
	let sub_category_choice_upload = document.querySelector('#sub_category_choice_upload')
	console.log(sub_category_choice_upload.value)
	fetch(`/filePath`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(sub_category_choice_upload.value)
	})
}

function changeFileName(file) {
	let fileName = document.querySelector('.file_name')
	
	fileName.innerHTML = file[0].name
}

// 파일 업로드
function upload() {
	let fileUpload = document.querySelector('#fileUpload')
	let top_category_choice_upload = document.querySelector('#top_category_choice_upload')

	if (top_category_choice_upload.value == 0) {
		alert('카테고리를 선택해주세요.')
		return
	}
	if (!fileUpload.value) {
		alert("파일을 첨부해 주세요.");
		return
	}

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
			} else if (text == 3) {
				alert('파일이 이미 존재합니다.')
			}
		})
	})
}