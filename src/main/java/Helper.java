class Helper {
     static int getIndexOfCar(String[] parking, String s) {
        for(int i =0; i<parking.length; i++){
            if(s.equals(parking[i])){
                return i;
            }
        }
        return -1;
    }

    static boolean isInteger(String s) {
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
