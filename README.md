#Starting up kafka on local (Windows)

```
cd D:\Logiciel\kafka
```

First start Zookeeper

```
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```

Then start Kafka

```
bin\windows\kafka-server-start.bat config\server.properties
```

Then enjoy