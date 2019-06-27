package com.morningglory.basic.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: nsh
 * @Date: 2018/5/25
 * @Description:
 */
public class SocketServeDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2006);
        Socket client = null;

        client = serverSocket.accept();

        BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String str =  buf.readLine();

        System.out.println("msg :"+str);
    }
}