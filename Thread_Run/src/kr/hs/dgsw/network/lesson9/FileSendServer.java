package kr.hs.dgsw.network.lesson9;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileSendServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5000);
        Socket sc = ss.accept();

        InputStream is = sc.getInputStream();
        BufferedInputStream bir = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bir);

        String fileName = dis.readUTF();
        FileOutputStream fos = new FileOutputStream("C:/Users/DGSW/Desktop/네트워크 받은 파일/"+fileName);

        int readSize = 0;
        byte[] bytes = new byte[1024];

        while((readSize=dis.read(bytes)) != -1){
            fos.write(bytes,0,readSize);
        }
    }
}
