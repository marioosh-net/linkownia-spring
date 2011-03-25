function hidepanels(id, fun) {
	jQuery('.panel').each(function(index) {
		if(jQuery(this).attr('id') != id) {
			jQuery(this).hide('fast');
		}
	});
	jQuery('#'+id).toggle('fast', fun);
}
function deleteLink(id) {
	jQuery('.ajax_'+id).show();
	jQuery.get('delete.html?id='+id, function(data){
		jQuery('.ajax_'+id).hide();
		if(data == '0') {
			jQuery('div.link-item_'+id).remove();				
			toplinks();
		}
		jQuery('#debug-content').html(data);
	}, 'text');
}
function deleteSearch(id) {
	jQuery.get('delete-search.html?id='+id, function(data){
		if(data == '0') {
			jQuery('div.search-item_'+id).remove();				
			searches();
		}
		jQuery('#debug-content').html(data);
	}, 'text');
}	
function openLink(id, url) {
	jQuery.get('open.html?id='+id, function(data) {
		jQuery('div.count_'+id).html(data);
		toplinks();
	}, 'text');
	/*window.open(url , "open", "height=400,width=600");*/
	window.open(url);
}
function edit(id) {
	jQuery.post('edit.html', {'id': id}, function(data) {
		alert(data);
	});
}
/* odswiez linka, pociagnij name, description */
function refresh(id) {
	jQuery('.ajax_'+id).show();
	jQuery.post('refresh.html', {'id': id}, function(data) {
		jQuery('.ajax_'+id).hide();
		jQuery('#debug-content').html('NAME:'+data['name']+'<br/>DESC:'+data['description']);
		var item = jQuery('.link-item_'+id);
		jQuery(item).find('.descr').html(data['description']);
		if(jQuery.trim(data['name']) != '') {
			jQuery(item).find('.name').html(data['name']);
		}
	}, 'json');
}	
function toplinks() {
	jQuery('#toplinks').load('toplinks.html');
}
function searches() {
	jQuery('#searches').load('searches.html');
}	
jQuery(document).ready(function(){
	toplinks();
	searches();
});
