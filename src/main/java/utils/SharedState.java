package utils;

public class SharedState {

    private static SharedState instance;
    public String token;
    public String userID;
    public String productID;
    public String orderId;
    public String productOrderId;

    public SharedState() {}

    public static SharedState getInstance() {
        if (instance == null) {
            instance = new SharedState();
        }
        return instance;
    }
}
