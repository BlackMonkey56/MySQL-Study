package jdbc.broker.exception;
/*
 * RecordNotFoundException
 * TransactionInvalidException
 */
public class RecordNotFoundException extends Exception{
	public RecordNotFoundException(String message){
		super(message);
	}
	public RecordNotFoundException(){
		this("This is RecordNotFoundException... ");
	}
}
