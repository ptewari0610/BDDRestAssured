package pojo;

import io.cucumber.java.eo.Se;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsResponse {

    private Data data;
    private String message;

    @Getter
    @Setter
    public static class Data{
        private String _id;
        private String orderById;
        private String orderBy;
        private String productOrderedId;
        private String productName;
        private String country;
        private String productDescription;
        private String productImage;
        private String orderPrice;
        private Integer __v;

    }

    public OrderDetailsResponse(){}
}
