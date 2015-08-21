package transmitter;

import serial.SerialPortOperation;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-ÁùÔÂ-2012 10:40:20
 */
public class GPRSTextMessageTransmitter implements TextMessageTransmitter {
	
	private SerialPortOperation serialPortOper;

	public GPRSTextMessageTransmitter(SerialPortOperation serialOper){
		this.serialPortOper = serialOper;
	}

	/**
	 * 
	 * @param receiver
	 * @param message
	 */
	public void sendTextMessage(String receiver, String message){
		char c = 0x1a;
		serialPortOper.writeToPort("AT+CMGF=1\r");
		serialPortOper.writeToPort("AT+CMGS=" + receiver + "\r");
		serialPortOper.writeToPort(message);
		serialPortOper.writeToPort(c);

	}

}