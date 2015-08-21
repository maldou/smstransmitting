package serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;


import util.PropertyManager;

public class SerialPortOperation {
	
	private SerialPort serialPort = null;
	private InputStream in;
	private OutputStream out;
	private String serialPortName;
	
	//private static SerialPortOperation instance;
	
	public SerialPortOperation(String serialPortName) {
		this.serialPortName = serialPortName;
	}
	
//	private static Object serialLock = new Object();
//	
//	public static SerialPortOperation getInstance(String serialPortName){
//		if(instance == null) {
//			synchronized(serialLock) {
//				if(instance == null) {
//					instance = new SerialPortOperation(serialPortName);
//				}
//			}
//		}
//		
//		return instance;
//	}


	public String readFromPort(){
		return "";
	}

	public void startSerial(){		
		String baudRate = PropertyManager.getProperty("BAUD_RATE");
		String dataBits = PropertyManager.getProperty("DATA_BITS");
		String parity = PropertyManager.getProperty("PARITY");
		String stopBits = PropertyManager.getProperty("STOP_BITS");
		
		startSerial( baudRate, dataBits, parity, stopBits);
	}
	
	public void startSerial(String baudRate, 
			                     String dataBits, String parity, String stopBits){		
		try {
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(serialPortName);

			try {
				serialPort = (SerialPort) portId.open(serialPortName, 3000);
			} catch (PortInUseException e) {
				e.printStackTrace();
				return ;
			}
			try {
				in = serialPort.getInputStream();
				out = serialPort.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			try {
				serialPort.setSerialPortParams(Integer.valueOf(baudRate), getDataBits(dataBits), getStopBits(stopBits), getParity(parity));
			} catch (UnsupportedCommOperationException e) {
				e.printStackTrace();
			}
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		}
	}


	public void stopSerail(){
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serialPort.close();
	}

	/**
	 * 
	 * @param input
	 */
	public void writeToPort(String input){
		try {
			for (int i = 0; i < input.length(); i++)
				out.write(input.charAt(i));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToPort(byte[] input){
		try {
			out.write(input);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	public void writeToPort(char input){
		try {
			out.write(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int getDataBits(String dataBits) {
		if(dataBits.equals("5")) 
			return SerialPort.DATABITS_5;
		else if(dataBits.equals("6")) 
			return SerialPort.DATABITS_6;
		else if(dataBits.equals("7")) 
			return SerialPort.DATABITS_7;
		
		return SerialPort.DATABITS_8;
	}
	
	private int getStopBits(String stopBits) {
		if(stopBits.equals("1")) 
			return SerialPort.STOPBITS_1;
		else if(stopBits.equals("2")) 
			return SerialPort.STOPBITS_2;
		else if(stopBits.equals("3")) 
			return SerialPort.STOPBITS_1_5;
		
		return SerialPort.STOPBITS_1;
	}
	
	private int getParity(String parity) {
		if(parity.equals("0")) 
			return SerialPort.PARITY_NONE;
		else if(parity.equals("1")) 
			return SerialPort.PARITY_ODD;
		else if(parity.equals("2")) 
			return SerialPort.PARITY_EVEN;
		else if(parity.equals("3")) 
			return SerialPort.PARITY_MARK;
		else if(parity.equals("4")) 
			return SerialPort.PARITY_SPACE;
		
		return SerialPort.PARITY_NONE;
	}

}