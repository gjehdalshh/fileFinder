

function fileOpen(file_open_form) {
	file_open_form.encoding  = "multipart/form-data"
	file_open_form.submit()
}


let file_modal_docx_div = document.querySelector('#file_modal_docx_div')

function file_open_modal() {
	file_modal_docx_div.style.display = 'block';
	
	let middle_summary_text_input = document.querySelector('#middle_summary_text_input').value
	let file_dcx_title = document.querySelector('.file_dcx_title')
	let file_docx_content = document.querySelector('.file_docx_content')
	console.log(middle_summary_text_input)
	let title = middle_summary_text_input.split('\n', 1);

	file_dcx_title.textContent = title[0]
	file_docx_content.textContent = middle_summary_text_input.substr(title[0].length+1)
	console.log(title[0].length)
	console.log(middle_summary_text_input)
	console.log(file_dcx_title.textContent)
	console.log(file_docx_content.textContent)
}

function file_modal_docx_close() {
	file_modal_docx_div.style.display = 'none';
}
