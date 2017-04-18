var eventSource = new EventSource("/dashboardAdmin/updates");
		
eventSource.addEventListener("newSuggestion", function(event) {
	var obj = JSON.parse(event.data);
  	var row = document.getElementById("sugerencias").insertRow(-1);
	row.insertCell(0).outerHTML = `<th><a href="${obj.suggestion}">${obj.suggestion}</a></th>`;
	row.insertCell(1).innerHTML = obj.name;
	row.insertCell(2).innerHTML = 0;
	row.insertCell(3).innerHTML = 0;
});

eventSource.addEventListener("alertSuggestion", function(event) {
	var obj = JSON.parse(event.data);
	
	$('a').filter(function() {
		return $(this).text() === obj.suggestion;
	}).css("color", "red");
	
	$("#alerta").modal();
});
    	
eventSource.addEventListener("voteSuggestion", function(event) {
	var obj = JSON.parse(event.data);
  	var value = parseInt($('a').filter(function() {
					return $(this).text() === obj.suggestion;
				}).closest('tr').children('td').eq(1).text());

	$('a').filter(function() {
		return $(this).text() === obj.suggestion;
	}).closest('tr').children('td').eq(1).html(value+1);
});

eventSource.addEventListener("newComment", function(event) {
	var obj = JSON.parse(event.data);
  	var value = parseInt($('a').filter(function() {
					return $(this).text() === obj.suggestion;
				}).closest('tr').children('td').eq(2).text());

	$('a').filter(function() {
		return $(this).text() === obj.suggestion;
	}).closest('tr').children('td').eq(2).html(value+1);
});