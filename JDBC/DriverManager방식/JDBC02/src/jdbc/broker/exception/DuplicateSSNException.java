package jdbc.broker.exception;
/*
 * RecordNotFoundException
 * invalidTransactionException
 */
public class DuplicateSSNException extends Exception{
	public DuplicateSSNException(String message){
		super(message);
	}
	public DuplicateSSNException(){
		this("This is DuplicateSSNException... ");
	}
}
