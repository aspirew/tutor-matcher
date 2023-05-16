Z ważnych rzeczy żeby to działo

1. Jeżeli używamy dockerowego obrazu rabbita, to trzeba wyspecyfikować pory przy inicjalizacji kontenera eg. ```docker run -d --hostname haroldjcastillo --name rabbit-server -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin2017 -p 5672:5672 -p 15672:15672 rabbitmq:3-management```
2. Konfig połączenia jest w application.yml