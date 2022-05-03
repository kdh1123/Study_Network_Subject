package kr.hs.dgsw.network.lesson1;

public class Thread1 extends Thread{
    public void run() {

        try {
            for(int i=0; i<500; i++) {
                System.out.println("쓰레드 1 : "+i);
            }
            Thread1.sleep(1);
        } catch (InterruptedException e){
            System.out.println("강제종료됨");
        }

    }
    /*public Thread1(String name){
        setName(name);
    }*/
}
