package com.smarthome.Models.YeeLightBulb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

import com.smarthome.Models.BaseDevice;

public class YeeLightDevice extends BaseDevice {

    public static final String multicastChannel = "239.255.255.250";
    public static final int port = 1982;
    public static final int timeout = 15000;
    public byte[] receiveData = new byte[1024];

    public Socket clientSocket;
    public DataOutputStream outToServer;
    public BufferedReader inFromServer;
    
}
