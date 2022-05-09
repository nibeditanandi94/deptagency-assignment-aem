$(document).ready(function() {
	console.log("Movie JS loaded");
});

$('#movieShow').click(function() {
	console.log("Please show the movie list");
	$.ajax( {
		type : "GET",
		url: "/bin/moviesIMDB",
		dataType : "json",
		data : {
			param : 'movies'
		},
		success: function (data) {
			console.log("SUCCESS", data);
			data = jQuery.parseJSON(data);
			var list = data.list;
			console.log("List--->"+list);
			console.log("new data--->"+data);
			var html_to_append = '';
			$.each(list, function(index, element){
				console.log("Inside for each loop");
				 html_to_append +=
					    '<div class="mainContainer">' +
			            '<div class="listTitle"><p>' +
			            element.title + '</p></div>'+ 
			            '<div class="listTitleID"><p>' +
			            element.titleId + '</p></div>'
			            '<div class="listUrl"><p>' +
			            element.url + '</p></div>' +
			            '</div>';
				 $("#items-container").html(html_to_append);
			});
			
			$("#movieShow").hide();
			$(".successMsg").append("Movie list will be shown shortly");
		},
		error: function (e) {
			console.log("ERROR",e);
		}
	} );
	
});
