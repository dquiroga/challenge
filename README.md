# Adidas Coding Challenge
This project is was made as a part of Adidas selection process.
# Technologies 
1. Java 8
2. Redis
3. H2 as a relational DataBases
4. Spring Boot
5. Swagger
6. Docker
7. Maven
# Explanation

This project have 3 modules:
1. Review Services: This services allow get, create, delete and remove, products reviews.
2. Product Services: This services allow get a extendeable version of current Adidas Product API. 
3. Gateway: Work as  unique entry point to the rest of api.

# How to run
Clone the repo, go to the project path and, inside each module run:
```sh
$ mvn spring-boot:run
```
For example
```sh
$ cd gateway-api
$ mvn spring-boot:run
```

Then you can test this project into > http://localhost:8090/api

Thanks
