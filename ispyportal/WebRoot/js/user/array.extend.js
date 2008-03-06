// extend JS array, helpful functions
	 
Array.prototype.inArray = function (value)
// Returns true if the passed value is found in the
// array.  Returns false if it is not.
{
	for (var i=0; i < this.length; i++) {
		// Matches identical (===), not just similar (==).
		if (this[i] === value) {
			return true;
		}
	}
	return false;
};

Array.prototype.keyExists = function (value)
// Returns true if the passed value is one of the keys
// of the array. Returns false otherwise.
{
	for( var i in this ) {
		// Matches identical
		if( i == value ){
			return true;
		}
	}
	return false;
};

Array.prototype.keyCount = function ()
// Returns the number of entries in an array.
// Ignore the prototype functions because they
// are returned as well.
{
	var _count = 0;
	for( var i in this ) {
		if( typeof( this[ i ] ) != 'function' ){
			_count++;
		}
	}
	return _count;
};
