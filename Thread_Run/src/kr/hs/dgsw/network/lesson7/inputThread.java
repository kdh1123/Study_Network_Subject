package kr.hs.dgsw.network.lesson7;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class inputThread extends Thread{
    private static List<inputThread> ClientList = (List<inputThread>) Collections.synchronizedList(new ArrayList<inputThread>());


    Socket sc;
    inputThread(Socket sc) {
        this.sc = sc;
        ClientList.add(this);
    }
    public void sendMassage(String msg){
        OutputStream os = null;
        try {
            os = this.sc.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(os, true);
        pw.println(msg);
    }
    public void run() {
            InputStream is = null;
            try {
                is = this.sc.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            while(true){
                for (inputThread tmpit:ClientList){
                    tmpit.sendMassage(br.readLine());
                }
            }
        } catch (IOException e ){
            e.printStackTrace();
        }
    }
}
