
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
                    current = parkCar(parking,current,cap,input);
                    cap++;
                } else if (Constants.LEAVE_CAR.equals(input[0])) {
                    current = leaveCar(parking,current,cap,input);
                    cap--;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Input file not found");
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

    private static int parkCar(String[] parking,int i,int capacity,String[] input){
        if(parking!=null){
            if(input.length==Constants.PARK_INPUT_SIZE){
                parking[i] = input[1];
                while(i<parking.length){
                    i++;
                    if(parking[i]==null){
                        return i;
                    }
                }
            } else {
                System.out.println("Invalid Input For Car Park");
            }
        }
        return 0;
    }

    private static int leaveCar(String[] parking,int i,int capacity,String[] input){
        if(parking!=null){
            if(input.length==Constants.LEAVE_INPUT_SIZE && isInteger(input[Constants.LEAVE_HOUR_INDEX])){
                int j = getIndexOfCar(parking,input[Constants.LEAVE_PLATE_INDEX]);
                parking[j] = null;
                if(j<i){
                    i = j;
                }
                return i;
            } else {
                System.out.println("Invalid Input For Leave Park");
            }
        }
        return 0;
    }

    private static int getIndexOfCar(String[] parking, String s) {
        for(int i =0; i<parking.length; i++){
            if(s.equals(parking[i])){
                return i;
            }
        }
        return -1;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
