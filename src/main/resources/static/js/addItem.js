"use strict";

var middle = document.getElementById("middleCategory");
var small = document.getElementById("smallCategory");
//middle.prop('disabled',true);
//small.prop('disabled',true);
//middle.setAttribute("disabled",true);
//small.setAttribute("disabled",true);

window.onload = function() {

}

$('#bigCategory').change(function() {
	var bigCategory = $('#bigCategory').val();
	console.log(bigCategory);
	$.ajax({
		url: 'http://localhost:8080/item/middle',
		type: 'post',
		dataType: 'json',
		data :{
			bigCategory : bigCategory
		},
		async: true,
	}).done(function(middleList){
			console.log(middleList);
		//selectに当てはめる
		let $middle = $("#middleCategory");
		let $small = $("#smallCategory");
		$middle.empty();
		$small.empty();
		$middle.append($("<option>-- 中分類　--</option>"));
		$small.append($("<option>-- 中分類を選択してください　--</option>"));
		$.each(middleList,function(key,value){
			$middle.append($("<option></option>")
				.attr("value", value.name).text(value.name));
		})
		$("#middleCategory").append(middleList);
		console.log(middleList);
		
	}).fail(function(XMLHttpRequest,textStatus,errorThrown){
		alert('値がないんやけど。');
	});


});

$('#middleCategory').change(function(){
	let middleCategory = $('#middleCategory').val();
	console.log(middleCategory);
	$.ajax({
		url: 'http://localhost:8080/item/small',
		type: 'post',
		dataType: 'json',
		data:{
			middleCategory : middleCategory
		},
		async: true,
	}).done(function(smallList){
		let $small = $("#smallCategory");
		$small.empty();
		small.append($("<option>-- 小分類 --</option>"));
		$.each(smallList,function(key,value){
			$small.append($("<option></option>")
			.attr("value",value.name).text(value.name));
		})
		$("#smallCategory").append(smallList);
		console.log(smallList);
	});
});