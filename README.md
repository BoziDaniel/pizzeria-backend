## What is it?

This is the backend part of an imaginary pizzeria. It has two different goals,
 works as a webshop for customers to order pizza and for employees it works as a
 simple ERP to help workflow.

At the moment there are five different roles:

- manager
- cook
- delivery guy
- customer
- anonymous

Based on the authenticated role front-end shows different information, for example 
manager can see every active orders while cooks can only see the orders assigned to them.

## Used technologies
* Hibernate
* Spring Boot
* Spring Security
* Lombok

## Endpoints
- /login: It uses JWT tokens from request body to authenticate user
- /cook/all
    - Method: GET
    - returns all cooks
- /deliveryguy/all 
    - Method: GET
    - returns all delivery guys
- /orders
    - /active 
        - Method: GET
        - returns relevant active orders based on user's role
    - /addnew
        - Method: POST
        - add new order
- /pizzas
    - /{int page}
        - Method: GET
        - returns the pizzas on the actual page
    - /numberOfPizzas
        - Method: GET
        - retunrs the number of different pizzas
    
## Front-end repository
https://github.com/BoziDaniel/pizzeria-frontend.git