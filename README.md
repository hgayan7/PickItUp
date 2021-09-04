# PickItUp

PickItUp is a service that allows users to request pick up of items to deliver to charity events organized by organizations. Volunteers of an event will be facilitating the pickup and delivery from requested users. It is for the help of individuals and organizations who wish to donate items but could not do in person.
## Run it locally
### Without using Docker
Navigate to the project's root directory and use the below commands:
```bash
mvn install
cd target
java -jar name_of_jar.jar
```
### Using Docker
Clone the repository
```
git clone https://github.com/hgayan7/PickItUp
```
Build docker image of the project
```
cd PickItUp
docker build . -t name-of-spring-boot-project-image
```
Run a MySQL container using the official MySQL docker image
```
docker run --name name-of-your-mysql-container -e MYSQL_DATABASE=pickitup -e MYSQL_ROOT_PASSWORD=password -d mysql:8.0
```
Run a new container using the name-of-spring-boot-project-image and link it to the name-of-your-mysql-container
```
docker run -p 8100:8100 --name name-of-spring-boot-project-container --link name-of-your-mysql-container:mysql -d name-of-spring-boot-project-image
```

## ER diagram
<p align="center" >
  <img width="800" height="700" src="https://user-images.githubusercontent.com/29502161/132048212-d8475287-48a5-4848-98e8-13310b7409f1.png" hspace="20">
</p>

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)
