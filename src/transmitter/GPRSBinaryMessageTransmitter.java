package transmitter;

import serial.SerialPortOperation;
import util.PropertyManager;

/**
 * @author wangm-2347
 * @version 1.0
 * @created 29-����-2012 10:40:20
 */
public class GPRSBinaryMessageTransmitter implements BinaryMessageTransmitter {

	private SerialPortOperation serialPortOper;
	
	// ���ұ��룬Ĭ��������Ϊ86���й���
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
	 * ����PDU��Ϣͷ������PDU��ʽ���Ͷ���ϢʱTP-DCSһ����Ϊ08, ����16-bit���뷽ʽ������Ϣ�������Ϣ����Ϊ70���ַ�
	 * 
	 * @param msg
	 * @return
	 */
	private String getHeadPort(String dstMS) {
		String result = "001100" + getMSNumberPort(dstMS) + "000801";

		return result;
	}
	
	/**
	 * ��֯��Ϣ����
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
	 * ���ݹ������Ŀ���ֻ����빹����Ϣͷ
	 * 
	 * @param ms
	 * @return
	 */
	private String getMSNumberPort(String ms) {
		String result = "";

		// ���Ŀ��������Ƿ����������
		if (!ms.startsWith(strMCC))
			ms = strMCC + ms;

		int length = ms.length();
		for (int i = 0; i < length - 1; i = i + 2) {
			result += ms.charAt(i + 1);
			result += ms.charAt(i);
		}
		// ������볤��Ϊ����������ĩλ����
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
	 * ��byte[]��ʽ���ı���ϢתΪ16���Ƶ��ı���ʽ
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