import java.util.Random;
import java.lang.Math;
import java.lang.Object;
import java.util.*; 


public class ServerProcessor extends Hotel{
    private static int NumberOfAvailableRooms = 0;

    //The AvailableRoomList length is decided upon the NumberOfRooms
    private static int[] AvailableRoomList= new int[NumberOfRooms];


    private static Map<Integer, Integer> ReservationDictionary = new HashMap<Integer, Integer>();
    private static Map<Integer, Integer> RoomFeeDictionary = new HashMap<Integer, Integer>();

    private static int[] FeeList = new int[NumberOfRooms];
    private static int[] RoomList = new int[NumberOfRooms];

    private static int NumberOfOnlineReservationInstances = 1;
    private static int[] IDlistOfUsersWhoAreOnOnlineReservation = new int[NumberOfOnlineReservationInstances];

    private static int SelectedID;
    private static int[] NotSelectedIDs = new int[10];

    private static String ChainName;

    private static int RoomForReservation;

    private static int tempID;

    private static int ReservationNumber;

    private static boolean userIsChosen;

    private static Queue<Integer> WaitingList = new LinkedList<>();

    private static int WifiUsage;
    private static String Date;

    private static OnlineReservationDetails details = new OnlineReservationDetails();


    private static List<Room> RoomInstances = new ArrayList<Room>();








    public static void RoomAvailable(){
        NumberOfOnlineReservationInstances = 1;
        int dice; 
        int i;
       

        //Number of available rooms are instantiated here, 1/2 chance vacant, 1/2 chance occupied

        for(i=0; i<NumberOfRooms; i++){
            dice = (int)(Math.random()*2);
            if(dice == 1){
                AvailableRoomList[i] = i+1;
            }else{
                AvailableRoomList[i] = -1;
            }

        }
        
    }

    public static void TwoUserWantOneAvailableRoom(){
        Random rand = new Random();
        NumberOfOnlineReservationInstances = 2;
        WaitingList.add(99999);
        WaitingList.add(88888);
        int OpenRoom;

        //One Open room is randomly chosen, the others will be occupied

        OpenRoom = rand.nextInt((NumberOfRooms-1)+1)+1;

        for(int i=0; i<AvailableRoomList.length; i++){
            if((i+1)!=OpenRoom){
                AvailableRoomList[i] = -1;
            }else if ((i+1)==OpenRoom){
                AvailableRoomList[i] = OpenRoom;
            }
        }

    }

    public static void RoomFull(){
        NumberOfOnlineReservationInstances = 1;
        WaitingList.add(99999);
        WaitingList.add(88888);
        for(int i=0; i<AvailableRoomList.length; i++){
            AvailableRoomList[i] = -1;
        }
    }

    
    public static int SearchAvailableRoom(int userID){
        int k;
        //The rooms are instantiated, and fee of each room is assigned when this function is called
        for (k=1; k<=NumberOfRooms; k++){
            RoomFeeDictionary.put(k, 10000+(k*1550));
            RoomInstances.add(new Room(k, false, 0));
        }

        int i = 0;
        int j;
        tempID = userID;
        NumberOfAvailableRooms = 0;
        
        for(j=0; j<NumberOfRooms; j++){
            if(AvailableRoomList[j] != -1){
                NumberOfAvailableRooms++;
            }
        }
        return NumberOfAvailableRooms;
        
    }

    public static void CheckHowManyWantToReserveRoom(){

        if(NumberOfAvailableRooms<1){
            OnlineReservation.UserHereIsNotSelected();
            AddToWaitingList(tempID);
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
                AddToWaitingList(tempID);
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

    public static void AddToWaitingList(int userID){
        WaitingList.add(userID);
        System.out.println("[Server "+location+"] Current waiting list queue: "+WaitingList);
        System.exit(0);
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
        UpdateAvailableRoomList();
        FetchOnlineReservationDetails();

    }

    public static void UpdateAvailableRoomList(){
        int i = 0;
        System.out.println("[Server "+location+"] Old Available room list" +Arrays.toString(AvailableRoomList));
        while(true){
            if(AvailableRoomList[i] != -1){
                RoomForReservation = AvailableRoomList[i];
                AvailableRoomList[i] = -1;
                System.out.println("[Server "+location+"] RoomForReservation: "+ RoomForReservation);
                System.out.println("[Server "+location+"] New Available room list" +Arrays.toString(AvailableRoomList));
                ReservationDictionary.put(ReservationNumber, RoomForReservation);

                System.out.println("[Server "+location+"] Room number given to this reservation number: " + ReservationDictionary.get(ReservationNumber));

                break;


            }
            i++;
        }


    }


    public static void UserHasLeft(int RN){
        AvailableRoomList[RN-1] = RN;
        ReservationDictionary.remove(ReservationNumber);
        System.out.println("[Server "+location+"] New Available room list" +Arrays.toString(AvailableRoomList));
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

        basefee = RoomFeeDictionary.get(RoomNumber);

        System.out.println("[Server "+location+"] Base Fee: "+basefee);

        fee = basefee + WifiUsage;

        return fee;
        


    }

    public static void StartWifi(int RoomNumber, boolean wifi){
        RoomInstances.get(RoomNumber-1).UpdateWifiStatus(wifi);
    }
}