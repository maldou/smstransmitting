package transmitter;

import serial.SerialPortOperation;
import util.PropertyManager;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-ÁùÔÂ-2012 10:40:20
 */
public abstract class MessageTransmitFactory {

	public MessageTransmitFactory(){

	}

	public abstract BinaryMessageTransmitter createBinaryMessageTransmitter(SerialPortOperation serailOper);

	public abstract TextMessageTransmitter createTextMessageTransmitter(SerialPortOperation serailOper);

	public static MessageTransmitFactory getInstance(String modelType){
		
		String className = "";
		String classNameProp = PropertyManager.getProperty(modelType);
        if (classNameProp != null) {
            className = classNameProp;
        }		
		try {
			Class c = Class.forName(className);
			MessageTransmitFactory factory = (MessageTransmitFactory)c.newInstance();
			return factory;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}