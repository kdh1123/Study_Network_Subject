package kr.hs.dgsw.network.lesson7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            ServerSocket ss = new ServerSocket(5000);
            while(true) {
                Socket sc = ss.accept();
                Thread inputThread = new inputThread(sc);
                inputThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}