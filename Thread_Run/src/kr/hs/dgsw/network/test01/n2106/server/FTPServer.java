package kr.hs.dgsw.network.test01.n2106.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FTPServer {
    private static final String ID = "test1";
    private static final String PASS = "1234";
    private static final String fileFolder = "C:/Users/DGSW/Desktop/네트워크 받은 파일";
    private static boolean isLogin = false;
    private static Socket sc;



    public static void main(String[] args) {
        FTPServer server = new FTPServer();
        while(true) {
            try {
                ServerSocket ss = new ServerSocket(5000);
                String fun;
                String fileName;
                boolean isExit = false;
                while (!isExit) {
                    sc = ss.accept();
                    InputStream is = sc.getInputStream();
                    OutputStream os = sc.getOutputStream();
                    BufferedInputStream bir = new BufferedInputStream(is);
                    DataInputStream dis = new DataInputStream(bir);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    PrintWriter pw = new PrintWriter(os, true);
                    if (!isLogin) {
                        isLogin = server.login();
                    }
                    if (isLogin) {
                        System.out.println("명령 프롬프트 진입");
                        while (true) {
                            fun = br.readLine();
                            if (fun.equals("파일목록")) {
                                pw.println(server.fileList());
                            } else if (fun.equals("업로드")) {
                                server.upload();
                            } else if (fun.equals("다운로드")) {
                                server.download();
                            } else if (fun.equals("접속종료")) {
                                isExit = true;
                            }
                        }
                    }
                }
            } catch (SocketException e) {
            } catch (IOException e) {
                System.out.println("연결 실패");
            }
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

            //파일 이름 받기
            String fileName = dis.readUTF();
            File file = new File(fileFolder+"/"+fileName);
            System.out.println(fileFolder+"/"+fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            br.mark(0);

            if(file.exists()){
                pw.println("중복");
                System.out.println("중복");
                br.reset();
                String answer = br.readLine();
                System.out.println(answer);
                if(answer.equals("YES")){
                    FileOutputStream fos = new FileOutputStream(file);
                    int readSize = 0;
                    byte[] bytes = new byte[1024];

                    while ((readSize = dis.read(bytes)) != -1) {
                        fos.write(bytes, 0, readSize);
                    }
                    System.out.println("업로드 성공");
                    pw.println("성공");
                }
                else if(answer.equals("NO")){
                    String[] fileNameEx = fileName.split(".");
                    for(int i=1;; i++) {
                        fileName = fileNameEx[0] + "("+Integer.toString(i)+")" + fileNameEx[1];
                        file = new File(fileFolder +"/"+ fileName);
                        if(file.exists()) continue;
                        break;
                    }
                    System.out.println("업로드 성공");
                    pw.println("성공");
                }
            }
            else {
                pw.println("성공");
                System.out.println("업로드 성공");
                FileOutputStream fos = new FileOutputStream(file);

                int readSize = 0;
                byte[] bytes = new byte[1024];

                while ((readSize = dis.read(bytes)) != -1) {
                    fos.write(bytes, 0, readSize);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void download() throws IOException{
        InputStream is = sc.getInputStream();
        OutputStream os = sc.getOutputStream();
        BufferedInputStream bir = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(bir);
        PrintWriter pw = new PrintWriter(os,true);
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

}
