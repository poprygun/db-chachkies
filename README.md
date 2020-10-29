# Spring Boot Interation with Oracle Stored Procedures

## Run Oracle in Docker

Follow [this guide](https://github.com/oracle/docker-images/tree/master/OracleDatabase/SingleInstance) to prepare Oracle Image.
[Additional info about the setup](https://marschall.github.io/2020/04/12/oracle-docker-container.html)

Once `oracle/database:19.3.0-se2` image is ready start it as follows:

Scripts in db/sql folder will be executed on Oracle image startup.
Make sure to update mount volume to proper directory.

```bash
docker run --name oracle-db \
 -p 1521:1521 -p 5500:5500 \
 -e ORACLE_PWD=password \
 -v /Users/ashumilov/projects/sp500/db-chachkies/db/sql:/docker-entrypoint-initdb.d/setup \
 -d oracle/database:19.3.0-se2
```

Sometimes you get _restricted mode on connection_ error, it can be fixed by folowing 
[this instructions](https://github.com/oracle/docker-images/issues/1620)

Basically, login to container and disable restricted mode.

```bash
docker exec -it --user=oracle 46d5dbadaaec bash
sqlplus sys/password@//localhost:1521/ORCLCDB as sysdba
alter system disable restricted session;
```






