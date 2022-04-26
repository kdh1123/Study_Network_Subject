package kr.hs.dgsw.network;

public class Thread_interrupt extends Thread{
    public void run() {

        try {
            for(int i=0; i<500; i++) {
                System.out.println("쓰레드 1 : "+i);
            }
            Thread_interrupt.sleep(1);
        } catch (InterruptedException e){
            System.out.println("강제종료됨");
        }

    }
    /*public Thread1(String name){
        setName(name);
    }*/
}
