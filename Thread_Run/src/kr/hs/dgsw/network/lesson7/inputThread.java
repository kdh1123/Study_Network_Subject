package kr.hs.dgsw.network.lesson7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class inputThread extends Thread{
    Socket sc = null;

    public inputThread(Socket sc){
        this.sc = sc;
    }
    public void run() {
        while(true){
        try {
            InputStream is = this.sc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader((is)));

            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}
