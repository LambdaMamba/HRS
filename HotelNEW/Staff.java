import java.util.InputMismatchException;
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
    public static boolean angryStaff = false;

    

    public static void StartCheckin(){
        ReceiveReservationNo(); 

    }

    public static void StartCheckout(){
        TellFee();
        ReceivePayment();


        ConductCheckin();
        TerminateCheckOut();

    }

    public static void ReceivePayment(){
        int userFee;
        try{
            Scanner scan = new Scanner(System.in);

            userFee = scan.nextInt();
            if(userFee < fee){
                System.out.println("Not enough money!");
                angryStaff = true;
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

            
            RoomNumber = RoomDatabaseServer.SearchRoomCorrespondingToReservationNumber(ReservationNumber);
            if(RoomNumber == -1) {
                System.out.println("Your reservation number is not found.");
                TellUserToRebook();
            } else {
                details = RoomDatabaseServer.FetchOnlineReservationDetails();
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
            fee = RoomDatabaseServer.SearchFeeOfRoomNumber(RoomNumber);
            
            while(fee==-1){
                System.out.println("I believe this is not your room number, please re-enter your room number");
                RoomNumber = scan.nextInt();
                fee = RoomDatabaseServer.SearchFeeOfRoomNumber(RoomNumber);
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
        if(angryStaff == false){
            System.out.println("Thank you for staying at Bilton! We hope to see you again!");
        } else if (angryStaff == true){
            System.out.println("Do not forget to pay us back.");
        }
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