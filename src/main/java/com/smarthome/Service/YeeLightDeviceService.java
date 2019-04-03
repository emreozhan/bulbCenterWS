package com.smarthome.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.smarthome.Constants.DiscoverResponseType;
import com.smarthome.Models.YeeLightBulb.YeeLightDevice;
@Component("yeelightService")
public class YeeLightDeviceService {

	public YeeLightDevice DiscoverDeviceOnNetwork(YeeLightDevice yeeLightDevice) {
		
        byte[] sendData = ("M-SEARCH * HTTP/1.1\r\n" +
                "HOST: 239.255.255.250:1982\r\n" +
                "MAN: \"ssdp:discover\"\r\n" +
                "ST: wifi_bulb").getBytes();
        
        try {

            DatagramPacket receivePacket = null;
            while (receivePacket == null) {
                		
            		 try {
            	            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
            	            		InetAddress.getByName(YeeLightDevice.multicastChannel), YeeLightDevice.port);

            	            DatagramSocket datagramSocket = new DatagramSocket();
            	            datagramSocket.setSoTimeout(YeeLightDevice.timeout);
            	            datagramSocket.send(sendPacket);

            	            receivePacket = new DatagramPacket(yeeLightDevice.receiveData, yeeLightDevice.receiveData.length);

            	            datagramSocket.receive(receivePacket);
            	            System.out.println("Socket receive");

            	        } catch (Exception e) {

            	            System.out.println(e.getMessage());
            	        }		
                		
                		
               
                Thread.sleep(50);// wait 5 sec
            }
            yeeLightDevice = PrepareDeviceByReceivedPacket(yeeLightDevice, receivePacket);
            System.out.println("Device Added");

        } catch (Exception ex) {

        }

        return yeeLightDevice;
	}
	
	private YeeLightDevice PrepareDeviceByReceivedPacket(YeeLightDevice yeeLightDevice, DatagramPacket receivePacket) {

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
	        yeeLightDevice.setIp(receivePacket.getAddress().toString().substring(1));
	        String location =responseData.get(DiscoverResponseType.LOCATION);
	        yeeLightDevice.setPort(Integer.parseInt(location.substring(location.lastIndexOf(":")+1)));
	        yeeLightDevice.setName(responseData.get(DiscoverResponseType.NAME));
	        yeeLightDevice.setId(responseData.get(DiscoverResponseType.ID));
	        
	        return yeeLightDevice;
	    }
	
    public boolean setTcpSetting(YeeLightDevice yeeLightDevice) {
        try {
            try {
                if (!yeeLightDevice.clientSocket.isClosed()) {
                	yeeLightDevice.clientSocket.close();
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            yeeLightDevice.clientSocket = new Socket(yeeLightDevice.getIp(), yeeLightDevice.getPort());
            yeeLightDevice.outToServer = new DataOutputStream(yeeLightDevice.clientSocket.getOutputStream());
            yeeLightDevice.inFromServer = new BufferedReader(new InputStreamReader(yeeLightDevice.clientSocket.getInputStream()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void toogleBulb(YeeLightDevice yeeLightDevice){
        String data = "NONE";
        try {
        	yeeLightDevice.outToServer.writeBytes("{\"id\":0,\"method\":" + "\"toggle\",\"params\":[\"" + "" + "\", \"" + "" + "\", "  + "]" + "}" + "\r\n");
            data = yeeLightDevice.inFromServer.readLine();
            System.out.println(data);
            //clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       // return data;

    }
}
