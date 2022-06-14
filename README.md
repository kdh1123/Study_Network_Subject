# Study_Network_Subject
학교 네트워크 수업 시간에 코딩한 것을 올리는 Repo입니다.


# FTP 파일 전송 프로그램

### 사용 언어 : JAVA

### 사용 기술 : Socket, Thread, DataInput/OutputStream

---

## 목적

> 네트워크 공부를 하기 위해 FTP 서비스를 JAVA로 구현해보기로 하였다.
> 

---

## TODO

- [x]  서버와 클라이언트 구현
- [ ]  업로드(클라이언트 → 서버) 구현
- [ ]  다운로드(서버 → 클라이언트) 구현
- [x]  접속 종료 기능 구현
- [x]  서버에 저장된 파일목록 보기 기능 구현
- [ ]  Thread를 사용한 클라이언트 다중 접속 구현
- **작성한 클래스(Code)**
    - **FTPClient**
        
        ```
        package kr.hs.dgsw.network.test01.n2106.client;
        
        import kr.hs.dgsw.network.test01.n2106.CommonFun;
        
        import java.io.*;
        import java.net.Socket;
        import java.net.SocketException;
        import java.nio.Buffer;
        import java.util.List;
        import java.util.Scanner;
        
        public class FTPClient {
            private static Stringid;
            private static Stringpass;
            private static final StringFILE_FOLDER= "C:/Users/DGSW/Desktop/네트워크 보낼 파일/";
            private static final intPORT= 5000;
            private static final StringIP_ADDRESS= "192.168.35.149";
            private static FTPClientclient;
            private static booleanisLogin= false;
            private static Socketsc;
            Scanner scanner = new Scanner(System.in);
            String fun;
            String name, newName;
            private static CommonFuncommonFun;
            public FTPClient(){
                try {
        sc= new Socket(IP_ADDRESS,PORT);
        commonFun= new CommonFun(sc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            public static void main(String[] args) throws IOException {
                System.out.println("** 서버에 접속하였습니다 **");
        client= new FTPClient();
        client.execute();
            }
        
            public void execute() throws IOException{
        
                if(!isLogin)
        
        client.login();
        
                if(isLogin){
                    while(true) {
                        System.out.println("명령 대기");
                        fun = scanner.next();
        
                        if (fun.startsWith("/")) {
        
                            fun = fun.substring(1);
        
                            if (fun.equals("파일목록")) {
        client.fileList(fun);
                            }
                            else if (fun.equals("업로드")) {
                                try{
                                    String[] nameArray = scanner.nextLine().split(" ");
                                    name = nameArray[1];
                                    newName = nameArray[nameArray.length-1];
        commonFun.sendMSG("업로드");
        client.upload(name, newName);
                                } catch (ArrayIndexOutOfBoundsException e){
                                    System.out.println("** 잘못된 형식의 명령어입니다 **");
                                }
                            }
        
                            else if (fun.equals("다운로드")) {
                                name = scanner.next();
        commonFun.sendMSG(fun);
        client.download(name);
                            }
        
                            else if (fun.equals("접속종료")) {
        client.exit(scanner,sc);
                            }
        
                            else {
                                System.out.println("** 지원하지 않는 명령어입니다 **");
                            }
                        }
                    }
                }
        
            }
            public void upload(String name,String newName) {
                try{
                    Scanner scanner = new Scanner(System.in);
        
                    File file = new File(FILE_FOLDER+name);
                    FileInputStream fis = new FileInputStream(file);
        
        commonFun.sendMSG(newName);
                    System.out.println(newName);
                    String result =
        commonFun.receiveMSG();
        
                    if(result.equals("성공")){
        commonFun.sendFile(file);
                        System.out.println("** "+name+"("+newName+") 파일이 성공적으로 업로드 되었습니다 **");
                    }
                    else if(result.equals("중복")) {
                        System.out.println("** 같은 이름의 파일이 이미 존재합니다, 덮어쓰시겠습니까? (YES / NO) **");
        commonFun.sendMSG(scanner.next());
        commonFun.sendFile(file);
                        if (commonFun.receiveMSG().equals("성공")) {
                            System.out.println("** " + name + " 파일이 성공적으로 업로드 되었습니다 **");
                        }
                        else {
                            System.out.println("** 파일 업로드 실패 **");
                        }
                    }
                } catch (FileNotFoundException e){
        commonFun.sendMSG("실패");
                    System.out.println("** "+name+" 해당 파일을 찾을 수 없습니다 **");
                }
            }
            public void download(String name){
                    Scanner scanner = new Scanner(System.in);
        
                    System.out.println(FILE_FOLDER+name);
                    File file = new File(FILE_FOLDER+name);
        
        commonFun.sendMSG(name);
                    String result =commonFun.receiveMSG();
        
                    if(result.equals("성공")){
        commonFun.receiveFile(file);
                        System.out.println("** "+name+"() 파일이 성공적으로 다운로드 되었습니다 **");
                    }
                    else if(result.equals("실패")) {
                        System.out.println("** 같은 이름의 파일이 이미 존재합니다, 덮어쓰시겠습니까? (YES / NO) **");
                        String answer = scanner.next();
                        if(answer.equals("YES")){
        commonFun.receiveFile(file);
                        }
                        else if(answer.equals("NO")){
                            String[] fileNameEx = name.split("\\.");
                            for(int i=1;; i++) {
                                name = fileNameEx[0] + "("+i+")." + fileNameEx[1];
                                file = new File(FILE_FOLDER+ name);
                                if(file.exists()) continue;
                                break;
                            }
                        }
                        else {
                            System.out.println("** 파일 업로드 실패 **");
                        }
                    }
            }
            public void login() {
                    while(true) {
                        System.out.print("ID : ");
        commonFun.sendMSG(scanner.next());
        
                        System.out.print("PASS : ");
        commonFun.sendMSG(scanner.next());
        
                        String success =
        commonFun.receiveMSG();
        
                        System.out.println(success);
        
                        if (success.equals("성공")){
                            System.out.println("** FTP 서버에 접속했습니다 **");
        isLogin= true;
                            break;
                        }
                        else if(success.equals("실패")){
                            System.out.println("** 비밀번호 또는 아이디가 잘못되었습니다 **");
                        }
                    }
            }
            public void fileList(String fun) throws IOException {
        commonFun.sendMSG(fun);
                String result;
                System.out.println("** [File List] **");
                result =
        commonFun.receiveMSG();
                result = result.substring(1,result.lastIndexOf("]"));
                String[] fileList = result.split(", ");
                for (String f:fileList) {
                    System.out.println("** "+f+" **");
                }
                System.out.println("** 총 "+fileList.length+"개의 파일 **");
            }
            public void exit(Scanner scanner,Socket sc) throws IOException {
                scanner.close();
                sc.close();
                System.out.println("** 접속이 종료되었습니다 **");
            }
        }
        
        ```
        
    - **FTPServer**
        
        ```
        package kr.hs.dgsw.network.test01.n2106.server;
        
        import kr.hs.dgsw.network.test01.n2106.CommonFun;
        
        import java.io.*;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.net.SocketException;
        import java.util.ArrayList;
        import java.util.List;
        
        public class FTPServer extends Thread{
            private static final StringID= "test1";
            private static final StringPASS= "1234";
            private static final StringFILE_FOLDER= "C:/Users/DGSW/Desktop/네트워크 받은 파일/";
            private boolean isLogin = false;
            private static List<Thread>list;
        
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
        list= new ArrayList<Thread>();
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
                if(ID.equals(id) &&PASS.equals(pass)){
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
                    File file = new File(FILE_FOLDER+ fileName);
        
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
                                file = new File(FILE_FOLDER+ fileName);
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
                    File current = new File(FILE_FOLDER+"/"+f);
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
        
        ```
        
    - **CommonFun**

---

# 기능 목록

### /파일목록

<aside>
💡 서버에 저장되어 있는 파일 목록을 클라이언트에 전송해 사용자가  서버의 파일 상태를 확인할 수 있게 한다.

</aside>

DataInput/OutputStream과 File 클래스를 통해 파일 목록을 String 형으로 클라이언트에 전송하면 

Client는 받아서 String 형의 목록을 String[] 형태로 구분해 For문으로 목록과 총 파일 개수 등을 

출력해준다.

### /다운로드 (파일 이름)

<aside>
💡 서버에 저장된 파일 중, 다운로드 요청이 온 파일을 서버 → 클라이언트로 전송한다.

</aside>

DataOutputStream, FIleInputStream으로 파일을 읽어 Bytes[] 형으로 전송한다. 

클라이언트는 DataInputStream, FIleOutputStream으로 Bytes[] 형태로 온 데이터를 받아 파일을 저장한다.

### /업로드 (파일 이름) (새 이름)

<aside>
💡 자신에게 있는 파일 중, 보낼 파일을 선택해 서버에 업로드하고, 파일이 중복된다면, 덮어씌울 지 다른 이름으로 저장할 지 선택한다.

</aside>

DataOutputStream, FIleInputStream으로 파일을 읽어 Bytes[] 형으로 전송한다. 

서버는 DataInputStream, FIleOutputStream으로 Bytes[] 형태로 온 데이터를 받아 파일을 저장한다. 중복되는 이름이 있다면 클라이언트에 중복 메시지를 보내고 답을 받아 덮어 씌울 지 아닐 지를 결정한다.

### 접속 종료

<aside>
💡 접속을 종료할 수 있게 한다.

</aside>

Socket을 닫는다.

---

## 힘들었던 점

파일 전송 과정에 계속 오류가 생겨 그 오류를 고치는데 시간을 많이 소비했지만, 결국 해결하지 못했다. 핵심 기능이었기에 실망이 크다.

## 뿌듯한 점

JAVA로 기능하는 프로그램을 직접 만들어 사용해보니 감회가 남달랐다. JAVA 실력을 기르는데에도  한몫 한 것 같다. 

## 아쉬운 점

오류로 인해 완성하지 못했기 때문에 보수해서 완성해보고 싶다.
