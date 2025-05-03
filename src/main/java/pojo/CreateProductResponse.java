package pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductResponse {

    private String productId;
    private String message;

    public CreateProductResponse(){}

}
