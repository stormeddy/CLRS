package beerbottle;

public class DrinkBeer {
	private int beer,bottle,lid;
	
	public DrinkBeer(int beer,int bottle,int lid){
		this.beer=beer;
		this.bottle=bottle;
		this.lid=lid;
	}
	
	
	public int getBeer() {
		int num=beer;
		int judge=beer;
		int nbottle,nlid;
		while(judge!=0){
			nbottle=bottle;
			nlid=lid;
			bottle=(judge+bottle)%2;
			lid=(judge+lid)%4;
			judge=(judge+nbottle)/2+(judge+nlid)/4;			
			num=num+judge;
		}
		return num;
	}
	
	public int getReserveLid(){
		return lid;
	}
	
	public int getRserveBottle(){
		return bottle;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DrinkBeer drinkBeer=new DrinkBeer(5, 1, 2);
		System.out.println(drinkBeer.getReserveLid());
		System.out.println(drinkBeer.getRserveBottle());
		System.out.println(drinkBeer.getBeer());
	}

}
