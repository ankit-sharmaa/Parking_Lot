
import Exceptions.CarNotFoundException;
import Exceptions.InvalidInputException;
import Exceptions.ParkingLotNotExistException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static String[] parking = null;
    private static int current = 0;
    private static int cap = 0;

    public static void main(String[] args) {
        try {
            String fileName = args[0];
            FileInputStream stream = new FileInputStream(fileName);
            Scanner sc = new Scanner(stream);
            while (sc.hasNext()) {
                processInput(sc.next());
            }
        } catch (ParkingLotNotExistException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void processInput(String inputLine) throws ParkingLotNotExistException {
        try {
            String[] input = null;
            input = inputLine.split(" ");
            if (Constants.CREATE_LOT.equals(input[0])) {
                parking = createParkingLot(input[1]);
            } else if (Constants.PARK_CAR.equals(input[0])) {
                current = parkCar(parking, current, input);
            } else if (Constants.LEAVE_CAR.equals(input[0])) {
                current = leaveCar(parking, current, input);
            }
        } catch (CarNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String[] createParkingLot(String capacity) {
        try {
            int c = Integer.parseInt(capacity);
            return new String[c];
        } catch (Exception e) {
            System.out.println("Incorrect Capacity");
        }
        return null;
    }

    private static int parkCar(String[] parking, int i, String[] input) throws ParkingLotNotExistException, InvalidInputException {
        if (parking == null) {
            throw new ParkingLotNotExistException(Constants.PARKING_LOT_NOT_EXIST);
        }
        if (input.length == Constants.PARK_INPUT_SIZE) {
            throw new InvalidInputException("");
        }
        parking[i] = input[1];
        cap++;
        while (i < parking.length) {
            i++;
            if (parking[i] == null) {
                return i;
            }
        }
        return i;
    }

    private static int leaveCar(String[] parking, int i, String[] input) throws ParkingLotNotExistException, CarNotFoundException, InvalidInputException {
        if (parking == null) {
            throw new ParkingLotNotExistException(Constants.PARKING_LOT_NOT_EXIST);
        }
        if (input.length != Constants.LEAVE_INPUT_SIZE || !Helper.isInteger(input[Constants.LEAVE_HOUR_INDEX])) {
            throw new InvalidInputException("");
        }
        int j = Helper.getIndexOfCar(parking, input[Constants.LEAVE_PLATE_INDEX]);
        if (j == -1) {
            throw new CarNotFoundException(input[Constants.LEAVE_PLATE_INDEX] + " : " + Constants.CAR_NOT_FOUND);
        }
        parking[j] = null;
        cap--;
        if (j < i) {
            i = j;
        }
        return i;
    }
}
