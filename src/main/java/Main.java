
import Exceptions.ParkingLotNotExistException;

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
        } catch(ParkingLotNotExistException e){
            System.out.println(e.getMessage());
        }catch (FileNotFoundException e){
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

    private static int parkCar(String[] parking,int i,int capacity,String[] input) throws ParkingLotNotExistException{
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
        throw new ParkingLotNotExistException(Constants.PARKING_LOT_NOT_EXIST);
    }

    private static int leaveCar(String[] parking,int i,int capacity,String[] input) throws ParkingLotNotExistException{
        if(parking!=null){
            if(input.length==Constants.LEAVE_INPUT_SIZE && Helper.isInteger(input[Constants.LEAVE_HOUR_INDEX])){
                int j = Helper.getIndexOfCar(parking,input[Constants.LEAVE_PLATE_INDEX]);
                if(j==-1){
                    System.out.println("Car is not parked");
                }
                parking[j] = null;
                if(j<i){
                    i = j;
                }
                return i;
            } else {
                System.out.println("Invalid Input For Leave Park");
            }
        }
        throw new ParkingLotNotExistException(Constants.PARKING_LOT_NOT_EXIST);
    }
}
