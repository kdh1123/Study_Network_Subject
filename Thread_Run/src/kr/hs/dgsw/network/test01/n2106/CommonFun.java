package kr.hs.dgsw.network.test01.n2106;

import java.io.*;
import java.net.Socket;

public class CommonFun {
    private Socket sc;
    private OutputStream os;
    private InputStream is;
    BufferedInputStream bis;
    DataInputStream dis;
    BufferedOutputStream bos;
    DataOutputStream dos;
    FileOutputStream fos;
    FileInputStream fis;
    public CommonFun(Socket sc){
        this.sc = sc;
        try{
            is = sc.getInputStream();
            os = sc.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void receiveFile(File file){

        try {
            fos = new FileOutputStream(file);
            bis = new BufferedInputStream(dis);
            bos = new BufferedOutputStream(fos);
            System.out.println("받을 파일 : "+file);
            int readSize = 0;
            byte[] bytes = new byte[1024];

            while ((readSize = bis.read(bytes)) > 0){
                bos.write(bytes, 0, readSize);
            }
            System.out.println("파일 받음");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendFile(File file){
        try{
            fis = new FileInputStream(file);
            bos = new BufferedOutputStream(dos);
            bis = new BufferedInputStream(fis);
            System.out.println("보낼 파일 : "+file);
            int readSize = 0;
            byte[] bytes = new byte[1024];
            while((readSize = bis.read(bytes)) > 0) {
                dos.write(bytes, 0, readSize);
            }
            System.out.println("파일 보냄");
            fis.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public String receiveMSG(){
        try{
        return dis.readUTF();
        }catch (Exception e){
            return "error";
        }
    }
    public void sendMSG(String str){
        try{
            dos.writeUTF(str);
            System.out.println("전송 : " + str);
            dos.flush();
        }catch (IOException e){
            System.out.println(str+" 실패");
        }
    }
}
