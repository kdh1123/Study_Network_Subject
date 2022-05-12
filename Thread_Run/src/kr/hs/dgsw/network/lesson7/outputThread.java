package kr.hs.dgsw.network.lesson7;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class outputThread extends Thread{
    Scanner input = new Scanner(System.in);

    Socket sc = null;

    public outputThread(Socket sc) {
        this.sc = sc;
    }

    public void run(){
        // 서버로 데이터 보내기
        OutputStream os = null;
        while(true){
            try {
                os = sc.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(os,true);
            pw.println(input.nextLine());
        }
    }
}