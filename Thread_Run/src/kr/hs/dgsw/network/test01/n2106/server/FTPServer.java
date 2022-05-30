package kr.hs.dgsw.network.test01.n2106.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FTPServer {

    private Map<String,String> userData = new HashMap<>();
    private static final String fileFolder = "";
    private static boolean isLogin = false;
    static class outputThread extends Thread {
        Socket sc;
        public outputThread(Socket sc){
            this.sc = sc;
        }
        public void run(String msg) {
            OutputStream os = null;
            try {
                os = this.sc.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(os, true);
            pw.write(msg);
        }
    }

    public static void main(String[] args) throws IOException {
        FTPServer server = new FTPServer();
        ServerSocket ss = new ServerSocket(5000);
        while (true) {
                Socket sc = ss.accept();
                InputStream is = sc.getInputStream();
                BufferedInputStream bir = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bir);
                if(!isLogin) {
                    isLogin = server.login();
                    if(!isLogin){

                    }
                }
                if(isLogin){
                    String fileName = dis.readUTF();
                    FileOutputStream fos = new FileOutputStream(fileFolder + fileName);

                    int readSize = 0;
                    byte[] bytes = new byte[1024];

                    while ((readSize = dis.read(bytes)) != -1) {
                        fos.write(bytes, 0, readSize);
                    }
                }
        }
    }
    public boolean login(String id, String pw){
        return userData.get(id).equals(pw);
    }

}
