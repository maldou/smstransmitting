package transmitter;

import serial.SerialPortOperation;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-ÁùÔÂ-2012 10:40:20
 */
public class CDMAMessageTransmitFactory extends MessageTransmitFactory {

	public CDMAMessageTransmitFactory(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public BinaryMessageTransmitter createBinaryMessageTransmitter(SerialPortOperation serialOper){
		return new CDMABinaryMessageTransmitter(serialOper);
	}

	public TextMessageTransmitter createTextMessageTransmitter(SerialPortOperation serialOper){
		return new CDMATextMessageTransmitter(serialOper);
	}

}