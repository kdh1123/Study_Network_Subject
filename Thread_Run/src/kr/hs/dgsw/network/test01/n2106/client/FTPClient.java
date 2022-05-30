package kr.hs.dgsw.network.test01.n2106.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
    private final Scanner scanner;
    private String id;
    private String pw;
    private String filefolder = "";
    public FTPClient() {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws IOException {
        Socket sc = new Socket("",5000);
        FTPClient client = new FTPClient();

        OutputStream os = sc.getOutputStream();
        BufferedOutputStream bor = new BufferedOutputStream(os);
        DataOutputStream dos = new DataOutputStream(bor);

        File f1 = new File(client.filefolder);
        FileInputStream fis = new FileInputStream(f1);

        dos.writeUTF(f1.getName());

        int readSize = 0;
        byte[] bytes = new byte[1024];

        while((readSize=fis.read(bytes)) != -1){
            dos.write(bytes,0,readSize);
        }
    }
}
