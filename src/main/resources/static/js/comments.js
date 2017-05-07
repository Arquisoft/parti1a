var eventSource = new EventSource("/dashboard/updates");

eventSource.addEventListener("deleteSuggestion", function(event) {
	var obj = JSON.parse(event.data);
	if($('#ident').text() === obj.suggestion) {
		$("#alerta").modal();
	}
});
		
eventSource.addEventListener("newComment", function(event) {
	var obj = JSON.parse(event.data);
	if($('#ident').text() === obj.suggestion) {
		var row = document.getElementById("comentarios").insertRow(-1);
		row.insertCell(0).outerHTML = `<th>${obj.comment}</th>`;
		row.insertCell(1).innerHTML = 0;
		row.insertCell(2).innerHTML = 0;
	}
});

eventSource.addEventListener("positiveComment", function(event) {
	var obj = JSON.parse(event.data);
	if($('#ident').text() === obj.suggestion) {
	  	var value = parseInt($('th').filter(function() {
						return $(this).text() === obj.comment;
					}).closest('tr').children('td').eq(0).text());
	
		$('th').filter(function() {
			return $(this).text() === obj.comment;
		}).closest('tr').children('td').eq(0).html(value+1);
	}
});
    	
eventSource.addEventListener("negativeComment", function(event) {
	var obj = JSON.parse(event.data);
	if($('#ident').text() === obj.suggestion) {
	  	var value = parseInt($('th').filter(function() {
						return $(this).text() === obj.comment;
					}).closest('tr').children('td').eq(1).text());
	
		$('th').filter(function() {
			return $(this).text() === obj.comment;
		}).closest('tr').children('td').eq(1).html(value+1);
	}
});