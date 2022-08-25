# automacao-teste-software -- JMeter

O script para rodar os testes de carga contra o sistema
web [teste-sofware](https://github.com/leonidesfernando/teste-software)
é o _Whole flow.jmx_.

Antes de rodar, você deve verificar:

* as credenciais no arquivo ``jmeter/confi/config.data.csv``
* a **url** e **porta** onde o sistema web estará rodando. Essas configurções estão no elemento de
  configuração ``HTTP Request Defaults``
* e claro as configurações de execução(users amount, ramp-up period, loop count) presentes no ``Thread Group``

## Requisitos

- JDK 17+
- Apache JMeter 5.5+

Este script foi criado usando os seguintes plugins do JMeter:

* 3 Basic Graphics
* 5 Additional Graphics
* Apdex Score Calculator( quando executado em background pelo script: ```runAndGenerateReports.bat```)

[Para instalar o gerenciador de plugins](https://jmeter-plugins.org/wiki/PluginsManager/)

[Boas práticas com JMeter](https://jmeter.apache.org/usermanual/best-practices.html)

---
[English](README-JMETER.md)