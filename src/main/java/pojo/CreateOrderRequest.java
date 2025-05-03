package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    private List<Order> orders = new ArrayList<Order>();

    @Getter
    @Setter
    public static class Order{
        private String country;
        private String productOrderedId;
    }

    public CreateOrderRequest(){}

}
