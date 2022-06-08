package kr.hs.dgsw.network.test01.n2106.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FTPServer extends Thread{
    private static final String ID = "test1";
    private static final String PASS = "1234";
    private static final String fileFolder = "C:/Users/DGSW/Desktop/네트워크 받은 파일/";
    private static boolean isLogin = false;
    private static Socket sc;

    public FTPServer(ServerSocket ss){
       try {
           sc = ss.accept();
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    @Override
    public void run(){

        try {
            String fun;
            String fileName;
            InputStream is = sc.getInputStream();
            OutputStream os = sc.getOutputStream();
            BufferedInputStream bir = new BufferedInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            PrintWriter pw = new PrintWriter(os, true);
            if (!isLogin) {
                isLogin = this.login();
            }
            if (isLogin) {
                System.out.println("명령 프롬프트 진입");
                while (true) {
                    fun = br.readLine();
                    if (fun.equals("파일목록")) {
                        pw.println(this.fileList());
                    } else if (fun.equals("업로드")) {
                        this.upload();
                    } else if (fun.equals("다운로드")) {
                        this.download();
                    } else if (fun.equals("접속종료")) {

                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(5000);
            boolean isExit = false;
            Thread ftpServer = new FTPServer(ss);
            ftpServer.start();
        } catch (SocketException e) {
        } catch (IOException e) {
            System.out.println("연결 실패");
        }
    }
    public boolean login() throws IOException,RuntimeException {
        InputStream is = sc.getInputStream();
        OutputStream os = sc.getOutputStream();
        System.out.println("로그인 함수 실행");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(os,true);
        pw.println("로그인");
        while(true){
        String id = br.readLine();
        String pass = br.readLine();
        if(ID.equals(id) && PASS.equals(pass)){
            System.out.println("로그인 성공");
            pw.println("성공");
            return true;
        }
        else {
            System.out.println("로그인 실패");
            pw.println("실패");
            }
        }
    }
    public void upload(){
        try{
            InputStream is = sc.getInputStream();
            OutputStream os = sc.getOutputStream();
            BufferedInputStream bir = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bir);
            PrintWriter pw = new PrintWriter(os,true);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //파일 이름 받기
            String fileName = br.readLine();
            File file = new File(fileFolder+fileName);

            if(new File(fileFolder + fileName).exists()){
                pw.println("중복");
                System.out.println("중복");
                String answer = br.readLine();
                if(answer.equals("YES")){
                    System.out.println(answer);
                    this.fileInPut(file);
                    System.out.println("업로드 성공");
                    pw.println("성공");
                }
                else if(answer.equals("NO")){
                    System.out.println(answer);
                    String[] fileNameEx = fileName.split("\\.");
                    for(int i=1;; i++) {
                        fileName = fileNameEx[0] + "("+i+")." + fileNameEx[1];
                        file = new File(fileFolder + fileName);
                        if(file.exists()) continue;
                        break;
                    }
                    this.fileInPut(file);
                    System.out.println("업로드 성공");
                    pw.println("성공");
                }
                else {
                    pw.println("실패");
                }
            }
            else {
                this.fileInPut(file);
                pw.println("성공");
                System.out.println("업로드 성공");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void download() throws IOException{
        try{
            InputStream is = sc.getInputStream();
            OutputStream os = sc.getOutputStream();
            BufferedOutputStream bor = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bor);
            PrintWriter pw = new PrintWriter(os,true);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //파일 이름 받기
            String fileName = br.readLine();
            File file = new File(fileFolder+fileName);

            if(file.exists()) {
                this.fileOutPut(file);
                pw.println("성공");
            }
        } catch (SocketException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }catch (RuntimeException e){
            e.printStackTrace();
        }

    }
    public List<String> fileList(){
        File file = new File(fileFolder);
        List<String> list = new ArrayList<String>();
        for (String f:file.list()) {
            File current = new File(fileFolder +"/"+f);
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
    public void fileInPut(File file){
        try {
            InputStream is = sc.getInputStream();
            OutputStream os = sc.getOutputStream();
            BufferedInputStream bir = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bir);
            FileOutputStream fos = new FileOutputStream(file);
            int readSize = 0;
            byte[] bytes = new byte[1024];

            do {
                fos.write(bytes, 0, readSize);
            } while ((readSize = dis.read(bytes)) == 1024);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void fileOutPut(File file){
        try{
            InputStream is = sc.getInputStream();
            OutputStream os = sc.getOutputStream();
            BufferedOutputStream bor = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bor);
            FileInputStream fis = new FileInputStream(file);
            int readSize = 0;
            byte[] bytes = new byte[1024];
            do {
                dos.write(bytes, 0, readSize);
            } while ((readSize = fis.read(bytes)) == 1024);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
