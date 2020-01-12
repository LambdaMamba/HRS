import java.util.InputMismatchException;
import java.util.Scanner;

public class RobotStaff extends Hotel{
	private static int ReservationNumber;
	private static int RoomNumber;
    private static String details;
    private static int fee;
    private static boolean wifi;
    private static boolean angryRobotStaff = false;

    

    public static void StartCheckin(){
        System.out.println("Welcome to "+HotelName+" "+location+"!");
        ReceiveReservationNo(); 

    }

    public static void StartCheckout(){
        TellFee();
        ReceivePayment();


        ConductCheckout();
        TerminateCheckOut();

    }

    public static void ReceivePayment(){
        int userFee;
        try{
            Scanner scan = new Scanner(System.in);

            userFee = scan.nextInt();
            if(userFee < fee){
                System.out.println("Not enough money!");
                angryRobotStaff = true;
            } else if (userFee > fee){
                System.out.println("Here is your change: " + (userFee-fee));
            } else if (userFee == fee){
                System.out.println("Exact payment accepted.");
            }
        } catch (InputMismatchException e){
            System.out.println("Invalid payment. Please enter integer value.");
            ReceivePayment();

        }
    }

	public static void ReceiveReservationNo() {

        try{
            System.out.println("Input Reservation Number:");
            Scanner scan = new Scanner(System.in);
            
            ReservationNumber = scan.nextInt();

            
            RoomNumber = ServerProcessor.SearchRoomCorrespondingToReservationNumber(ReservationNumber);
            if(RoomNumber == -1) {
                System.out.println("Your reservation number is not found.");
                TellUserToRebook();
            } else {
                details = ServerProcessor.FetchOnlineReservationDetails();
                ShowOnlineReservationDetails();
            }
        }catch(InputMismatchException e){
            System.out.println("Invalid Reservation format. Please enter integer value.");
            ReceiveReservationNo();
        }
        
	}	
	public static void TellRoomNumber(){
        
        System.out.println("Your room number is "+RoomNumber);
		
		ConductCheckin();
    }
    

	public static void TellFee() {
        try{
            System.out.println("Input Room Number:");
            Scanner scan = new Scanner(System.in);
            RoomNumber = scan.nextInt();
            fee = ServerProcessor.SearchFeeOfRoomNumber(RoomNumber);
            
            while(fee==-1){
                System.out.println("I believe this is not your room number, please re-enter your room number");
                RoomNumber = scan.nextInt();
                fee = ServerProcessor.SearchFeeOfRoomNumber(RoomNumber);
            }
            System.out.println("Please pay " + fee);
        }catch(InputMismatchException e){
            System.out.println("Invalid Room Number format. Please enter integer value.");
            TellFee();
        }
    }
    
	public static void ShowOnlineReservationDetails() {
        int confirm;

        try{
            Scanner scan = new Scanner(System.in);
            System.out.println("The online reservation details are "+details);
            System.out.println("Are these correct? (1 for yes) or (0 for no)");
            confirm = scan.nextInt();
            while((confirm!=1)&&(confirm!=0)){
                System.out.println("Please type (1 for yes) or (0 for no)");
                confirm = scan.nextInt();
            }

            if(confirm==1){
                TellRoomNumber();
            } else if (confirm==0){
                TellUserToRebook();
            }
        } catch (InputMismatchException e){
            System.out.println("Invalid answer. (1 for yes) or (0 for no) ");
            ShowOnlineReservationDetails();
        }

    }

    public static void TellUserToRebook(){
        System.out.println("Please rebook.");
        System.exit(0);
    }
    

	public static void TerminateCheckOut() {
        ServerProcessor.UserHasLeft(RoomNumber);
        if (angryRobotStaff == true){
            System.out.println("Do not forget to pay us back.");
        }

        if(angryRobotStaff == false){
            System.out.println("Thank you for staying at "+HotelName+" "+location+"! We hope to see you again!");
        }
        
    }
    


    public static void ConductCheckin(){

        wifi = ServerProcessor.SeeWifiDetails();
        ServerProcessor.StartWifi(RoomNumber, wifi);
    }


    public static void ConductCheckout(){
        ServerProcessor.StartWifi(RoomNumber, false);
    }

}