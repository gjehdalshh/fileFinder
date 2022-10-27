let category_modal_open_btn = document.querySelector('#category_modal_open_btn')
let category_modal_close_btn = document.querySelector('#category_modal_close_btn')
let category_insert = document.querySelector('#category_insert')
let modal = document.querySelector('#modal')
let large_category = document.querySelector('#large_category')
let small_category = document.querySelector('#small_category')

let order = 1;

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

/* --------------- 모달창 -------------------*/
category_modal_open_btn.onclick = function() {
	modal.style.display = 'block';
}

category_modal_close_btn.onclick = function() {
	modal.style.display = 'none';
}


/* --------------- 카테고리 생성 -------------------*/
category_insert.onclick = function() {
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
				modal.style.display = 'none';
				location.reload()
				break;
			case 2:
				alert('이미 존재하는 카테고리입니다.')
				break;
		}
	})
}


