package transmitter;

import java.io.UnsupportedEncodingException;

import serial.SerialPortOperation;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-ÁùÔÂ-2012 10:40:19
 */
public class CDMABinaryMessageTransmitter implements BinaryMessageTransmitter {
	
	private SerialPortOperation serialPortOper;

	public CDMABinaryMessageTransmitter(SerialPortOperation serailOper){
		this.serialPortOper = serailOper;
	}

	/**
	 * 
	 * @param receiver
	 * @param message
	 */
	public void sendBinaryMessage(String receiver, String message){
		serialPortOper.writeToPort("AT+wscl=6,4\r");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		serialPortOper.writeToPort("AT+cmgs=\"" + receiver + "\"\r");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			serialPortOper.writeToPort((message+"\r").getBytes("UTF-16BE"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		serialPortOper.writeToPort((char)0x00);
		serialPortOper.writeToPort((char)0x1a);
	}

}