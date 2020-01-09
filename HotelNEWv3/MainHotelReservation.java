import java.util.Scanner;

public class MainHotelReservation{
    public static void main(String[] args) {

		int test;
		
		Scanner scan1 = new Scanner(System.in);

		System.out.println("Choose Hotel Chain, 1 for Shinjuku, 2 for Ikebukuro, 3 for Nishi Waseda, 4 for Takadanobaba");
		int choice = scan1.nextInt();
		Hotel.HotelChain(choice);
		
		System.out.println("Redirecting you to your chosen Hotel Chain's Online Reservation System...");


		System.out.println("[Test] Choose test case: 1 for Room Available, 2 for Room Full, 3 for Two Users want a single vacant room");
		test = scan1.nextInt();
		if(test==1){
			ServerProcessor.RoomAvailable();
		} else if (test==2){
			ServerProcessor.RoomFull();
		} else if (test == 3){
			ServerProcessor.TwoUserWantOneAvailableRoom();
        }
		
		System.out.println();
        System.out.println("***Online Reservation***");
        OnlineReservation.StartOnlineReservation();


		try{
			for(int i=0; i<5; i++){
				Thread.sleep(500);
				System.out.println(".");

			}
			

			System.out.println("***Check In***");
			Staff.StartCheckin();
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}
		

		try{
			for(int i=0; i<5; i++){
				Thread.sleep(500);
				System.out.println(".");

			}
			System.out.println("***Check out***");
			Staff.StartCheckout();
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}

		
		
	}

}