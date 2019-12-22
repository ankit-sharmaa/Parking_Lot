package com.parking;

import com.parking.Exceptions.CarNotFoundException;
import com.parking.Exceptions.InvalidInputException;
import com.parking.Exceptions.ParkingLotFullException;
import com.parking.Exceptions.ParkingLotNotExistException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static String[] parking = null;
    public static int current = 0;
    public static int cap = 0;

    public static void main(String[] args) {
        try {
            String fileName = "";
            for(String s:args){
                fileName = fileName + s;
            }
            FileInputStream stream = new FileInputStream(fileName);
            Scanner sc = new Scanner(stream);
            while (sc.hasNext()) {
                processInput(sc.nextLine());
            }
        } catch (ParkingLotNotExistException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void processInput(String inputLine) throws ParkingLotNotExistException {
        try {
            String[] input = null;
            input = inputLine.split(" ");
            if (Constants.CREATE_LOT.equals(input[Constants.INDEX_ZERO])) {
                parking = createParkingLot(input[1]);
            } else if (Constants.PARK_CAR.equals(input[Constants.INDEX_ZERO])) {
                current = parkCar(parking, current, input);
            } else if (Constants.LEAVE_CAR.equals(input[Constants.INDEX_ZERO])) {
                current = leaveCar(parking, current, input);
            } else if (Constants.STATUS.equals(input[Constants.INDEX_ZERO])) {
                printStatus(parking);
            }
        } catch (CarNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        } catch (ParkingLotFullException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printStatus(String[] parking) {
        System.out.println(Constants.STATUS_HEADER);
        for (int i = 0; i < parking.length; i++) {
            if (parking[i] != null) {
                System.out.println((i + 1) + " " + parking[i]);
            }
        }
    }

    public static String[] createParkingLot(String capacity) throws InvalidInputException{
        try {
            int c = Integer.parseInt(capacity);
            System.out.println("Created parking lot with " + capacity + " slots");
            return new String[c];
        } catch (Exception e) {
            throw new InvalidInputException("Incorrect Capacity");
        }
    }

    public static int parkCar(String[] parking, int i, String[] input) throws ParkingLotNotExistException, InvalidInputException, ParkingLotFullException {
        if (parking == null) {
            throw new ParkingLotNotExistException(Constants.PARKING_LOT_NOT_EXIST);
        }
        if (input.length != Constants.PARK_INPUT_SIZE) {
            throw new InvalidInputException(Constants.INVALID_INPUT);
        }
        if (cap == parking.length) {
            throw new ParkingLotFullException(Constants.PARKING_LOT_FULL);
        }
        parking[i] = input[Constants.PARK_PLATE_INDEX];
        System.out.println("Allocated slot number:" + (i + 1));
        cap++;
        while (i < parking.length - 1) {
            i++;
            if (parking[i] == null) {
                return i;
            }
        }
        return i;
    }

    public static int leaveCar(String[] parking, int i, String[] input) throws ParkingLotNotExistException, CarNotFoundException, InvalidInputException {
        if (parking == null) {
            throw new ParkingLotNotExistException(Constants.PARKING_LOT_NOT_EXIST);
        }
        if (input.length != Constants.LEAVE_INPUT_SIZE || !Helper.isInteger(input[Constants.LEAVE_HOUR_INDEX])) {
            throw new InvalidInputException(Constants.INVALID_INPUT);
        }
        int j = Helper.getIndexOfCar(parking, input[Constants.LEAVE_PLATE_INDEX]);
        if (j == -1) {
            throw new CarNotFoundException(Constants.REGISTRATION_NUMBER + Constants.BLANK + input[Constants.LEAVE_PLATE_INDEX] + Constants.BLANK + Constants.NOT_FOUND);
        }
        parking[j] = null;
        System.out.println(Constants.REGISTRATION_NUMBER + Constants.BLANK + input[Constants.LEAVE_PLATE_INDEX] + " with Slot Number " + (i + 1) + " is free with Charge " + getParkingChanges(input[Constants.LEAVE_HOUR_INDEX]));
        cap--;
        if (j < i) {
            i = j;
        }
        return i;
    }

    public static int getParkingChanges(String s) {
        int hr = Integer.parseInt(s);
        if (hr <= 2) {
            return Constants.FIXED_PARKING_CHARGE;
        } else {
            hr -= 2;
            return Constants.FIXED_PARKING_CHARGE + Constants.HOURLY_PARKING_CHARGE * hr;
        }
    }
}
