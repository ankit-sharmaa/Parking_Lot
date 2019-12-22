### **PARKING LOT**

It is an application that manage a parking lot of capacity C. In the when ever a new car comes for parking. The car a assigned to a slot nearest to the parking entry gate.
At the exit the customer returns
the ticket with the time the car was parked in the lot, which then marks the slot they
were using as being available. Total parking charge is calculated as per the
parking time. Charge applicable is $10 for first 2 hours and $10 for every additional
hour.

The application accept a path to inupt file(which contain all the commands of parking) and output the result.

Following are the valid command 
1. Create parking lot of size n : create_parking_lot
{capacity}
2. Park a car : park {car_number}
3. Remove(Unpark) car from : leave {car_number} {hours}
4. Print status of parking slot : status

*capacity and hours should be numbers.

###### **System requirement to run the application**

-Maven 3.6.0 or higher

-Java 8 or higher

##### **Steps to run the application**
1. Copy the root dir to any machine Java and Maven as per system requirment.
2. Execute bin/setup to add dependency and compile code.
3. Execute bin/parking_lot file_inputs.txt to test the application.