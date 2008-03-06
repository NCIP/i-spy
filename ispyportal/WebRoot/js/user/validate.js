/* * * * * * * * * * * * * * * * * * * * * * * * * * *
 
 	JS Form Validation
	
		validates the keys specified in the _validate array by the 
		option set as the value.
 
 		add to form:
		
			onSubmit="return validate_form( this )"
 
		format:

			_validate[ <input_tag_name> ] = <validation_option>;
			
			e.g. _validate[ 'input_email' ] = 'email';
 
		options:
			
			date	- matches format, mm/dd/yyyy
			email	- valid email address
			num		- numbers only
			text	- text only, no nums or symbols
			empty	- checks if field is empty
			match	- checks if field matches other match ( e.g. _validate[ 'password' ] = 'match:password_confirm'; )

 * * * * * * * * * * * * * * * * * * * * * * * * * * */

var _validate = [];

/* * * * * * * * * * * * * * * * * * * * * * * * * * */

function clear_errors(){

	for( var _f1 in _validate ){
	
		$( 'input[@name=' + _f1 + ']' ).siblings( 'span.error' ).remove();
	
	}
	
}

function mark_errors( _errs ){
	
	for( var _f2 in _errs ){
		
		$( 'input[@name=' + _f2 + ']' ).parent().append( '<span class="error">'+_errs[ _f2 ]+'</span>' );
		
	}
	
}


/*function isBlanks(s){
	    var i;
        for (i = 0; i < s.length; i++){   
          // Check that current character is number.
          var c = s.charAt(i);
          if (c != " ") 
           return false; 
        }
        // All characters are numbers.
        return true;
    }*/
    

/* * * * * * * * * * * * * * * * * * * * * * * * * * */

function validate_form( _form ){

	clear_errors();

	var _formErrs = [];

	for( var _field in _validate ){

		var _f = 'input[@name=' + _field + ']';
		
		if( typeof(_validate[ _field ]) == 'string' && _validate[ _field ].match( /match/ ) ){
			
			var _m = _validate[ _field ].split( ':' );
			
			if( $( _f ).val() != $('input[@name=' + _m[ 1 ] + ']').val() ){
				_formErrs[_field] = $( _f ).attr('name')+' field and '+_m[ 1 ]+' field do not match';
			}
			
			if( $(_f).val() == '' ){
				_formErrs[$( _f ).attr('name')] = 'Field can not be empty';
			}
				
		} else {

			switch( _validate[ _field ] ){
				
				case 'date' :
					if( !$(_f).val().match( /(\d{1,2}\/\d{1,2}\/\d{4})/ ) ){
						_formErrs[_field] = 'Dates must in MM\/DD\/YYYY format';
					}
					if( $(_f).val() == '' ){
						_formErrs[_field] = 'Field can not be empty';
					}			
				break;
				
				case 'email' :
					if( !$(_f).val().match( /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/ ) ){
						_formErrs[_field] = 'Field must be a valid email';
					}			
					if( $(_f).val() == '' ){
						_formErrs[_field] = 'Field can not be empty';
					}
				break;
				
				case 'num' :
					if( $(_f).val().match( /[^0-9]/ ) ){
						_formErrs[_field] = 'Field can contain only numbers';
					}
					if( $(_f).val() == '' ){
						_formErrs[_field] = 'Field can not be empty';
					}			
				break;
				
				case 'text' :
					if( $(_f).val().match( /[^A-z]/ ) ){
						_formErrs[_field] = 'Field can contain only characters';
					}
					if( $(_f).val() == '' ){
						_formErrs[_field] = 'Field can not be empty';
					}			
				break;
						
				default :
					if( $(_f).val() == '' ){
						_formErrs[ _field ] = 'Field can not be empty';
					}
				break;
			
			}
	
		}
	
	}
		
	if( _formErrs.keyCount() < 1 ){
				
		return true;
		
	} else {
		
		mark_errors( _formErrs );
		
		return false;
		
	}

}

/* * * * * * * * * * * * * * * * * * * * * * * * * * */

/**
* Function : dump()
* Arguments: The data - array,hash(associative array),object
*    The level - OPTIONAL
* Returns  : The textual representation of the array.
* This function was inspired by the print_r function of PHP.
* This will accept some data as the argument and return a
* text that will be a more readable version of the
* array/hash/object that is given.
*/
function dump(arr,level) {
var dumped_text = "";
if(!level) level = 0;

//The padding given at the beginning of the line.
var level_padding = "";
for(var j=0;j<level+1;j++) level_padding += "    ";

if(typeof(arr) == 'object') { //Array/Hashes/Objects
 for(var item in arr) {
  var value = arr[item];
 
  if(typeof(value) == 'object') { //If it is an array,
   dumped_text += level_padding + "'" + item + "' ...\n";
   dumped_text += dump(value,level+1);
  } else {
   dumped_text += level_padding + "'" + item + "' => \"" + value + "\"\n";
  }
 }
} else { //Stings/Chars/Numbers etc.
 dumped_text = "===>"+arr+"<===("+typeof(arr)+")";
}
return dumped_text;
} 
/* * * * * * * * * * * * * * * * * * * * * * * * * * */