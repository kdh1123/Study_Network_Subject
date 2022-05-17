package kr.hs.dgsw.network.lesson7;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class outputThread extends Thread{
    Socket sc;
    outputThread(Socket sc) {
        this.sc = sc;
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        OutputStream os = null;
        try {
            os = this.sc.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(os, true);
        pw.println(scanner.nextLine());
    }

}