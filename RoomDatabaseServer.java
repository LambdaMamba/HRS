import java.util.Random;
import java.lang.Math;
import java.lang.Object;
import java.util.*; 

// import org.apache.commons.lang3.RandomStringUtils;

public class RoomDatabaseServer{
    private static int NumberOfAvailableRooms = 0;
    private static int NumberOfRooms = 6;
    private static int[] AvailableRoomList = {0, 2, 3, 0, 5, 6};
    // private static int[] ReservationList = {0, 0, 0, 0, 0, 0};
    // private static int[] ReservationIDList = {0, 0, 0, 0, 0, 0};

    private static Map<Integer, Integer> ReservationDictionary = new HashMap<Integer, Integer>();
    private static Map<Integer, Integer> RoomFeeDictionary = new HashMap<Integer, Integer>();
    // private static int[] AvailableRoomList = new int[NumberOfRooms];
    // private static int[] WaitingList = new int[NumberOfRooms*2];
    private static int[] FeeList = new int[NumberOfRooms];
    private static int[] RoomList = new int[NumberOfRooms];

    private static int NumberOfOnlineReservationInstances = 1;
    private static int[] IDlistOfUsersWhoAreOnOnlineReservation = new int[NumberOfOnlineReservationInstances];

    private static int SelectedID;
    private static int[] NotSelectedIDs = new int[10];

    private static String HotelChain;

    private static int RoomForReservation;

    private static int tempID;

    private static int ReservationNumber;

    private static boolean userIsChosen;

    private static Queue<Integer> WaitingList = new LinkedList<>();

    // private static boolean Wifi;
    private static int WifiUsage;
    private static int Date = 123;

    private static OnlineReservationDetails details = new OnlineReservationDetails();


    private static List<Room> RoomInstances = new ArrayList<Room>();


    public static void RoomAvailable(){
        NumberOfOnlineReservationInstances = 1;
    }

    public static void TwoUserWantOneAvailableRoom(){
        NumberOfOnlineReservationInstances = 2;
        WaitingList.add(99999);
        WaitingList.add(88888);
        for(int i=2; i<AvailableRoomList.length; i++){
            AvailableRoomList[i] = 0;
        }
    }

    public static void RoomFull(){
        NumberOfOnlineReservationInstances = 1;
        WaitingList.add(99999);
        WaitingList.add(88888);
        for(int i=0; i<AvailableRoomList.length; i++){
            AvailableRoomList[i] = 0;
        }
    }

    
    public static int SearchAvailableRoom(int userID){
        int k;
        //The rooms are instantiated, and fee of each room is assigned when this function is called
        for (k=1; k<=NumberOfRooms; k++){
            RoomFeeDictionary.put(k, 10000+(k*1550));
            RoomInstances.add(new Room(k, false, 0));
            // Room room = new Room(k, false, 0);
        }

        int i = 0;
        int j;
        tempID = userID;
        NumberOfAvailableRooms = 0;
        
        for(j=0; j<NumberOfRooms; j++){
            if(AvailableRoomList[j] != 0){
                NumberOfAvailableRooms++;
            }
        }

        // System.out.println("[Room Database] Number Of Available Rooms: " + NumberOfAvailableRooms);
        return NumberOfAvailableRooms;
        
    }

    public static void CheckHowManyWantToReserveRoom(){

        if(NumberOfAvailableRooms<1){
            OnlineReservation.UserHereIsNotSelected();
            AddToWaitingList(tempID);
        } else if (NumberOfOnlineReservationInstances > NumberOfAvailableRooms){
            System.out.println("[Server]: # of users who want to reserve: " + NumberOfOnlineReservationInstances);
            userIsChosen = RandomlySelectOneUser(NumberOfOnlineReservationInstances);
            if(userIsChosen == true){
                System.out.println("[Server]: User is chosen");
                OnlineReservation.UserHereIsSelected();
                SelectedID = tempID;
            } else if (userIsChosen == false){
                System.out.println("[Server]: User is not chosen");
                OnlineReservation.UserHereIsNotSelected();
                AddToWaitingList(tempID);
            }
              
        } else if (NumberOfOnlineReservationInstances == 1){
            // GenerateReservationNumber();
            // System.out.println("Reservation number" + ReservationNumber);
            OnlineReservation.UserHereIsSelected();
            SelectedID = tempID;
        }
    }

    public static boolean RandomlySelectOneUser(int NumberOfOnlineReservationInstances){
        int dice = (int)(Math.random()*NumberOfOnlineReservationInstances);
        System.out.println("[Server] Dice: "+dice);
        if(dice == 1){
            return true;
        }else{
            return false;
        }

        
    } 

    public static void AddToWaitingList(int userID){
        WaitingList.add(userID);
        System.out.println("[Server] Current waiting list queue: "+WaitingList);
        System.exit(0);
    }
    

    public static int GenerateReservationNumber(){
        String first = String.valueOf(SelectedID);

        StringBuilder strNum = new StringBuilder();
        
        int[] generated = new int[11];


        //int[] randomnumber = new int[10];
        Random rand = new Random();
        int i = 0;
        int max = 9;

        for(i = 1; i<10; i++){
            // if(i==0 || i==9){
            //     generated[i] = rand.nextInt((max-1)+1)+1;

            // }
            generated[i] = rand.nextInt((max-1)+1)+1;
            strNum.append(generated[i]);
        }

        // for(int num: generated){
        //     strNum.append(num);
        // }
        int resnum = Integer.parseInt(strNum.toString());

        ReservationNumber = resnum;
        return resnum;
    }


    public static void RegisterReservation(boolean Wifi, int Date){
      
        details.register(ReservationNumber, tempID, Date, Wifi);
        UpdateAvailableRoomList();
        FetchOnlineReservationDetails();

        //Pretend staff
        RoomDatabaseServer.StartWifi(2, Wifi);

    }

    public static void UpdateAvailableRoomList(){
        int i = 0;
        System.out.println("[Server] Old Available room list" +Arrays.toString(AvailableRoomList));
        while(true){
            if(AvailableRoomList[i] != 0){
                RoomForReservation = AvailableRoomList[i];
                AvailableRoomList[i] = 0;
                System.out.println("[Server] RoomForReservation: "+ RoomForReservation);
                System.out.println("[Server] New Available room list" +Arrays.toString(AvailableRoomList));
                ReservationDictionary.put(ReservationNumber, RoomForReservation);

                System.out.println("[Server] Room number given to this reservation number: " + ReservationDictionary.get(ReservationNumber));
                // if(ReservationDictionary.get(13)==null){
                //     System.out.println("nah");
                // }
                break;

                // ReservationList[i] = AvailableRoomList[i];
                // ReservationIDList[i] = ReservationNumber;
                // System.out.println(Arrays.toString(ReservationList));
                // System.out.println(Arrays.toString(ReservationIDList));   
            }
            i++;
        }


    }


    public static void UserHasLeft(int RN){
        AvailableRoomList[RN] = RN;
        ReservationDictionary.remove(ReservationNumber);
        System.out.println("[Server] New Available room list" +Arrays.toString(AvailableRoomList));
    }




    public static int SearchRoomCorrespondingToReservationNumber(int checkinReservationNumber){
        int checkinRoomNumber;
        try{
            checkinRoomNumber = ReservationDictionary.get(checkinReservationNumber);
            return checkinRoomNumber;
        }catch(Exception e){
            return -1;
        }

    }


    public static String FetchOnlineReservationDetails(){
        String words = details.GiveDetails();
        // System.out.println("[Server] OnlineReservationDetails: " +words);
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
        wifitruth = RoomInstances.get(RoomNumber-1).WifiTrue();

        if(wifitruth==true){
            WifiUsage = RoomInstances.get(RoomNumber-1).ShowWifiUsage();
            System.out.println("[Server] Wifi Usage (Minuites): "+WifiUsage);
            //Wifi is 1 yen per minuite

        }

        basefee = RoomFeeDictionary.get(RoomNumber);

        System.out.println("[Server] Base Fee: "+basefee);

        fee = basefee + WifiUsage;

        return fee;
        


    }

    public static void StartWifi(int RoomNumber, boolean wifi){
        RoomInstances.get(RoomNumber-1).UpdateWifiStatus(wifi);
    }
}