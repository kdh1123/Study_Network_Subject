package kr.hs.dgsw.network.test01.n2106.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class FTPServer {
    private static final Map<String,String> userData =
            Map.of("test1","1234");
    private static final String fileFolder = "C:/Users/DGSW/Desktop/네트워크 받은 파일";
    private static boolean isLogin = false;
    private static String id;
    private static String pass;
    private static boolean isExit = false;
    private static InputStream is;
    private static OutputStream os;
    private static BufferedInputStream bir;
    private static DataInputStream dis;
    private static BufferedReader br;
    private static PrintWriter pw;



    public static void main(String[] args) throws IOException {
        FTPServer server = new FTPServer();
        ServerSocket ss = new ServerSocket(5000);
        String fun;
        String fileName;
        while (!isExit) {
            Socket sc = ss.accept();
            is = sc.getInputStream();
            os = sc.getOutputStream();
            bir = new BufferedInputStream(is);
            dis = new DataInputStream(bir);
            br = new BufferedReader(new InputStreamReader(is));
            pw = new PrintWriter(os,true);
                if(!isLogin) {
                    isLogin = server.login();
                }
                if(isLogin){
                    while(true){
                        fun = br.readLine();
                        if(fun.equals("파일목록")){
                            pw.println(server.fileList());
                        }
                        else if(fun.equals("업로드")){

                        }
                        else if(fun.equals("다운로드")){

                        }
                    }
                }
        }
    }
    public boolean login() throws IOException {
        System.out.println("로그인 함수 실행");
        id = br.readLine();
        pass = br.readLine();
        System.out.println(id + pass);
        if(userData.get(id).equals(pass)){
            System.out.println("로그인 성공");
            pw.println("성공");
            return true;
        }
        else {
            System.out.println("로그인 실패");
            pw.println("실패");
        }
        return false;
    }
    public void upload(DataInputStream dis) throws IOException{
        String fileName = dis.readUTF();
        FileOutputStream fos = new FileOutputStream(fileFolder + fileName);

        int readSize = 0;
        byte[] bytes = new byte[1024];

        while ((readSize = dis.read(bytes)) != -1) {
            fos.write(bytes, 0, readSize);
        }
    }
    public List<String> fileList(){
        File file = new File(fileFolder);
        List<String> list = null;
        for (String f:file.list()) {
            list.add(f+" "+f.length());
        }
        System.out.println(list);
        return list;
    }


}
