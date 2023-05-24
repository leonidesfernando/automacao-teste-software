# _automacao-teste-software_

Projeto para explorar testes, unitários, e2e, API e testes de carga e stress com:  
TestNG([na branch testng](https://github.com/leonidesfernando/automacao-teste-software/tree/testng)) ou
Junit([na branch junit](https://github.com/leonidesfernando/automacao-teste-software/tree/junit)),
Mockito, REST Assured, Cucumber, Selenium Webdriver
, [JMeter](src/test/jmeter/README-JMETER.pt_br.md) e [Postman](src/test/postman/README-JMETER.pt_br.md)

Todos os tests foram construídos para serem executados contra uma simples aplicação web presente no
repositório [teste-software](https://github.com/leonidesfernando/teste-software)

## Requisitos

- Maven
- JDK 18+

## Estrutura
Este projeto alguns frameworks como SpringBoot e Lombok. Caso venha usar uma IDE que possua plugins para esses frameworks, recomenda-se que faça a instalação.

No IntelijIDEA:
1. Habilitar annotaion processing:
   Settings->Compiler->Annotation Processors: "Enable annotation processing"
2. Instalar o plugin do Lombok, Spring e Spring Boot(via Marketplace)
3. Reiniciar o IDEA

---
[English](README.md)