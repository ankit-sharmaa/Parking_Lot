import com.parking.Exceptions.CarNotFoundException;
import com.parking.Exceptions.InvalidInputException;
import com.parking.Exceptions.ParkingLotFullException;
import com.parking.Exceptions.ParkingLotNotExistException;
import com.parking.Helper;
import com.parking.Main;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    private String[] parking = null;
    private String[] parkingFull = null;
    private String[] inputPark = null;
    private String[] inputLeave = null;

    @Before
    public void setUp(){
        parking = new String[6];
        parking[0] = "DL-2-1234";
        parking[2] = "DL-2-1235";
        parking[3] = "DL-2-1236";

        parkingFull = new String[1];
        parkingFull[0] = "DL-2-1234";

        inputPark = new String[2];
        inputPark[0] = "park";
        inputPark[1] = "DL-2-1212";

        inputLeave = new String[3];
        inputLeave[0] = "leave";
        inputLeave[1] = "DL-2-1234";
        inputLeave[2] = "5";
    }

    @Test
    public void isIntTest(){
        boolean result = Helper.isInteger("1");
        assert result;
    }

    @Test
    public void isIntTest2(){
        boolean result = Helper.isInteger("one");
        assert !result;
    }

    @Test
    public void getIndexOfCarTest(){
        int result = Helper.getIndexOfCar(parking,"DL-2-1236");
        assert result==3;
    }

    @Test
    public void getIndexOfCarTest2(){
        int result = Helper.getIndexOfCar(parking,"DL-2-1111");
        assert result==-1;
    }

    @Test
    public void parkCarTest() throws InvalidInputException, ParkingLotFullException, ParkingLotNotExistException {
        int result = Main.parkCar(parking,1,inputPark);
        assert 4==result;
        assert inputPark[1].equals(parking[1]);

    }

    @Test(expected = ParkingLotFullException.class)
    public void parkCarTest2() throws InvalidInputException, ParkingLotFullException, ParkingLotNotExistException {
        Main.cap = 1;
        Main.parkCar(parkingFull,1,inputPark);
    }

    @Test(expected = InvalidInputException.class)
    public void parkCarTest3() throws InvalidInputException, ParkingLotFullException, ParkingLotNotExistException {
        Main.cap = 1;
        inputPark = new String[0];
        Main.parkCar(parkingFull,1,inputPark);
    }

    @Test(expected = ParkingLotNotExistException.class)
    public void parkCarTest4() throws InvalidInputException, ParkingLotFullException, ParkingLotNotExistException {
        Main.parkCar(null,1,inputPark);
    }

    @Test
    public void leaveCarTest() throws InvalidInputException, ParkingLotNotExistException, CarNotFoundException {
        int result = Main.leaveCar(parking,1,inputLeave);
        assert parking[0]==null;
        assert result==0;
    }

    @Test(expected = ParkingLotNotExistException.class)
    public void leaveCarTest2() throws InvalidInputException, ParkingLotNotExistException, CarNotFoundException {
        Main.leaveCar(null,1,inputLeave);
    }

    @Test(expected = InvalidInputException.class)
    public void leaveCarTest4() throws InvalidInputException, ParkingLotNotExistException, CarNotFoundException {
        Main.leaveCar(parking,1,inputPark);
    }

    @Test(expected = CarNotFoundException.class)
    public void leaveCarTest3() throws InvalidInputException, ParkingLotNotExistException, CarNotFoundException {
        inputLeave[1] = "DL-1-11111";
        Main.leaveCar(parking,1,inputLeave);
    }

    @Test
    public void getParkingChangesTest(){
        int result = Main.getParkingChanges("1");
        assert result==10;
    }

    @Test
    public void getParkingChangesTest2(){
        int result = Main.getParkingChanges("5");
        assert result==40;
    }

    @Test
    public void createParkingLotTest() throws InvalidInputException {
        String[] result = Main.createParkingLot("10");
        assert result.length==10;
    }

    @Test(expected = InvalidInputException.class)
    public void createParkingLotTest2() throws InvalidInputException {
        String[] result = Main.createParkingLot("Ten");
        assert result.length==10;
    }

    @Test
    public void processInputTest() throws ParkingLotNotExistException {
        Main.parking = parking;
        Main.current = 1;
        Main.cap = 4;
        Main.processInput("park DL-1-1111");
        assert Main.parking[1].equals("DL-1-1111");
    }

    @Test
    public void processInputTest2() throws ParkingLotNotExistException {
        Main.parking = parking;
        Main.current = 1;
        Main.cap = 4;
        Main.processInput("leave DL-2-1235 5");
        assert Main.parking[2]==null;
    }

    @Test(expected = ParkingLotNotExistException.class)
    public void processInputTest3() throws ParkingLotNotExistException {
        Main.parking = null;
        Main.current = 1;
        Main.cap = 4;
        Main.processInput("leave DL-1-1111");
    }

    @Test
    public void processInputTest4() throws ParkingLotNotExistException {
        Main.parking = null;
        Main.current = 0;
        Main.cap = 0;
        Main.processInput("create_parking_lot 4");
        assert Main.parking.length==4;
    }

}
