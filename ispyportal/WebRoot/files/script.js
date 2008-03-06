/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

$(document).ready( function(){

	$('.file_browser').click( function(){
	
		file_browser( $(this).attr('title') );
	
	});

});

* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

function file_browser( _html, _name ){
//alert(_name);
//	window.open( _name, 'height=220,width=500' );
    window.open( _html, _name, "height=220,width=500");

}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

function return_file( _select, _field ){
//alert(_select);
//alert(_field);
	window.opener.document.getElementById( _field ).value = _selectedValue;

	window.close();

	return false;

}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */