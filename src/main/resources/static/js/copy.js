"use strict";

var middle = document.getElementById("middleCategory");
var small = document.getElementById("smallCategory");
//middle.prop('disabled',true);
//small.prop('disabled',true);
//middle.setAttribute("disabled",true);
//small.setAttribute("disabled",true);

window.onload = function() {

}

$('#bigcategory').change(function() {
	var bigCategory = $('#bigcategory').val();
	$.ajax({
		url: 'http://localhost:8080/item/middle',
		type: 'post',
		dataType: 'json',
		data :{
			bigCategory : bigCategory
		},
		async: true,
	}).done(function(middleList){
		//selectに当てはめる
		let $middle = $("#middleCategory");
		let $small = $("#smallCategory");
		$middle.empty();
		$small.empty();
		$middle.append($("<option>-- 中分類　--</option>"));
		$small.append($("<option>-- 中分類を選択してください　--</option>"));
		$.each(middleList,function(key,value){
			$middle.append($("<option></option>")
				.attr("value", value.id).text(value.name));
		})
		$("#middleCategory").append(middleList);
		console.log(middleList);
		
	}).fail(function(XMLHttpRequest,textStatus,errorThrown){
		alert('値がないんやけどnnnn。');
	});


});

