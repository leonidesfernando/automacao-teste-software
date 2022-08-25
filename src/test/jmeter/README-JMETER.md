# automacao-teste-software -- JMeter

The script to run the loading tests against the web
system [teste-sofware](https://github.com/leonidesfernando/teste-software) is  _Whole flow.jmx_.
Before run you have to check:

* the credentials inside the file ``jmeter/confi/config.data.csv``
* the **url** and **port** where the web system will be running. These configurations are on the config
  element ``HTTP Request Defaults``
* and of course the thread properties(users amount, ramp-up period, loop count) inside the ``Thread Group``

## Requirements

- JDK 17+
- Apache JMeter 5.5+

This script was created using the following JMeter's plugins:

* 3 Basic Graphics
* 5 Additional Graphics
* Apdex Score Calculator( when executed in background by the script: ```runAndGenerateReports.bat```)

[To install the plugins manager ](https://jmeter-plugins.org/wiki/PluginsManager/)

[JMeter's best practices](https://jmeter.apache.org/usermanual/best-practices.html)

---
[Português](README-JMETER.pt_br.md)
