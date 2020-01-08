import java.util.Scanner;

public class Staff {
	public static char HotelChain;
	public static int ReservationNumber;
	public static int RoomNumber;
	public static boolean ReceiveFee;
    public static int staffaction;
    public static String details;
    public static int fee;
    public static boolean wifi;
    // System.out.println("Input action Number(1 for CheckIn, 2 for CheckOut, 3 for Reservation detail):");
    
	// Scanner scan = new Scanner(System.in);
	// staffaction = scan.nextInt();
	// public static void main(String[] args) {
	// if (staffaction == 1) {
	// 	ReceiveReservationNo();
	// }else if(staffaction == 2) {
	// 	TellFee();
	// 	System.out.println("Payment status:");
	// 	Scanner scan = new Scanner(System.in);
	// 	ReceiveFee = scan.nextBoolean();
	// }else if (staffaction == 3){
	// 	ShowOnlineReservationDetails();
	// }
	// if(ReceiveFee == true){
	// 	TerminateCheckOut()
	// }
	
    // }
    

    public static void StartCheckin(){
        ReceiveReservationNo(); 

    }

    public static void StartCheckout(){
        TellFee();
        int userFee;
		Scanner scan = new Scanner(System.in);
        // ReceiveFee = scan.nextBoolean();
        // if(ReceiveFee == true){
        //     TerminateCheckOut();
        // }
        userFee = scan.nextInt();
        if(userFee < fee){
            System.out.println("You owe us.");
        } else if (userFee > fee){
            System.out.println("Here is your change: " + (userFee-fee));
        } else if (userFee == fee){
            System.out.println("Exact payment accepted.");
        }
        TerminateCheckOut();

    }

	public static void ReceiveReservationNo() {
		System.out.println("Input Reservation Number:");
        Scanner scan = new Scanner(System.in);
        
        ReservationNumber = scan.nextInt();

        
        RoomNumber = RoomDatabaseServer.SearchRoomCorrespondingToReservationNumber(ReservationNumber);
        if(RoomNumber == -1) {
            System.out.println("Your reservation number is not found.");
            TellUserToRebook();
        } else {
            details = RoomDatabaseServer.FetchOnlineReservationDetails();
            ShowOnlineReservationDetails();
        }
        
	}	
	public static void TellRoomNumber(){
        
        System.out.println("Your room number is "+RoomNumber);
		
			//Tell User to Rebook
		// }else {
		// 	RoomNumber = RN;
        //     System.out.println("RoomNumber is" + RN);

        //     details = RoomDatabaseServer.FetchOnlineReservationDetails();
           
        //     ShowOnlineReservationDetails();

		// 	//Receive RoomNumber
		// }
    }
    

	public static void TellFee() {
		System.out.println("Input Room Number:");
		Scanner scan = new Scanner(System.in);
		RoomNumber = scan.nextInt();
		fee = RoomDatabaseServer.SearchFeeOfRoomNumber(RoomNumber);
		System.out.println("Please pay " + fee);
    }
    
	public static void ShowOnlineReservationDetails() {
        int confirm;
        Scanner scan = new Scanner(System.in);
        System.out.println("The online reservation details are "+details);
        System.out.println("Are these correct? (1 for yes) or (0 for no)");
        confirm = scan.nextInt();
        if(confirm==1){
            TellRoomNumber();
        } else if (confirm==0){
            TellUserToRebook();
        }

    }

    public static void TellUserToRebook(){
        System.out.println("Please rebook.");
        System.exit(0);
    }
    

	public static void TerminateCheckOut() {
		// staffaction = 0;
        // ReceiveFee = false;
        System.out.println("Thank you for staying at Bilton! We hope to see you again!");
        RoomDatabaseServer.UserHasLeft(RoomNumber);
        
    }
    


    public static void ConductCheckin(){

        wifi = RoomDatabaseServer.SeeWifiDetails();
        RoomDatabaseServer.StartWifi(RoomNumber, wifi);
    }


    public static void ConductCheckout(){
        RoomDatabaseServer.StartWifi(RoomNumber, false);
    }

}