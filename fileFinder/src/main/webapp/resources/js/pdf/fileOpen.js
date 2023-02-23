let file_docx_content = ''
let middle_text = ''

function fileOpen(file_open_form) {
	if(user_session == '') { // 로그인 시 이용 가능
		return
	}
	file_open_form.encoding  = "multipart/form-data"
	file_open_form.submit()
}


let file_modal_docx_div = document.querySelector('#file_modal_docx_div')

function file_open_modal(e) {
	if(user_session == '') { // 로그인 시 이용 가능
		return
	}
	file_modal_docx_div.style.display = 'block';
	middle_text = e.querySelector('#middle_full_text').value

	if(middle_text == '') { 
		middle_text = e.querySelector('#middle_summary_text_input').value
	}

	let file_dcx_title = document.querySelector('.file_dcx_title')
	file_docx_content = document.querySelector('.file_docx_content')
	let title = middle_text.split('\n', 1);

	file_dcx_title.textContent = title[0]
	file_docx_content.textContent = middle_text.substr(title[0].length+1)
	
	changeTextColor()
}

function file_modal_docx_close() {
	file_modal_docx_div.style.display = 'none';
}
