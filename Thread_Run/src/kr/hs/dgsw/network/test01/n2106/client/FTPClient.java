package kr.hs.dgsw.network.test01.n2106.client;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
    private final Scanner scanner;
    private String id;
    private String pw;
    public FTPClient(){
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws IOException {
        Socket sc = new Socket("",5000);

        OutputStream os = sc.getOutputStream();
        BufferedOutputStream bor = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bor);


        while(true){

        }
    }
}
