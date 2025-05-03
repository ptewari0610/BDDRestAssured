Feature:API Automation using Rest Assured

  @Login
  Scenario: Logging into the Application using POST API Request
    Given User login into the ecommerce application using POST API Request
    Then User should get correct message in HTTP Response


  @Product
  Scenario: Creating a product using POST API Request
    Given User creates a product in ecommerce application using POST API Request
    Then User should get product add message in HTTP Response


  @Order
  Scenario: Creating an order using POST API Request
    Given User places an order in ecommerce application using POST API Request
    Then User should get order placed success message in HTTP Response


  @ViewOrder
  Scenario: Viewing order details using GET API Request
    Given User views order details in ecommerce application using GET API Request
    Then User should get order fetched success message in HTTP Response

  @DeleteOrder
  Scenario: Deleting order using DELETE API Request
    Given User deletes an order in ecommerce application using DELETE API Request
    Then User should get order deletion message in HTTP Response


  @DeleteProduct
  Scenario: Deleting a product using DELETE API Request
    Given User deletes a product in ecommerce application using DELETE API Request
    Then User should get product deletion message in HTTP Response

