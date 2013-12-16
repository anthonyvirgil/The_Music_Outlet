var timer;
$(document).ready(function() {
	
	$('#leftOfLogoDiv').hide().slideDown(1500).delay(2000).slideUp(1500);
	
	$("input[type='text']").focus(function() {
		$(this).css("background-color", "#eeeeee");
	});
	$("input[type='text']").blur(function() {
		$(this).css("background-color", "#ffffff");
	});
	$("input[type='password']").focus(function() {
		$(this).css("background-color", "#eeeeee");
	});
	$("input[type='password']").blur(function() {
		$(this).css("background-color", "#ffffff");
	});
	$('#news').rssfeed($('#rss').val(), {
		date : false,
		header : false,
		limit : 3
	});
});
