public class Hotel extends HotelCompany{

    public static String location;
    public static int NumberOfRooms;

    public static void HotelChain(int i){
        if(i==1){
            location = "Shinjuku";
            NumberOfRooms = 30;
        } else if (i==2){
            location = "Ikebukuro";
            NumberOfRooms = 25;
        } else if (i==3){
            location = "Nishi Waseda";
            NumberOfRooms = 20;
        } else if (i==4){
            location = "Takadanobaba";
            NumberOfRooms = 10;
        }
    }
}