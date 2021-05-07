# Webflux streaming

## JDK
Expected to use JDK 11 or please change the pom xml maven properties to reflect the version you use

## How it works
	1. Data pushed to H2 in memory DB
    2. Data taken out of DB via Flux
    

## How to run it
1. Start the StreamingWebApp either from your preferred IDE
   Or from command line make sure you have compiled the project with the right JDK version
   goto web-app/ target folder and run java -jar target/web-app-1.0-SNAPSHOT-spring-boot.jar
2. Open http://localhost:8081/swagger-ui.html and navigate to [findAll]

## Suitable test
      

## Note
   1. Multi module structure is preferred choice
	