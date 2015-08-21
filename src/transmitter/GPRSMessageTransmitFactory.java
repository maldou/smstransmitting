package transmitter;

import serial.SerialPortOperation;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-ÁùÔÂ-2012 10:40:20
 */
public class GPRSMessageTransmitFactory extends MessageTransmitFactory {

	public GPRSMessageTransmitFactory(){

	}

	public BinaryMessageTransmitter createBinaryMessageTransmitter(SerialPortOperation serialOper){
		return new GPRSBinaryMessageTransmitter(serialOper);
	}

	public TextMessageTransmitter createTextMessageTransmitter(SerialPortOperation serialOper){
		return new GPRSTextMessageTransmitter(serialOper);
	}

}