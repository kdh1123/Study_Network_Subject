package kr.hs.dgsw.network.test01.n2106.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FTPServer {
    private Map<String,String> userData = new HashMap<>();

    public static void main(String[] args) {
        String fileFolder = "";
        try{
            ServerSocket ss = new ServerSocket(5000);
            Socket sc = ss.accept();

            InputStream is = sc.getInputStream();
            BufferedInputStream bir = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bir);
            String fileName = dis.readUTF();
            FileOutputStream fos = new FileOutputStream(fileFolder+fileName);

            int readSize = 0;
            byte[] bytes = new byte[1024];

            while((readSize=dis.read(bytes)) != -1) {
                fos.write(bytes, 0, readSize);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
