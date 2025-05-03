package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderResponse {

    private List<String> orders;
    private List<String> productOrderId;
    private String message;

    public CreateOrderResponse(){}

}
