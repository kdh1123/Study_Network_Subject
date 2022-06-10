package kr.hs.dgsw.network.test01.n2106.server;

import kr.hs.dgsw.network.test01.n2106.CommonFun;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class FTPServer extends Thread{
    private static final String ID = "test1";
    private static final String PASS = "1234";
    private static final String FILE_FOLDER = "C:/Users/DGSW/Desktop/네트워크 받은 파일/";
    private boolean isLogin = false;
    private static List<Thread> list;

    private Socket sc;
    private CommonFun commonFun;

    public FTPServer(ServerSocket ss){
       try {
           sc = ss.accept();
           commonFun = new CommonFun(sc);
       }catch (IOException e){
           e.printStackTrace();
       }
    }
    @Override
    public void run(){
        try {
            if (!isLogin) {
                isLogin = this.login();
            }
            if (isLogin) {
                while (true) {
                    System.out.println("명령 대기");
                    String fun = commonFun.receiveMSG();
                    System.out.println(fun);
                    if (fun.equals("파일목록")) {
                        commonFun.sendMSG(this.fileList().toString());
                    } else if (fun.equals("업로드")) {
                        this.upload();
                    } else if (fun.equals("다운로드")) {
                        this.download();
                    } else if (fun.equals("접속종료")) {
                        list.remove(this);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(5000);
            ss.setReuseAddress(true);
            list = new ArrayList<Thread>();
            while(true) {
                Thread ftpServer = new FTPServer(ss);
                list.add(ftpServer);
                ftpServer.start();
            }
        } catch (SocketException e) {
        } catch (IOException e) {
            System.out.println("연결 실패");
        }
    }
    public boolean login() throws IOException,RuntimeException {
        System.out.println("로그인 함수 실행");
        while(true){
        String id = commonFun.receiveMSG();
            System.out.println(id);
        String pass = commonFun.receiveMSG();

            System.out.println(id);
            System.out.println(pass);

        //로그인 성공 여부 반환
        if(ID.equals(id) && PASS.equals(pass)){
            commonFun.sendMSG("성공");
            return true;
        }
        else {
            System.out.println("로그인 실패");
            commonFun.sendMSG("실패");
            }
        }
    }
    public void upload(){
        //파일 이름 받기
        String fileName = commonFun.receiveMSG();
        if(!fileName.equals("실패")) {
            File file = new File(FILE_FOLDER + fileName);

            if (file.exists()) {
                //중복일 때,
                commonFun.sendMSG("중복");
                System.out.println("중복");

                String answer =
                        commonFun.receiveMSG();

                if (answer.equals("YES")) {

                    System.out.println(answer);
                    commonFun.receiveFile(file);

                    System.out.println("업로드 성공");
                    commonFun.sendMSG("성공");
                } else if (answer.equals("NO")) {
                    System.out.println(answer);

                    String[] fileNameEx = fileName.split("\\.");
                    for (int i = 1; ; i++) {
                        fileName = fileNameEx[0] + "(" + i + ")." + fileNameEx[1];
                        file = new File(FILE_FOLDER + fileName);
                        if (file.exists()) continue;
                        break;
                    }

                    commonFun.receiveFile(file);

                    System.out.println("업로드 성공");
                    commonFun.sendMSG("성공");
                } else {
                    commonFun.sendMSG("실패");
                }
            } else {
                commonFun.receiveFile(file);

                commonFun.sendMSG("성공");
                System.out.println("업로드 성공");
            }
        }
    }
    public void download(){

        //파일 이름 받기
        String fileName = commonFun.receiveMSG();
        System.out.println(fileName);
        File file = new File(FILE_FOLDER+fileName);

        if(file.exists()) {
            commonFun.sendFile(file);
            commonFun.sendMSG("성공");
        }
        else{
            commonFun.sendMSG("실패");
        }

    }
    public List<String> fileList(){
        File file = new File(FILE_FOLDER);
        List<String> list = new ArrayList<String>();
        for (String f:file.list()) {
            File current = new File(FILE_FOLDER +"/"+f);
            long val = current.length();
            String len = val + " B";
            if(val > 1024){
                val /= 1024;
                len = val+"Kb";
            }
            if(val > 1024){
                val /= 1024;
                len = val+"Mb";
            }
            if(val > 1024){
                val /= 1024;
                len = val+"Gb";
            }
            list.add(f+" "+len);
        }
        System.out.println(list);
        return list;
    }

}
