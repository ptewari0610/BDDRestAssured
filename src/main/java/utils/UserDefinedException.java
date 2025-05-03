package utils;

public class UserDefinedException  extends Exception{

    /** Parameterized constructor calling super class's parameterized constructor
     @param msg This is user defined exception message
     */
    public UserDefinedException(String msg){
        super(msg);
    }
}
