let modal_insert = document.querySelector('#modal_insert')
let modal_delete = document.querySelector('#modal_delete')

let large_category = document.querySelector('#large_category')
let small_category = document.querySelector('#small_category')

let large_delete_category = document.querySelector('#large_delete_category')
let small_delete_category = document.querySelector('#small_delete_category')

let large_category_div = document.querySelector('#large_category_div')
let small_category_div = document.querySelector('#small_category_div')

let large_delete_category_div = document.querySelector('#large_delete_category_div')
let small_delete_category_div = document.querySelector('#small_delete_category_div')

let order = 1;

let file_modal_div = document.querySelector('#file_modal_div')

let sub_category_i_cateogry = document.querySelectorAll('#sub_category_i_cateogry')

let currentPath = document.querySelector('#currentPath').value
let largeCategory = document.querySelector('#largeCategory').value
let smallCategory = document.querySelector('#smallCategory').value

window.onload = function() {
	changeColor()
}

function goHome() {
	location.href = `/`
}

function changeColor() { // 검색 시 특정 문자 색 변경
	if(currentPath == "searchTitle") {
		return
	}
	console.log("확인")
	let contentValue = document.querySelector('#searchContent').value
	if (contentValue == '') {
		return
	}
	
	let middle_summary_text = document.querySelectorAll('.middle_summary_text')

	let middle_file_nm = document.querySelectorAll('.middle_file_nm')

	let nm = "<span style='color:red;font-size:17px';>" + contentValue + "</span>"
	let arr = []
	let summary = []

	for (let i = 0; i < middle_summary_text.length; i++) {
		let index = middle_summary_text[i].innerHTML.toLowerCase().indexOf(contentValue.toLowerCase())
		let temp = middle_summary_text[i].innerHTML.slice(index, index + contentValue.length)
		arr[i] = temp; // 대소문자 관계없이 해당 글자를 그대로 가져옴, Japan, japan 등
	}

	// 중복 제거
	let set = new Set(arr)
	arr = Array.from(set)

	for (let i = 0; i < arr.length; i++) {
		summary[i] = "<span style='color:red;font-size:15px';>" + arr[i] + "</span>"
	}

	for (let i = 0; i < middle_summary_text.length; i++) {
		for (let j = 0; j < arr.length; j++) {
			middle_summary_text[i].innerHTML = middle_summary_text[i].innerHTML.replaceAll(arr[j], summary[j]);
		}
		middle_file_nm[i].innerHTML = middle_file_nm[i].innerHTML.replaceAll(contentValue, nm);
	}

	// 전체 텍스트 검색 색상 변경
	let j = 0
	let fullArr = []
	let fullText = []
	
	let firstIndex = middle_text.toLowerCase().indexOf(contentValue)
	fullArr[j] = middle_text.slice(firstIndex, firstIndex + contentValue.length)
	while (firstIndex != -1) {
		fullArr[j++] = middle_text.slice(firstIndex, firstIndex + contentValue.length)
		firstIndex = middle_text.toLowerCase().indexOf(contentValue, firstIndex + contentValue.length);
	}

	set = new Set(fullArr)
	fullArr = Array.from(set)

	for (let i = 0; i < fullArr.length; i++) {
		fullText[i] = "<span style='color:red;font-size:15px';>" + fullArr[i] + "</span>"
	}

	for (let i = 0; i < fullArr.length; i++) {
		middle_text = middle_text.replaceAll(fullArr[i], fullText[i])
	}
	file_docx_content.innerHTML = middle_text
}


$('#search_input').keyup(function() { // 검색 시 글자에 따라 실시간 색 변경
	var search = $('#search_input').val();
	$(".middle_summary_text:contains('" + search + "')").each(function() {
		var regex = new RegExp(search, 'gi');
		$(this).html($(this).text().replace(regex, "<span class='txt-hlight'>" + search + "</span>"));
	});
});

/* --------------- 대/소분류 구분 -------------------*/

large_category.onclick = function() {
	order = 1
	large_category_div.style.display = 'block'
	small_category_div.style.display = 'none'

	large_category.style.backgroundColor = "d1ebf6"
	small_category.style.backgroundColor = "white"
}
small_category.onclick = function() {
	order = 2
	large_category_div.style.display = 'none'
	small_category_div.style.display = 'block'

	large_category.style.backgroundColor = "white"
	small_category.style.backgroundColor = "d1ebf6"
}

large_delete_category.onclick = function() {
	order = 1
	large_delete_category_div.style.display = 'block'
	small_delete_category_div.style.display = 'none'

	large_delete_category.style.backgroundColor = "d1ebf6"
	small_delete_category.style.backgroundColor = "white"
}
small_delete_category.onclick = function() {
	order = 2
	large_delete_category_div.style.display = 'none'
	small_delete_category_div.style.display = 'block'

	large_delete_category.style.backgroundColor = "white"
	small_delete_category.style.backgroundColor = "d1ebf6"
}

/* --------------- 파일 업로드 모달창 -------------------*/
function upload_modal_open() {
	file_modal_div.style.display = 'block';
}

function upload_modal_close() {
	file_modal_div.style.display = 'none';
}


/* --------------- 카테고리 등록 모달창 -------------------*/
function insert_category_open() {
	modal_insert.style.display = 'block';
}

function insert_category_close() {
	modal_insert.style.display = 'none';
}

/* --------------- 카테고리 삭제 모달창 -------------------*/
function delete_category_open() {
	modal_delete.style.display = 'block';
}

function delete_category_close() {
	modal_delete.style.display = 'none';
}

/* --------------- 카테고리 생성 -------------------*/
function insert_cateogry() {
	let large_category_input = document.querySelector('.large_category_input')
	let small_category_input = document.querySelector('.small_category_input')
	let category_choice = document.querySelector('#category_choice')
	let category_top
	let category_input

	if (order == 1) {
		category_top = 0
		category_input = large_category_input
	}
	if (order == 2) {
		category_top = category_choice.options[category_choice.selectedIndex].value
		category_input = small_category_input
	}

	let param = {
		category_nm: category_input.value,
		category_order: order,
		category_top: category_top
	}

	fetch(`/createCategory`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(param)
	}).then(function(res) {
		return res.json()
	}).then(function(data) {
		switch (data.category) {
			case 1:
				alert('카테고리가 등록되었습니다.')
				modal_insert.style.display = 'none';
				location.reload()
				break;
			case 2:
				alert('이미 존재하는 카테고리입니다.')
				break;
			case 3:
				alert('이름을 입력해주세요.')
		}
	})
}

/* --------------- 카테고리 삭제 -------------------*/


/* --------------- 카테고리 분류 뿌리기 -------------------*/
function top_categoryChange(e) {
	let sub_category_top = document.querySelectorAll('.sub_category_top')
	let sub_category_nm = document.querySelectorAll('.sub_category_nm')
	let sub_category_i_category = document.querySelectorAll('.sub_category_i_category')
	let small_category_choice = document.querySelector('#small_category_choice')
	console.log(small_category_choice.length)
	small_category_choice.length = 0;

	for (i = 0; i < sub_category_nm.length; i++) {
		let opt = document.createElement("option");
		if (e.value == sub_category_top.item(i).value) {
			opt.value = sub_category_i_category.item(i).value
			opt.innerHTML = sub_category_nm.item(i).value
			small_category_choice.append(opt);
		}
	}
}

/* --------------- 카테고리 삭제 -------------------*/
function delete_category() {
	let small_category_choice = document.querySelector('#small_category_choice')
	let large_category_choice = document.querySelector('#large_category_choice')
	let id
	console.log(large_category_choice.options[large_category_choice.selectedIndex].value)
	if (order == 1) {
		id = large_category_choice.options[large_category_choice.selectedIndex].value

		fetch('/deleteLargeCategory/' + id, {
			method: 'DELETE',
		}).then(function(res) {
			res.text().then(function(text) {
				console.log(text)
				if (text == 1) {
					alert('카테고리가 삭제되었습니다.')
					location.reload()
				} else if (text == 2) {
					alert('카테고리를 선택해주세요.')
				}
			})
		})
	}
	if (order == 2) {
		console.log(small_category_choice.options.length)
		console.log(small_category_choice.length)
		if (small_category_choice.length == 0) {
			alert('소분류가 존재하지 않습니다.')
			return
		}

		id = small_category_choice.options[small_category_choice.selectedIndex].value

		fetch('/deleteSmallCategory/' + id, {
			method: 'DELETE',
		}).then(function(res) {
			res.text().then(function(text) {
				console.log(text)
				if (text == 1) {
					alert('카테고리가 삭제되었습니다.')
					location.reload()
				} else if (text == 2) {
					alert('카테고리를 선택해주세요.')
				}
			})
		})
	}
}

function enterkey() {
	if (window.event.keyCode == 13) {
		searchForm()
	}
}

function searchForm() {
	let search_select = document.querySelector('.search_select')
	let search_input = document.querySelector('#search_input').value
	search_select = search_select.options[search_select.selectedIndex].value

	if (search_input == '') {
		alert('내용을 입력해주세요')
		return
	}

	location.href = `/search/` + search_select + `/` + search_input
}

function movePage(pageNumber) {
	let searchContent = document.querySelector('#searchContent').value

	if (currentPath == "mainCategory") {
		location.href = `/?page=` + pageNumber
	} else if (currentPath == "entireCategory") {
		location.href = `/category?page=` + pageNumber
	} else if (currentPath == "largeCategory") {
		location.href = `/category/` + largeCategory + `?page=` + pageNumber
	} else if (currentPath == "smallCategory") {
		location.href = `/category/` + largeCateory + `/` + smallCategory + `?page=` + pageNumber
	} else if (currentPath == "searchTitle" || currentPath == "searchCategory") {
		location.href = `/search/` + currentPath + `/` + searchContent + `?page=` + pageNumber
	}
}

