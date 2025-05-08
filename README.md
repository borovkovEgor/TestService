## Тестовое задание для ВебРайз
Запуск через docker compose 
```
./gradlew clean build
docker compose -f docker-compose.yml up --build -d
```
Для локального запуска через среду разработки
```
docker compose -f docker-compose.local.yml up -d
```
Затем добавляем выбираем профиль local, для это заходим в Run/Debug configurations, далее в more options, выбираем Add VM options и копируем следующий код
```
-Dspring.profiles.active=local
```
или через терминал
```
./gradlew bootRun --args='--spring.profiles.active=local'
```
Сваггер - http://localhost:8080/swagger-ui/index.html