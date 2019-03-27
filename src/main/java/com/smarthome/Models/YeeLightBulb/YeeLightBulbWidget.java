package com.smarthome.Models.YeeLightBulb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import com.smarthome.Constants.DiscoverResponseType;
import com.smarthome.Models.BaseWidget;

public class YeeLightBulbWidget extends BaseWidget {

    public static final String multicastChannel = "239.255.255.250";
    public static final int port = 1982;
    public static final int timeout = 15000;
    private byte[] receiveData = new byte[1024];

    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    
	private YeeLightDevice yeeLightBulb;
	
	public YeeLightBulbWidget() {
		yeeLightBulb = new YeeLightDevice();
		DiscoverDeviceOnNetwork();

	}
	
	public YeeLightDevice getYeeLightBulb() {
		return yeeLightBulb;
	}

	public void setYeeLightBulb(YeeLightDevice yeeLightBulb) {
		this.yeeLightBulb = yeeLightBulb;
		
	}
	
	private void DiscoverDeviceOnNetwork() {
		
        byte[] sendData = ("M-SEARCH * HTTP/1.1\r\n" +
                "HOST: 239.255.255.250:1982\r\n" +
                "MAN: \"ssdp:discover\"\r\n" +
                "ST: wifi_bulb").getBytes();
        
        try {

            DatagramPacket receivePacket = null;
            while (receivePacket == null) {
                		
            		 try {
            	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
            	            		InetAddress.getByName(multicastChannel), port);

            	            DatagramSocket datagramSocket = new DatagramSocket();
            	            datagramSocket.setSoTimeout(timeout);
            	            datagramSocket.send(sendPacket);

            	            receivePacket = new DatagramPacket(receiveData, receiveData.length);

            	            datagramSocket.receive(receivePacket);
            	            System.out.println("Socket receive");

            	        } catch (Exception e) {

            	            System.out.println(e.getMessage());
            	        }		
                		
                		
               
                Thread.sleep(50);// wait 5 sec
            }
            PrepareDeviceByReceivedPacket(receivePacket);
            System.out.println("Device Added");

        } catch (Exception ex) {

        }

	}
	
	private void PrepareDeviceByReceivedPacket(DatagramPacket receivePacket) {

	        Scanner sc = new Scanner(new String(receivePacket.getData()));

	        HashMap<String, String> responseData = new HashMap<>();
	        String dummyLine = sc.nextLine();
	        String[] dummies;
	        responseData.put("HTTPStatus", dummyLine);

	        while (sc.hasNext()) {
	            dummyLine = sc.nextLine();
	            dummies = dummyLine.split(":", 2);
	            if (dummyLine.contains(":"))
	                responseData.put(dummies[0], dummies[1]);
	        }
	        yeeLightBulb.setIp(receivePacket.getAddress().toString().substring(1));
	        String location =responseData.get(DiscoverResponseType.LOCATION);
	        yeeLightBulb.setPort(Integer.parseInt(location.substring(location.lastIndexOf(":")+1)));
	        yeeLightBulb.setName(responseData.get(DiscoverResponseType.NAME));
	        yeeLightBulb.setId(responseData.get(DiscoverResponseType.ID));
	    }
	
    public boolean setTcpSetting() {
        try {
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            clientSocket = new Socket(yeeLightBulb.getIp(), yeeLightBulb.getPort());
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public void toogleBulb(){
        String data = "NONE";
        try {
            outToServer.writeBytes("{\"id\":0,\"method\":" + "\"toggle\",\"params\":[\"" + "" + "\", \"" + "" + "\", "  + "]" + "}" + "\r\n");
            data = inFromServer.readLine();
            System.out.println(data);
            //clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       // return data;

    }
}
