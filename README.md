# Introduction
This service is written with Java, in Spring Boot framework, with the usage of MySQL.

Restful API listening to 8080, and the port number of MySQL is 3306.

One table named `orders` is used to store data generated in the service. You can find the table structure in `./start.sh` or the test directory

# How to set up

1. Go to  `docker-compose.yml` , change the value of `GOOGLE_MAP_API_KEY` from `YOUR_API_KEY_HERE` to the Google Map API Key you used.

2. Make sure the username, password and schema you used in `docker-compose.yml` is aligned with `application.properties`

3. Run ` docker compose -f docker-compose.yml up --build` command in the root of project folder.

If you ever need to wipe out the database and restart the service,
```shell
docker-compose down -v
```

**Alternatively**, you can
1. Build the docker image first `docker build --tag order-service .`
2. Create volume and network
```shell
docker volume create mysql_data
docker volume create mysql_config
docker network create mysqlnet
```
3. Run the MySQL in a container
```shell
docker run -it --rm -d -v mysql_data:/usr/local/mysql \
-v mysql_config:/etc/mysql/conf.d \
--network mysqlnet \
--name mysqlserver \
-e MYSQL_USER=zhu -e MYSQL_PASSWORD=zhuyingcong \
-e MYSQL_ROOT_PASSWORD=zhuyingcong -e MYSQL_DATABASE=foo \
-p 3306:3306 mysql:8.0
```
4. Run the Spring Boot service in another container (Remember to replace your API key)
```shell
docker run --rm -d \
--name springboot-server \
--network mysqlnet \
-e MYSQL_URL=jdbc:mysql://mysqlserver/foo \
-e GOOGLE_MAP_API_KEY=YOUR_API_KEY_HERE \
-p 8080:8080 order-service
```
