

function fileOpen(file_open_form) {
	file_open_form.encoding  = "multipart/form-data"
	file_open_form.submit()
}


let file_modal_docx_div = document.querySelector('#file_modal_docx_div')

function file_open_modal(e) {

	file_modal_docx_div.style.display = 'block';
	
	let middle_summary_text_input = e.querySelector('#middle_summary_text_input').value
	console.log(middle_summary_text_input)
	let file_dcx_title = document.querySelector('.file_dcx_title')
	let file_docx_content = document.querySelector('.file_docx_content')
	let title = middle_summary_text_input.split('\n', 1);

	file_dcx_title.textContent = title[0]
	file_docx_content.textContent = middle_summary_text_input.substr(title[0].length+1)
	console.log("제목길이 : " + title[0].length)
	console.log("내용 : " + middle_summary_text_input)
	console.log("제목 : " + file_dcx_title.textContent)
	console.log("제목 이후 첫번쨰 내용 : " + file_docx_content.textContent)
}

function file_modal_docx_close() {
	file_modal_docx_div.style.display = 'none';
}
