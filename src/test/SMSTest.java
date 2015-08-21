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
		//��ʼ������
		serialPortOper.startSerial();
		
		MessageTransmitFactory smsTransFactory = MessageTransmitFactory.getInstance("GPRS");
		//���ö��ŷ����жϷ�����
		BinaryMessageTransmitter  binSMSTransmitter = smsTransFactory.createBinaryMessageTransmitter(serialPortOper);
		TextMessageTransmitter txtSMSTransmitter = smsTransFactory.createTextMessageTransmitter(serialPortOper);
		
		SMSManager manager = new SMSManager();
		manager.registBinaryMsgTransmitter(binSMSTransmitter);
		manager.registTextMessageTransmitter(txtSMSTransmitter);
		manager.setMessageType(ShortMessageType.BINARYMESSAGE);
		
		//���Է��Ͷ���
		String receiver = "15910354739";
		String message = "��·��";
		manager.sendMessage(receiver, message);
		
		serialPortOper.stopSerail();
	}

}
