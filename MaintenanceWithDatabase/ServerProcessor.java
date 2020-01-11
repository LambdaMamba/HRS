import java.util.Random;
import java.lang.Math;
import java.lang.Object;
import java.util.*; 


public class ServerProcessor extends Hotel{
    private static int NumberOfAvailableRooms = 0;


    private static int NumberOfOnlineReservationInstances = 1;

    private static int SelectedID;

    private static int RoomForReservation;

    private static int tempID;

    private static int ReservationNumber;

    private static boolean userIsChosen;

    private static OnlineReservationDetails details = new OnlineReservationDetails();


    private static List<Room> RoomInstances = new ArrayList<Room>();
    public static void RemoveReservation(){
        Database.UpdateAvailableRoomListRemoveReservation();
        
    }


    public static void RoomAvailable(){
        NumberOfOnlineReservationInstances = 1;
        Database.InstantiateRoomAvailable();
        for (int k=1; k<=NumberOfRooms; k++){
            Database.AddToRoomFee(k);
        }
        
    }

    public static void TwoUserWantOneAvailableRoom(){
        NumberOfOnlineReservationInstances = 2;
        Database.InstantiateTwoUserWantOneAvailableRoom();
        for (int k=1; k<=NumberOfRooms; k++){
            Database.AddToRoomFee(k);
        }
    }

    public static void RoomFull(){
        NumberOfOnlineReservationInstances = 1;
        Database.InstantiateRoomFull();
        for (int k=1; k<=NumberOfRooms; k++){
            Database.AddToRoomFee(k);
        }
    }

    
    public static int SearchAvailableRoom(int userID){
        int k;
        //The rooms are instantiated, and fee of each room is assigned when this function is called
        for (k=1; k<=NumberOfRooms; k++){
            RoomInstances.add(new Room(k, false, 0));
        }

        int i = 0;
        int j;
        tempID = userID;
        
        NumberOfAvailableRooms = Database.SearchAvailableRoomList();

        return NumberOfAvailableRooms; 
        
    }

    public static void CheckHowManyWantToReserveRoom(){

        if(NumberOfAvailableRooms<1){
            OnlineReservation.UserHereIsNotSelected();
            Database.AddToWaitingList(tempID);
        } else if (NumberOfOnlineReservationInstances > NumberOfAvailableRooms){
            System.out.println("[Server "+location+"]: # of users who want to reserve: " + NumberOfOnlineReservationInstances);
            userIsChosen = RandomlySelectOneUser(NumberOfOnlineReservationInstances);
            if(userIsChosen == true){
                System.out.println("[Server "+location+"]: User is chosen");
                OnlineReservation.UserHereIsSelected();
                SelectedID = tempID;
            } else if (userIsChosen == false){
                System.out.println("[Server "+location+"]: User is not chosen");
                OnlineReservation.UserHereIsNotSelected();
                Database.AddToWaitingList(tempID);
            }
              
        } else if (NumberOfOnlineReservationInstances == 1){

            OnlineReservation.UserHereIsSelected();
            SelectedID = tempID;
        }
    }

    public static boolean RandomlySelectOneUser(int NumberOfOnlineReservationInstances){
        int dice = (int)(Math.random()*NumberOfOnlineReservationInstances);
        System.out.println("[Server "+location+"] Dice: "+dice);
        if(dice == 1){
            return true;
        }else{
            return false;
        }

        
    } 

    

    public static int GenerateReservationNumber(){
        String first = String.valueOf(SelectedID);

        StringBuilder strNum = new StringBuilder();
        
        int[] generated = new int[6];

        Random rand = new Random();
        int i = 0;
        int max = 9;

        for(i = 1; i<6; i++){

            generated[i] = rand.nextInt((max-1)+1)+1;
            strNum.append(generated[i]);
        }

        int resnum = Integer.parseInt(strNum.toString());

        ReservationNumber = resnum;
        return resnum;
    }


    public static void RegisterReservation(boolean Wifi, String Date){
      
        details.register(ReservationNumber, tempID, Date, Wifi);
        RoomForReservation = Database.UpdateAvailableRoomList(ReservationNumber);
        FetchOnlineReservationDetails();

    }

  


    public static void UserHasLeft(int RN){
        Database.RemoveFromReservation(RN);
    }




    public static int SearchRoomCorrespondingToReservationNumber(int checkinReservationNumber){
        int checkinRoomNumber;
        checkinRoomNumber = Database.SearchReservationDictionary(checkinReservationNumber);
        return checkinRoomNumber;

    }


    public static String FetchOnlineReservationDetails(){
        String words = details.GiveDetails();

        return words;

    }

    public static boolean SeeWifiDetails(){
        boolean wifidetail = details.ShowWifiDetail();
        return wifidetail;
    }



    public static int SearchFeeOfRoomNumber(int RoomNumber){
        int WifiUsage = 0;
        boolean wifitruth;
        int fee;
        int basefee;

        if(RoomNumber != RoomForReservation){
            return -1;
        }
        
        wifitruth = RoomInstances.get(RoomNumber-1).WifiTrue();

        if(wifitruth==true){
            WifiUsage = RoomInstances.get(RoomNumber-1).ShowWifiUsage();
            System.out.println("[Server "+location+"] Wifi Usage (Minuites): "+WifiUsage);
            //Wifi is 1 yen per minuite

        }

        basefee = Database.SearchRoomFeeDictionary(RoomNumber);

        fee = basefee + WifiUsage;

        return fee;
        


    }

    public static void StartWifi(int RoomNumber, boolean wifi){
        RoomInstances.get(RoomNumber-1).UpdateWifiStatus(wifi);
    }
}