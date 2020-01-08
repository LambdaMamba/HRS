import java.lang.Math;
import java.util.Random;

public class Room{
    int RoomNumber;
    static boolean RoomWifi;
    static int RoomWifiUsage;

    public Room(int Number, boolean Wifi, int WifiUsage){
        RoomNumber = Number;
        RoomWifi = Wifi;
        RoomWifiUsage = WifiUsage;
    }

    public static int ShowWifiUsage(){
        int max=1440;
        int min=100;
        Random rand = new Random();
        RoomWifiUsage = rand.nextInt((max-min)+1)+min;
        return RoomWifiUsage;

    }

    public static boolean WifiTrue(){
        if (RoomWifi==false){
            return false;
        } else if (RoomWifi==true){
            return true;
        }
        return false;
    }


    public static void UpdateWifiStatus(boolean updateWifi){
        if(updateWifi == false){
            RoomWifi = false;
        } else if(updateWifi == true){
            RoomWifi = true;
        }
    }

}