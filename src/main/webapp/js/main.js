function hidepanels(id, fun) {
	/*jQuery('#newform')[0].reset();
	jQuery('#editform')[0].reset();*/
	
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
			jQuery('span.search-item_'+id).remove();				
			searches();
		}
		jQuery('#debug-content').html(data);
	}, 'text');
}
function deleteTag(id) {
	jQuery.get('delete-tag.html?id='+id, function(data){
		if(data == '0') {
			jQuery('span.tag-item_'+id).remove();				
			toptags();
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
	jQuery('#edit').load('edit.html', {'id': id}, function(data){
		if(jQuery('#edit').css('display') != 'none') {
			jQuery('.panel').each(function(index) {
				jQuery(this).hide('fast', function(){
					jQuery('#edit').show('fast');
				});
			});
		} else {
			hidepanels('edit', function() {jQuery('#address2').focus();});
		}
		autocomplete();
	});
}
function register() {
	jQuery('#register').load('register.html', function(data){
		hidepanels('register', function() {jQuery('#userlogin').focus();});
	});
}
function login() {
	jQuery('#login').load('login.html', function(data){
		hidepanels('login', function() {jQuery('#username').focus();});
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
function visibility(id) {
	jQuery('.ajax_'+id).show();
	jQuery.post('visibility.html', {'id': id}, function(data) {
		jQuery('.ajax_'+id).hide();
		var item = jQuery('.link-item_'+id);
		var t = 'make public';
		if(data == '1') {
			t = 'make private';
		}
		if(data == '1' || data == '0') {
			jQuery(item).find('a.visibility .button-text').html(t);
		} else {
			alert(data);
		}
	});
	
}
function mode(mode) {
	jQuery.get('settings.html', {'mode': mode}, function(data){
	});
}
function toplinks() {
	jQuery('#toplinks').load('toplinks.html');
}
function searches() {
	jQuery('#searches').load('searches.html');
}	
function toptags() {
	jQuery('#toptags').load('toptags.html');
}
function split( val ) {
	return val.split( /,\s*/ );
}
function extractLast( term ) {
	return split( term ).pop();
}
jQuery(document).ready(function(){
	toplinks();
	searches();
	toptags();
	
	$("input.enter").keydown(function(event) {
		if(event.keyCode == 13) {
			$(this).closest('form').submit();
		}
	});
	autocomplete();
});
function autocomplete() {
	jQuery.ajax({
		url: 'alltags.html',
		success: function(data) {
			
			jQuery('.tags_input').each(function(index){
				jQuery(this)
				.bind( 'keydown', function( event ) {
					if ( event.keyCode === 13 && $(this).data('autocomplete').menu.active) {
						event.preventDefault();
					}
					if ( event.keyCode === $.ui.keyCode.TAB && $(this).data('autocomplete').menu.active ) {
						event.preventDefault();
					}
				})			
				.autocomplete({
					source: function(request, response) {
						response($.ui.autocomplete.filter(data, extractLast(request.term)));
						/*
					      jQuery.getJSON('alltags.html?query='+request.term, function(data) {
			                    response(data);
			              });
			            */
					},
					focus: function() {
						// prevent value inserted on focus
						return false;
					},
					select: function( event, ui ) {
						var terms = split( this.value );
						// remove the current input
						terms.pop();
						// add the selected item
						terms.push( ui.item.value );
						// add placeholder to get the comma-and-space at the end
						terms.push( "" );
						this.value = terms.join( ", " );
						return false;
					}				
				});				
			});
		},
		dataType: 'json'
	});	
}