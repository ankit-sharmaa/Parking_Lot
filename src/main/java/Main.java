
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            String[] parking = null;
            int current = 0;
            int cap = 0;
            String fileName = args[0];
            FileInputStream stream = new FileInputStream(fileName);
            Scanner sc = new Scanner(stream);
            while(sc.hasNext()){
                String[] input = null;
                String inputLine = sc.next();
                input = inputLine.split(" ");
                if (Constants.CREATE_LOT.equals(input[0])) {
                    parking = createParkingLot(input[1]);
                } else if (Constants.PARK_CAR.equals(input[0])) {
                    current = parkCar(parking,current);
                    cap++;
                } else if (Constants.LEAVE_CAR.equals(input[0])) {
                    current = leaveCar(parking,current,0);
                    cap--;
                }
            }
        } catch (FileNotFoundException e){

        }
    }

    private static String[] createParkingLot(String capacity){
        try {
            int c = Integer.parseInt(capacity);
            return new String[c];
        } catch (Exception e){
            System.out.println("Incorrect Capacity");
        }
        return null;
    }

    private static int parkCar(String[] parking,int i){
        return 0;
    }

    private static int leaveCar(String[] parking,int i,int hr){
        return 0;
    }
}
