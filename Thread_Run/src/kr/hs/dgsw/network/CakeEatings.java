package kr.hs.dgsw.network;

public class CakeEatings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CakePlate cake = new CakePlate();
		CakeEater eater = new CakeEater(cake);
		CakeMaker baker = new CakeMaker(cake);
		
		baker.start();
		eater.start();
	}

}
