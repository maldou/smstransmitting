package test;

import serial.SerialPortOperation;
import smsserver.SMSManager;
import transmitter.BinaryMessageTransmitter;
import transmitter.MessageTransmitFactory;
import transmitter.TextMessageTransmitter;
import util.PropertyManager;
import util.ShortMessageType;

public class SMSTest {
	
	public static void main(String[] args) {
		
		SerialPortOperation serialPortOper = new SerialPortOperation(PropertyManager.getProperty("COMM"));
		//初始化串口
		serialPortOper.startSerial();
		
		MessageTransmitFactory smsTransFactory = MessageTransmitFactory.getInstance("GPRS");
		//设置短信发送中断发送器
		BinaryMessageTransmitter  binSMSTransmitter = smsTransFactory.createBinaryMessageTransmitter(serialPortOper);
		TextMessageTransmitter txtSMSTransmitter = smsTransFactory.createTextMessageTransmitter(serialPortOper);
		
		SMSManager manager = new SMSManager();
		manager.registBinaryMsgTransmitter(binSMSTransmitter);
		manager.registTextMessageTransmitter(txtSMSTransmitter);
		manager.setMessageType(ShortMessageType.BINARYMESSAGE);
		
		//测试发送短信
		String receiver = "15910354739";
		String message = "在路上";
		manager.sendMessage(receiver, message);
		
		serialPortOper.stopSerail();
	}

}
