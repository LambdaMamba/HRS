import java.util.Scanner;

public class OnlineReservation {
	public static int ReservationNumber;
	public static boolean UserOnThisOnlineReservationSelected;
	public static char HotelChain;
	public static int userID;
    public static int Date;
    public static int NumberOfAvailableRooms;
	

	public static void StartOnlineReservation(){
		Scanner scan1 = new Scanner(System.in);
		System.out.println("Please input your user ID:");
		userID = scan1.nextInt();
		System.out.println("Please input the date you prefer:");
		Date = scan1.nextInt();
		CheckAvailableRoom(userID);
		
        

	}
	
	public static void CheckAvailableRoom(int userID) {
        NumberOfAvailableRooms = RoomDatabaseServer.SearchAvailableRoom(userID);
        ShowNumberOfAvailableRooms();
		RoomDatabaseServer.CheckHowManyWantToReserveRoom();
		
    }
    
    public static void ShowNumberOfAvailableRooms(){
        System.out.println("Number of available rooms is " + NumberOfAvailableRooms);
    }

	public static void ShowReservationNumber() {
		System.out.println("Your reservation number is "+ ReservationNumber);
	}
	public static void ShowWaitingListWarning() {
		System.out.println("The reservation is currently not available, Please wait. You have been put on the waiting list");
	}
	public static void UserConfirmation() {
		System.out.println("Please confirm your reservation by typing in (1 for Yes) or (0 for No).");
		Scanner scan2 = new Scanner(System.in);
        int reply = scan2.nextInt();
        // int number = -1;
		if (reply==1) {
			boolean userConfirmation = true;
			System.out.println("Would you like to have Wifi services in your room? (1 for Yes) or (0 for No).");
			boolean wifi;
			int WifiNum = scan2.nextInt();

			if (WifiNum == 1){
				wifi = true;
			} else {
				wifi = false;
			}

			RoomDatabaseServer.RegisterReservation(wifi, Date);

		}else if (reply == 0) {
			boolean userConfirmation = false;
			ShowPleaseRebook();
		}
		
		
	}

	public static void UserHereIsSelected() {
        ReservationNumber = RoomDatabaseServer.GenerateReservationNumber();
		ShowReservationNumber();
		UserConfirmation();
	}
	public static void UserHereIsNotSelected() {
		ShowWaitingListWarning();
	}
	public static void ShowPleaseRebook() {
		System.out.println("Please Rebook.");
		System.exit(0);
	}
	
}
