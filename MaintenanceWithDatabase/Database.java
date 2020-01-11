import java.util.Random;
import java.lang.Math;
import java.lang.Object;
import java.util.*; 

public class Database extends Hotel{


    private static Map<Integer, Integer> ReservationDictionary = new HashMap<Integer, Integer>();
    private static Map<Integer, Integer> RoomFeeDictionary = new HashMap<Integer, Integer>();

    private static int[] FeeList = new int[NumberOfRooms];
    private static int[] RoomList = new int[NumberOfRooms];
    private static int[] AvailableRoomList= new int[NumberOfRooms];

    private static int RoomForReservation;

    private static Queue<Integer> WaitingList = new LinkedList<>();

    private static int ReservationNumber;

    private static int NumberOfAvailableRooms;


    public static void UpdateAvailableRoomListRemoveReservation(){
        AvailableRoomList[RoomForReservation-1] = RoomForReservation;
        ReservationDictionary.remove(ReservationNumber);
        System.out.println("[Database "+location+"] New Available room list" +Arrays.toString(AvailableRoomList));
        System.exit(0);
    }


    public static int SearchRoomFeeDictionary(int RoomNumber){
        int basefee;
        basefee = RoomFeeDictionary.get(RoomNumber);
        System.out.println("[Database "+location+"] Base Fee: "+basefee);
        return basefee;
    }


    public static int SearchReservationDictionary(int checkinReservationNumber){
        int checkinRoomNumber;
        try{
            checkinRoomNumber = ReservationDictionary.get(checkinReservationNumber);
            return checkinRoomNumber;
        }catch(Exception e){
            return -1;
        }
    }


    public static void RemoveFromReservation(int RN){
        AvailableRoomList[RN-1] = RN;
        ReservationDictionary.remove(ReservationNumber);
        System.out.println("[Database "+location+"] New Available room list" +Arrays.toString(AvailableRoomList));
    }

    public static int UpdateAvailableRoomList(int Reserve){
        ReservationNumber = Reserve;
        int i = 0;
        System.out.println("[Database "+location+"] Old Available room list" +Arrays.toString(AvailableRoomList));
        while(true){
            if(AvailableRoomList[i] != -1){
                RoomForReservation = AvailableRoomList[i];
                AvailableRoomList[i] = -1;
                ReservationDictionary.put(ReservationNumber, RoomForReservation);
                System.out.println("[Database "+location+"] Room number given to this reservation number: " + ReservationDictionary.get(ReservationNumber));
       
                System.out.println("[Database "+location+"] New Available room list" +Arrays.toString(AvailableRoomList));
                

                break;


            }
            i++;
        }
        return RoomForReservation;

    }


    public static void AddToWaitingList(int userID){
        WaitingList.add(userID);
        System.out.println("[Database "+location+"] Current waiting list queue: "+WaitingList);
        System.exit(0);
    }


    public static int SearchAvailableRoomList(){
        NumberOfAvailableRooms = 0;
        int j;
        
        for(j=0; j<NumberOfRooms; j++){
            if(AvailableRoomList[j] != -1){
                NumberOfAvailableRooms++;
            }
        }
        return NumberOfAvailableRooms;
    }


    public static void AddToRoomFee(int k){
        RoomFeeDictionary.put(k, 10000+(k*1550));
    }

    


    public static void InstantiateRoomAvailable(){
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

    public static void InstantiateTwoUserWantOneAvailableRoom(){
        Random rand = new Random();
 
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

    public static void InstantiateRoomFull(){

        WaitingList.add(99999);
        WaitingList.add(88888);
        for(int i=0; i<AvailableRoomList.length; i++){
            AvailableRoomList[i] = -1;
        }
    }
}