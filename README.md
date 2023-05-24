# _automacao-teste-software_

Project to explorer unit, e2e and load and stress tests with:
TestNG([at the branch testng](https://github.com/leonidesfernando/automacao-teste-software/tree/testng)) or
Junit([at the branch junit](https://github.com/leonidesfernando/automacao-teste-software/tree/junit))
, Mockito, REST Assured, Cucumber, Selenium Webdriver
, [JMeter](src/test/jmeter/README-JMETER.md) and [Postman](src/test/postman/README-JMETER.pt_br.md).

All tests were built to run against a simple web application in
the [teste-software](https://github.com/leonidesfernando/teste-software) repository.

## Requirements
- Maven
- JDK 18+


## Structure
This project uses some frameworks such as Spring Boot and Lombok, in case you come to use an IDE that has plugins to them, it's recommended that you install it. 

For IntelijIDEA:
1. Enable annotation processing: 
   1. Settings->Compiler->Annotation Processors: "Enable annotation processing"
2. Install plugins to Spring Boot and Lombok(via Marketplace)
3. Restart IDEA and enjoy it.

---
[Português](README.pt_br.md)
