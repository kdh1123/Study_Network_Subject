package kr.hs.dgsw.network.lesson6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChattingServerClass {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(5000);

            Socket sc = ss.accept();

            InputStream is = sc.getInputStream();
            BufferedReader br =
                    new BufferedReader(new InputStreamReader((is)));
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
