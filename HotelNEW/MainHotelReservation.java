import java.util.Scanner;

public class MainHotelReservation{
    public static void main(String[] args) {

		int test;
		
		Scanner scan1 = new Scanner(System.in);


		System.out.println("[Test] Choose test case: 1 for Room Available, 2 for Room Full, 3 for Two Users want a single vacant room");
		test = scan1.nextInt();
		if(test==1){
			RoomDatabaseServer.RoomAvailable();
		} else if (test==2){
			RoomDatabaseServer.RoomFull();
		} else if (test == 3){
			RoomDatabaseServer.TwoUserWantOneAvailableRoom();
        }
        
        System.out.println("***Online Reservation***");
        OnlineReservation.StartOnlineReservation();

		System.out.println();
        System.out.println("***Check In***");
        Staff.StartCheckin();

		System.out.println();
        System.out.println("***Check out***");
        Staff.StartCheckout();
		
	}

}