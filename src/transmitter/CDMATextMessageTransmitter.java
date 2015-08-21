package transmitter;

import serial.SerialPortOperation;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-ÁùÔÂ-2012 10:40:20
 */
public class CDMATextMessageTransmitter implements TextMessageTransmitter {

	private SerialPortOperation serialPortOper;
	
	public CDMATextMessageTransmitter(SerialPortOperation serialOper){
		this.serialPortOper = serialOper;
	}


	/**
	 * 
	 * @param receiver
	 * @param message
	 */
	public void sendTextMessage(String receiver, String message){
		serialPortOper.writeToPort("AT+wscl=1,2\r");
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
		
		serialPortOper.writeToPort(message+"\r");

		serialPortOper.writeToPort((char)0x00);
		serialPortOper.writeToPort((char)0x1a);
	}

}