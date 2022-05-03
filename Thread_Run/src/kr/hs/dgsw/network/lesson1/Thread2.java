package kr.hs.dgsw.network.lesson1;

public class Thread2 implements Runnable{
    public void run() {
        for(int i=0; i<500; i++) {
            System.out.println("쓰레드 2 : "+i);
        }
    }
}
