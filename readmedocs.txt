This is recipe management system.It adopt the microservce architecture.
There are four microservices in all  which are 

1. API Gateway - Request routing and Client side Load balancing 

2. Service Discovery- for health and available service instances

3. Usermanagement- for registration and management of users

4. Recipe Management - For the management of the recipe

The architecture has been provided by simple artifacts and 
the sample request and response can be seen by swagger documentation of both
UserManagement microservice and RecipeManagement microservice.

A docker compose has been provided to run these as containers.

Thre are two docker compose files .

one with obervability and monitoring which consumes alot memory on the pc

and second runs just the four microservices with their corresponding databases

which is the second internal docker compose file . just enter your terminal 

and run the command 
docker compose up and 
docker compose down to stop .

To run UserManagement microservice,
use http://localhost:

To start UserManagement  swagger just run

http://localhost:8080/swagger-ui.html

To Start RecipeManager microservices

http://localhost:8082/swagger-ui.html


