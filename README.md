
# JobOffers


### Web application aimed at assisting Junior Java Developers in finding job offers!

JobOffers is a web application built on Spring Boot.
It’s main goal is to aggregate job offers for Junior Java Developers.
It retrieves offers from external server at scheduled intervals and saves them in MongoDB database.
Only authenticated users have access to the application.
Authorization and authentication is implemented using Spring Boot Security and JWT tokens.
Once user is successfully logged in he can view, add and search offers.
Application has good coverage with unit and integration tests, including typical scenario test.
Controllers are tested via mockMvc, external server with offers was stubbed using WireMock and MongoDB test instances are created using Testcontainers.
For asynchronous scheduler testing Awaitility is used. Application also utilizes Redis for caching.
It is implemented in modular monolithic hexagonal architecture and follows facade design pattern.
Frontend is written in Angular accompanied by Tailwind CSS.
Everything is containerized and deployed on AWS EC2 server. Nginx is used as web server.
Application can be accessed at:

http://ec2-18-197-62-228.eu-central-1.compute.amazonaws.com

### Specification
- Spring Boot Web app
- Uses Facade design pattern
- Built on a modular monolith hexagonal architecture
- Stores offers and users data in NoSQL database (MongoDB)
- Ensures code coverage with unit and integration tests
- Fetches offers from external server on a schedule using RestTemplate
- Implements token authentication with Spring Boot Security
- Utilizes Redis database caching
- Features Angular frontend
- Deployed on an AWS Linux EC2 server


### Technologies

Core: <br>
![Static Badge](https://img.shields.io/badge/17-Java-orange?style=for-the-badge) &nbsp;
![Static Badge](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring) &nbsp;
![Static Badge](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white) &nbsp;

[//]: # (![Static Badge]&#40;https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink&#41; &nbsp;)

Testing:<br>
![Static Badge](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/assertj-darkblue?style=for-the-badge) &nbsp;
![Static Badge](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge) &nbsp;
![Static Badge](https://img.shields.io/badge/Testcontainers-9B489A?style=for-the-badge) &nbsp;
![Static Badge](https://img.shields.io/badge/awaitility-green?style=for-the-badge)
    
Deployment:<br>
![Static Badge](https://img.shields.io/badge/Amazon_AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/amazon%20ec2-rgb(236%2C%20217%2C%20198)?style=for-the-badge&logo=amazonec2) &nbsp;
![Static Badge](https://img.shields.io/badge/amazon%20ecr-rgb(255%2C%2077%2C%2077)?style=for-the-badge) &nbsp;
![Static Badge](https://img.shields.io/badge/nginx-rgb(0%2C%20179%2C%200)?style=for-the-badge&logo=nginx) &nbsp;


Frontend:<br>
![Static Badge](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white) &nbsp;
![Static Badge](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white) &nbsp;

Frontend repository: https://github.com/deBeee/joboffers-frontend

### Architecture
![Architecture diagram](/architecture/detailed_JobOffers_architecture_diagram.png)


### Endpoints
The application exposes the following endpoints:

|     ENDPOINT     |                  REQUEST DATA                  |               RESPONSE BODY                |       FUNCTION       |
|:----------------:|:----------------------------------------------:|:------------------------------------------:|:--------------------:|
|  POST /register  |            BODY (User credentials)             | username and account creation success flag |   registers a user   |
|  POST   /token   |            BODY (User credentials)             |        username with assigned token        | assigns user a token |
|  GET   /offers   |               HEADER (JWT token)               |     list of currently available offers     |  returns all offers  |
| GET /offers/{id} | HEADER (JWT token)<br/>PATH VARIABLE (offerId) |              requested offer               |  finds offer by id   |
|  POST   /offers  |    HEADER (JWT token)<br/> BODY (OfferDto)     |                added offer                 |  creates new offer   |

### Usage
**Using frontend:**

Visit: http://ec2-18-197-62-228.eu-central-1.compute.amazonaws.com
Prior to accessing the application you must register new account and log in.
Once logged in, you can access job offers, view specific offers by their ID, or create and save new job listings.

**Using exposed endpoints:**

To interact with the app, you can use Swagger:  
http://ec2-18-197-62-228.eu-central-1.compute.amazonaws.com:8000/swagger-ui/index.html#  
Alternatively, you can manually send requests (e.g., using Postman) to:  
http://ec2-18-197-62-228.eu-central-1.compute.amazonaws.com:8000      
Remember to append the endpoints mentioned earlier and provide the required request data.





