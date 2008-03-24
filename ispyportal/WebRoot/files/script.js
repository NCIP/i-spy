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
//debugger;
    window.open( _html, _name, "height=500,width=800");

}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
/*
function return_file( _select, _field ){
//alert(_select);
//alert(_field);
	window.opener.document.getElementById( _field ).value = _selectedValue;

	window.close();

	return false;

}
*/
function return_file( _select, _field ){
//debugger;
       var _options = document.getElementById( _select ).options;    
       for( var i = 0; i < _options.length; i++ ){            
              if( _options[ i ].selected ){            
//                     window.opener.document.getElementById( _field ).value = _selectedValue;
                     window.opener.document.getElementById( _field ).value = _options[i].value;
                     window.close();
                     break;
              }      
       }
       return false;
}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */