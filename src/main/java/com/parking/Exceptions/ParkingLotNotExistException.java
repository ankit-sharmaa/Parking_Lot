package com.parking.Exceptions;

public class ParkingLotNotExistException extends Exception{
    public ParkingLotNotExistException(String message){
        super(message);
    }
}
