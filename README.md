# PickItUp

PickItUp is a service that allows users to request pick up of items to deliver to charity events organized by organizations. Volunteers of an event will be facilitating the pickup and delivery from requested users. It is for the help of individuals and organizations who wish to donate items but could not do in person.
## Run it locally
Clone the repository
```
git clone https://github.com/hgayan7/PickItUp
cd PickItUp
```
### Without using Docker
Navigate to the project's root directory and use the below commands:
```bash
mvn install
cd target
java -jar pickItUp.jar
```
### Using Docker

Create .jar
```
mvn clean
mvn install
```
Build docker image of the project
```
docker build . -t name-of-spring-boot-project-image
```
Run a MySQL container using the official MySQL docker image
```
docker run --name mysql-standalone -e MYSQL_DATABASE=pickitup -e MYSQL_ROOT_PASSWORD=password -d mysql:8.0
```
Run a new container using the name-of-spring-boot-project-image and link it to the mysql-standalone container
```
docker run -p 8100:8100 --name name-of-spring-boot-project-container --link mysql-standalone:mysql -d name-of-spring-boot-project-image
```

## ER diagram
<p align="center" >
  <img width="800" height="900" src="https://user-images.githubusercontent.com/29502161/133283593-769ba4df-32d5-4d7c-9fa4-0f5afd5420b2.png" hspace="20">
</p>

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)
