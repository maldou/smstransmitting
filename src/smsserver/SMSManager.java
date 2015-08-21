package smsserver;

import transmitter.BinaryMessageTransmitter;
import transmitter.TextMessageTransmitter;
import util.ShortMessageType;

public class SMSManager {

	private BinaryMessageTransmitter binaryMessageTransmitter;
	private ShortMessageType messageType;
	private TextMessageTransmitter textMessageTransmitter;

	public SMSManager(){

	}

	/**
	 * 
	 * @param binMsgTransmitter
	 */
	public void registBinaryMsgTransmitter(BinaryMessageTransmitter binMsgTransmitter){
		this.binaryMessageTransmitter = binMsgTransmitter;
	}

	/**
	 * 
	 * @param txtMsgTransmitter
	 */
	public void registTextMessageTransmitter(TextMessageTransmitter txtMsgTransmitter){
		this.textMessageTransmitter = txtMsgTransmitter;
	}

	/**
	 * 
	 * @param receiver
	 * @param message
	 */
	public void sendMessage(String receiver, String message){
		if(messageType == ShortMessageType.TEXTMESSAGE) {
			if(textMessageTransmitter != null)
				textMessageTransmitter.sendTextMessage(receiver, message);
		}
		else if(messageType == ShortMessageType.BINARYMESSAGE) {
			if(binaryMessageTransmitter != null)
				binaryMessageTransmitter.sendBinaryMessage(receiver, message);
		}
	}

	/**
	 * 
	 * @param type
	 */
	public void setMessageType(ShortMessageType type){
		this.messageType = type;
	}

}