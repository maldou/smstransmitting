package transmitter;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-ÁùÔÂ-2012 10:40:19
 */
public interface BinaryMessageTransmitter {

	/**
	 * 
	 * @param receiver
	 * @param message
	 */
	public void sendBinaryMessage(String receiver, String message);

}