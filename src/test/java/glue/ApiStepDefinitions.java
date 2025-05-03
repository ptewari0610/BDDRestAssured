package glue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import pojo.*;
import utils.ConfigReader;
import utils.RequestSpecProvider;
import utils.ResponseSpecProvider;
import utils.SharedState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiStepDefinitions {

    private final SharedState sharedState; // Shared state object

    String loginSuccessMsg,productAddSuccessMsg,orderPlaceMsg;
    String _id,orderById,orderBy,productOrderedId,productName,country,productDescription,orderPrice,orderFetchedMsg;
    RequestSpecification reqLogin,addProduct,placeOrder,viewOrder,deleteOrder,deleteProduct;
    LoginResponse loginResponse;
    CreateProductResponse createProductResponse;
    CreateOrderRequest createOrderRequest = new CreateOrderRequest();
    CreateOrderResponse createOrderResponse;
    OrderDetailsResponse orderDetailsResponse;

    String baseURI = ConfigReader.getInstance().fetchValueFromConfig("baseuri");
    RequestSpecification basicSpec = RequestSpecProvider.getBasicSpec(baseURI);
    ResponseSpecification resSpecOne = ResponseSpecProvider.getBasicResponseSpec(HttpStatus.SC_OK);
    ResponseSpecification resSpecTwo = ResponseSpecProvider.getBasicResponseSpec(HttpStatus.SC_CREATED);

    // Constructor to inject SharedState
    public ApiStepDefinitions(SharedState sharedState) {
        this.sharedState = SharedState.getInstance();
    }

    /* ECOMMERCE APPLICATION LOGIN */
    @Given("User login into the ecommerce application using POST API Request")
    public void userLoginIntoTheApplicationUsingPOSTApiRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(ConfigReader.getInstance().fetchValueFromConfig("userEmail"));
        loginRequest.setUserPassword(ConfigReader.getInstance().fetchValueFromConfig("userPassword"));
        reqLogin = given(basicSpec).body(loginRequest);
    }

    @Then("User should get correct message in HTTP Response")
    public void userShouldGetCorrectHTTPResponse() {
        loginResponse = reqLogin.when().post("api/ecom/auth/login").then().spec(resSpecOne).extract().as(LoginResponse.class);
        sharedState.token = loginResponse.getToken();
        System.out.println("Token in login request:"+sharedState.token);
        sharedState.userID = loginResponse.getUserId();
        loginSuccessMsg = loginResponse.getMessage();
        Assert.assertEquals(loginSuccessMsg,"Login Successfully","Login unsuccessful response message");
    }



  /* ADDING A PRODUCT */
    @Given("User creates a product in ecommerce application using POST API Request")
    public void userCreatesAProductInEcommerceApplicationUsingPOSTAPIRequest() {
        System.out.println("Token:"+ sharedState.token);
        addProduct = given().spec(RequestSpecProvider.getAuthSpec(baseURI,sharedState.token)).param("productName", "Apple")
                .param("productAddedBy", sharedState.userID).param("productCategory", "Watches")
                .param("productSubCategory", "Ultra 2").param("productPrice", "104900")
                .param("productDescription", "Apple Watch Ultra 2").param("productFor", "Men")
                .multiPart("productImage",new File("C:/Users/ptewari/Downloads/apple_ultra2_watch.jpg"));
    }

    @Then("User should get product add message in HTTP Response")
    public void userShouldGetProductAddMessgaeInHTTPResponse() {
        createProductResponse = addProduct.when().post("api/ecom/product/add-product").then().spec(resSpecTwo).extract().as(CreateProductResponse.class);
        sharedState.productID = createProductResponse.getProductId();
        productAddSuccessMsg = createProductResponse.getMessage();
        Assert.assertEquals(productAddSuccessMsg,"Product Added Successfully","Product addition unsuccessful response msg");
    }



   /* PLACING AN ORDER */
    @Given("User places an order in ecommerce application using POST API Request")
    public void userPlacesAnOrderInEcommerceApplicationUsingPOSTAPIRequest() {
        CreateOrderRequest.Order order = new CreateOrderRequest.Order();
        order.setCountry("India");
        order.setProductOrderedId(sharedState.productID);
        List<CreateOrderRequest.Order> al = new ArrayList<>();
        al.add(order);
        createOrderRequest.setOrders(al);
        placeOrder = given().spec(RequestSpecProvider.getAuthSpec(baseURI,sharedState.token)).contentType("application/json").body(createOrderRequest);
    }

    @Then("User should get order placed success message in HTTP Response")
    public void userShouldGetOrderPlacedSuccessMessageInHTTPResponse() {
        createOrderResponse = placeOrder.when().post("api/ecom/order/create-order").then().spec(resSpecTwo).extract().as(CreateOrderResponse.class);
        List<String> orders = createOrderResponse.getOrders();
        sharedState.orderId = orders.get(0);
        System.out.println("ORder ID in place order:"+sharedState.orderId);
        List<String> productOrderIDs = createOrderResponse.getProductOrderId();
        sharedState.productOrderId = productOrderIDs.get(0);
        orderPlaceMsg = createOrderResponse.getMessage();
        Assert.assertEquals(sharedState.productOrderId,sharedState.productID,"Product ID's mismatched between Create Product and Create Order Request");
        Assert.assertEquals(orderPlaceMsg,"Order Placed Successfully","Order placed nsuccessful response msg");
    }



    /* VIEW ORDER DETAILS */
    @Given("User views order details in ecommerce application using GET API Request")
    public void userViewsOrderDetailsInEcommerceApplicationUsingGETAPIRequest() {
        System.out.println("ORder ID in view order:"+sharedState.orderId);
        viewOrder = given().spec(RequestSpecProvider.getQueryParamSpec(baseURI,sharedState.token,"id",sharedState.orderId));
    }

    @Then("User should get order fetched success message in HTTP Response")
    public void userShouldGetOrderFetchedSuccessMessageInHTTPResponse() {
        orderDetailsResponse = viewOrder.when().get("api/ecom/order/get-orders-details").then().spec(resSpecOne).extract().as(OrderDetailsResponse.class);
        _id = orderDetailsResponse.getData().get_id();
        orderById = orderDetailsResponse.getData().getOrderById();
        orderBy = orderDetailsResponse.getData().getOrderBy();
        productOrderedId = orderDetailsResponse.getData().getProductOrderedId();
        productName = orderDetailsResponse.getData().getProductName();
        country = orderDetailsResponse.getData().getCountry();
        productDescription = orderDetailsResponse.getData().getProductDescription();
        orderPrice = orderDetailsResponse.getData().getOrderPrice();
        orderFetchedMsg = orderDetailsResponse.getMessage();
        Assert.assertEquals(_id,sharedState.orderId,"Order ID's mismatched between create order and view order");
        Assert.assertEquals(orderById,sharedState.userID,"User ID's mismatched between login request and view order");
        Assert.assertEquals(orderBy,ConfigReader.getInstance().fetchValueFromConfig("userEmail"),"Order By ID's mismatched between login request and view order");
        Assert.assertEquals(productOrderedId,sharedState.productOrderId,"Product ID's mismatched between Create Product/Order and view order");
        Assert.assertEquals(productName,"Apple","Product Name mismatched");
        Assert.assertEquals(country,"India","Product country mismatched");
        Assert.assertEquals(productDescription,"Apple Watch Ultra 2","Product description mismatched");
        Assert.assertEquals(orderPrice,"104900","Product price mismatched");
        Assert.assertEquals(orderFetchedMsg,"Orders fetched for customer Successfully","Order fetched success msg mismatched");
    }



    /* ORDER DELETE */
    @Given("User deletes an order in ecommerce application using DELETE API Request")
    public void userDeletesAnOrderInEcommerceApplicationUsingDELETEAPIRequest() {
        deleteOrder = given().spec(RequestSpecProvider.getPathParamSpec(baseURI,sharedState.token,"oId",sharedState.orderId));
    }

    @Then("User should get order deletion message in HTTP Response")
    public void userShouldGetOrderDeletionMessageInHTTPResponse() {
        String deleteOrderResponse = deleteOrder.when().delete("api/ecom/order/delete-order/{oId}").then().spec(resSpecOne).extract().response().asString();
        JsonPath js1 = new JsonPath(deleteOrderResponse);
        Assert.assertEquals(js1.get("message"),"Orders Deleted Successfully");
    }



   /* DELETE PRODUCT */
    @Given("User deletes a product in ecommerce application using DELETE API Request")
    public void userDeletesAProductInEcommerceApplicationUsingDELETEAPIRequest() {
        deleteProduct = given().spec(RequestSpecProvider.getPathParamSpec(baseURI,sharedState.token,"pId",sharedState.productID));
    }

    @Then("User should get product deletion message in HTTP Response")
    public void userShouldGetProductDeletionMessageInHTTPResponse() {
        String deleteProductResponse = deleteProduct.when().delete("api/ecom/product/delete-product/{pId}").then().spec(resSpecOne).extract().response().asString();
        JsonPath js1 = new JsonPath(deleteProductResponse);
        Assert.assertEquals(js1.get("message"),"Product Deleted Successfully");
    }


}
