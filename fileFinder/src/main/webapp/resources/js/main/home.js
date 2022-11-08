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

function goHome() {
	location.href=`/`
}

/* --------------- 대/소분류 구분 -------------------*/

large_category.onclick = function() {
	order = 1
	large_category_div.style.display = 'block'
	small_category_div.style.display = 'none'
}
small_category.onclick = function() {
	order = 2
	large_category_div.style.display = 'none'
	small_category_div.style.display = 'block'
}

large_delete_category.onclick = function() {
	order = 1
	large_delete_category_div.style.display = 'block'
	small_delete_category_div.style.display = 'none'
}
small_delete_category.onclick = function() {
	order = 2
	large_delete_category_div.style.display = 'none'
	small_delete_category_div.style.display = 'block'
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

