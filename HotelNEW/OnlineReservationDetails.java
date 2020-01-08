public class OnlineReservationDetails{
    static int ReservationNumberDetails;
    static int IDDetails;
    static int DateDetails;
    static boolean WifiDetails; 

    public OnlineReservationDetails(){
        

    }

    public static void register(int ReservationNumber, int ID, int Date, boolean Wifi){
        ReservationNumberDetails = ReservationNumber;
        IDDetails = ID;
        DateDetails = Date;
        WifiDetails = Wifi;
    }

    public static String GiveDetails(){
        return "ReservationNumber: "+ReservationNumberDetails+" userID: "+IDDetails+" Date:"+DateDetails+" Wifi: "+WifiDetails;

    }

    public static boolean ShowWifiDetail(){
        return WifiDetails;
    }
}