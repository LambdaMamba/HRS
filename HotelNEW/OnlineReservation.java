import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class OnlineReservation {
	public static int ReservationNumber;
	public static boolean UserOnThisOnlineReservationSelected;
	public static char HotelChain;
	public static int userID;
    public static String Date;
	public static int NumberOfAvailableRooms;
	private static String details;

	

	public static void StartOnlineReservation(){
		inputID();
		inputDate();
		CheckAvailableRoom(userID);	
		
		

	}
	public static void inputID(){

		try{
			Scanner scan1 = new Scanner(System.in);
			System.out.println("Please input your user ID in five digits:");
			int ID = scan1.nextInt();
			int length = String.valueOf(ID).length();
			if(length != 5){
				inputID();

			}else if(length == 5){
				userID = ID;
			}
		}catch(InputMismatchException e){
			System.out.println("Please input 5 digit integer!");
			inputID();
		}
	}
	public static void inputDate(){
		// Scanner scand = new Scanner(System.in);
		// System.out.println("Please input the date you prefer in format YYMMDD :");
		// String Day = scand.next();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		String today = sdf.format(c.getTime());
		System.out.println("Today is "+today);

		c.add(c.DATE, 7);
		Date = sdf.format(c.getTime());
		System.out.println("Rooms available on "+Date +"(1 week later) will be searched.");
		
		
		
	}
	
	public static void CheckAvailableRoom(int userID) {
		int i;
		for(i=0; i<5; i++){
			try{
				Thread.sleep(500);
				System.out.println("...");
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
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
        char reply = scan2.next().charAt(0);
        // int number = -1;
		if (reply=='1') {
			WifiConfirmation();
		}else if (reply == '0') {
			boolean userConfirmation = false;
			ShowPleaseRebook();
		}else{
			UserConfirmation();
		}
		
		
	}
	public static void WifiConfirmation(){
			boolean userConfirmation = true;
			System.out.println("Would you like to have Wifi services in your room? (1 for Yes) or (0 for No).");
			Scanner scan3 = new Scanner(System.in);
			boolean wifi;
			char WifiNum = scan3.next().charAt(0);

			if (WifiNum == '1'){
				wifi = true;
				RoomDatabaseServer.RegisterReservation(wifi, Date);
				ShowDetails();
			} else if (WifiNum == '0') {
				wifi = false;
				RoomDatabaseServer.RegisterReservation(wifi, Date);
				ShowDetails();
			} else{
				WifiConfirmation();
			}

			
	}

	public static void UserHereIsSelected() {
		System.out.println("Please wait, we are generating reservation number.");
		int i;
		for(i=0; i<5; i++){
			try{
				Thread.sleep(500);
				System.out.println("...");
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}

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


	public static void ShowDetails(){
		System.out.println();
		details = RoomDatabaseServer.FetchOnlineReservationDetails();
		System.out.println("Your reservation is registered as "+details);
		System.out.println("We will be looking forward to your stay 1 week later!");
	}
	
}
