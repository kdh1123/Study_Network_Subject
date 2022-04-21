package kr.hs.dgsw.network;

public class Thread1 extends Thread{
    public void run() {
        for(int i=0; i<500; i++) {
            System.out.println("쓰레드 1 : "+i);
        }
    }
}
