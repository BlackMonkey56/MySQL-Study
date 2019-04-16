package jdbc.broker.exception;
/*
 * RecordNotFoundException
 * invalidTransactionException
 */
public class InvalidTransactionException extends Exception{
	public InvalidTransactionException(String message){
		super(message);
	}
	public InvalidTransactionException(){
		this("This is InvalidTransactionException... ");
	}
}
