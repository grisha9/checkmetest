Тестовое: 
Написать REST сервис, который будет оперировать двумя сущностями:
- обследование
- клиника. 
Одно обследование может содержаться в разных клиниках, но цена у него может быть разная. 

Сделать основные методы для этих сущностей (запись, чтение, обновление, удаление) и привязку обследования к клинике. При выполнении задания можно использовать любую реляционную базу данных. 
Можно использовать любые фреймворки за исключением Spring. 
------------------------------------------------------------

так как у вас на проекте используется асинхронное веб апи kotlin - ktor + expose
то решено было выбрать максимально похожие java frameworks - vertx + jooq

в качестве бд postgres т.к. для него есть полноценный асинхронный драйвер.

запуск проекта:
1)установка бд из докер образа:
docker run --name postgres-test -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres:11-alpine

2)запуск скриптов для создания бд: 
mvn liquibase:updateSQL

3)сборка проекта:  
mvn clean package

4)запуск проекта:
mvn exec:java

или можно проверить на тестах, убрав аннотацию @Ignore MainVerticleTest 
бд должна быть запущена 
настрйоки бд в application.properties

контракты веб методов:
1) создание(post) принимает json, возвращает 201 + id созданного объекта
2) обнволение(put) принимает json, возвращает 200 /404 (если запись не найдена или не обновлена из за неактуальной версии,
 реализован механизм optimistic lock на основе версий объекта)
3) удаление(delete) id в path, возвращает 200 /404 
4) get id в path, возвращает 200 /404 + json c данными
5) get + query param, возвращает 200 и json c данными (с учетом пейджинга или возможности получить следующую порцию данных)


