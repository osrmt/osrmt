package database;

/**
 *
 * Access class deals with a subset of data types - if any are used
 * that are not handled then this exception is called.
 */
public class AccessDataTypeException extends Exception {
	
	static final long serialVersionUID = 1L;
	
	public AccessDataTypeException(String exception){
		super(exception);
	}
}
