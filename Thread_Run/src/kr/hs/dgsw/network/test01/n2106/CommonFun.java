package kr.hs.dgsw.network.test01.n2106;

import java.io.*;
import java.net.Socket;

public class CommonFun {
    private Socket sc;
    private OutputStream os;
    private InputStream is;
    private BufferedInputStream bir;
    private DataInputStream dis;
    BufferedOutputStream bor;
    DataOutputStream dos;
    public CommonFun(Socket sc){
        this.sc = sc;
        try{
            is = sc.getInputStream();
            os = sc.getOutputStream();
            bir = new BufferedInputStream(is);
            dis = new DataInputStream(bir);
            dos = new DataOutputStream(bor);
            bor = new BufferedOutputStream(os);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void receiveFile(File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            int readSize = 0;
            byte[] bytes = new byte[1024];

            while ((readSize = dis.read(bytes)) != -1){
                fos.write(bytes, 0, readSize);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendFile(File file){
        try{
            FileInputStream fis = new FileInputStream(file);
            int readSize = 0;
            byte[] bytes = new byte[1024];
            while((readSize = fis.read(bytes)) != -1) {
                dos.write(bytes, 0, readSize);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public String receiveMSG(){
        try{
        return dis.readUTF();
        }catch (Exception e){
            return "";
        }
    }
    public void sendMSG(String str){
        try{
            dos.writeUTF(str);
            dos.flush();
        }catch (Exception e){
            System.out.println(str+" 실패");
        }
    }
}
