package transmitter;

import serial.SerialPortOperation;
import util.PropertyManager;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-六月-2012 10:40:20
 */
public class GPRSBinaryMessageTransmitter implements BinaryMessageTransmitter {

	private SerialPortOperation serialPortOper;
	
	// 国家编码，默认条件下为86（中国）
	private String strMCC = PropertyManager.getProperty("MCC");;
	
	public GPRSBinaryMessageTransmitter(SerialPortOperation serialOper){
		this.serialPortOper = serialOper;
	}


	/**
	 * 
	 * @param receiver
	 * @param message
	 */
	public void sendBinaryMessage(String receiver, String message){
		String strHeader = getHeadPort(receiver);
		String strUD = getUDPort(message);

		int msg_length = (strHeader.length() + strUD.length()) / 2 - 1;

		serialPortOper.writeToPort("AT+CMGF=0\r");
		serialPortOper.writeToPort("AT+CMGS=" + msg_length + "\r");

		String strTotalMsg = strHeader + strUD;

		serialPortOper.writeToPort(strTotalMsg);

		char c = 0x1a;
		serialPortOper.writeToPort(c);
	}
	
	/**
	 * 构造PDU消息头，当按PDU方式发送短消息时TP-DCS一律设为08, 即按16-bit编码方式发送消息，最大消息长度为70个字符
	 * 
	 * @param msg
	 * @return
	 */
	private String getHeadPort(String dstMS) {
		String result = "001100" + getMSNumberPort(dstMS) + "000801";

		return result;
	}
	
	/**
	 * 组织消息部分
	 */
	public String getUDPort(String msg) {
		String result = "";
		try {
			String msg_t = byte2HexString(msg.getBytes("UTF-16BE"));
			int msg_l = msg_t.length() / 2;
			result = constructLength(msg_l) + msg_t;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据国家码和目的手机号码构造消息头
	 * 
	 * @param ms
	 * @return
	 */
	private String getMSNumberPort(String ms) {
		String result = "";

		// 检查目标号码中是否包含国家码
		if (!ms.startsWith(strMCC))
			ms = strMCC + ms;

		int length = ms.length();
		for (int i = 0; i < length - 1; i = i + 2) {
			result += ms.charAt(i + 1);
			result += ms.charAt(i);
		}
		// 如果号码长度为奇数，补充末位数字
		if (length % 2 == 1) {
			result += "F" + ms.charAt(length - 1);
		}

		String strLen = constructLength(length);

		result = strLen + "91" + result;

		return result;
	}
	
	private String constructLength(int length) {
		String strL = Long.toHexString(length);
		if (strL.length() == 1) {
			strL = "0" + strL;
		} else if (strL.length() > 2)
			strL = strL.substring(strL.length() - 2);

		return strL;
	}
	
	/**
	 * 将byte[]格式的文本信息转为16进制的文本格式
	 * 
	 * @param b
	 * @return
	 */
	public String byte2HexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			long temp = (long) b[i];
			String s = Long.toHexString(temp);
			if (s.length() == 1)
				s = "0" + s;
			else if (s.length() > 2)
				s = s.substring(s.length() - 2);

			result += s;

		}

		return result;
	}

}