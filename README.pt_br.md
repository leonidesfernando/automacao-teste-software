# _automacao-teste-software_

Project to explorer several tests such as API, e2e, integration, load and stress with: TestNG, Mockito, REST Assured,
Cucumber, Selenium Webdriver
, [JMeter](src/test/jmeter/README-JMETER.md) and [Postman](src/test/postman/README-POSTMAN.md).

Também é possível gerar o  [relatório Allure](https://docs.qameta.io/allure/#_java)
basta executar: `mvn allure:report` para gerar e `mvn allure:serve` para abrir o relatório.

Todos os tests foram construídos para serem executados contra uma simples aplicação web presente no
repositório [teste-software](https://github.com/leonidesfernando/teste-software)

## Requisitos

- Maven
- JDK 17+

## Estrutura

Este projeto alguns frameworks como SpringBoot e Lombok. Caso venha usar uma IDE que possua plugins para esses
frameworks, recomenda-se que faça a instalação.

No IntelijIDEA:
1. Habilitar annotaion processing:
   Settings->Compiler->Annotation Processors: "Enable annotation processing"
2. Instalar o plugin do Lombok, Spring e Spring Boot(via Marketplace)
3. Reiniciar o IDEA

---
[English](README.md)