

function fileOpen(file_open_form) {
	file_open_form.encoding  = "multipart/form-data"
	file_open_form.submit()
}


let file_modal_docx_div = document.querySelector('#file_modal_docx_div')

function file_open_modal(e) {
	file_modal_docx_div.style.display = 'block';
	let middle_text = e.querySelector('#middle_full_text').value

	if(middle_text == '') { 
		middle_text = e.querySelector('#middle_summary_text_input').value
	}

	let file_dcx_title = document.querySelector('.file_dcx_title')
	let file_docx_content = document.querySelector('.file_docx_content')
	let title = middle_text.split('\n', 1);

	file_dcx_title.textContent = title[0]
	file_docx_content.textContent = middle_text.substr(title[0].length+1)
	
	changeColor()
}

function file_modal_docx_close() {
	file_modal_docx_div.style.display = 'none';
}
