package transmitter;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-ÁùÔÂ-2012 10:40:21
 */
public interface TextMessageTransmitter {

	/**
	 * 
	 * @param receiver
	 * @param message
	 */
	public void sendTextMessage(String receiver, String message);

}