
# DEAL MAKER

<img src="/logo_dealmaker.png" width="300" height="300"/>

A brand-new fin-tech approach to a match-making app.
Deal Maker takes away the awkwardness on discussing the touchy subject of personal finance in dating.

Apart from the traditional matching criteria in main-stream dating apps (e.g. age / geo location), our matching algorithm bases on an overview of a user's spending habits to determine the compatibility between one and another on the platform.
Deal Maker will challenge a user and their potential partner with a little test before they are off to their first date.
The cost and the ways to divide on a first date must be agreed upon the matchee finding a compatible profile.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.


### Prerequisites
Mobile App Component：
- Android Studio
- Java SDK 1.8

Data Integration Component：
- Capital One Hackathon Developer API (with a valid Bearer token)
- Java SDK 1.8
- SQLite

### Installing
Generating data:
Obtain a Bearer token following the instructions stated in:
https://developer.capitalone.co.uk/hackathon and paste into a key.txt file

move **key.txt** file to "Dealmaker/preprocc/src/main/resources/key.txt"
maven build with goals in **preprocc** 'mvn clean package' then execute 
```shell
preprocess/target>java -jar target/preprocc-1.0-SNAPSHOT-jar-with-dependencies.jar 

```

To build the API, simply run:
```shell
dealmakerapi> mvn clean jetty:run
```

To host it somewhere more stable, you can use it with docker:
```shell
dealmakerapi> docker build . -t dealmakerapi
dealmakerapi> docker run -p 8080:8080 dealmakerapi
```

The Docker image comes with *batteries included*, that is the Sqlite-Database. 
There is no further setup needed, if the sample dataset is enough.  

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management for Data Integration
* [Gradle](https://gradle.org) - Dependency Management for Mobile App Component
* [Docker](https://www.docker.com) - Container for Sqlite database and matching API


## Authors

* **Morgan David**,**Leonhard Applis**,**Nicola Wong*** @[HackNotts2019](https://www.hacknotts.com/)
See also the list of [contributors](https://github.com/Twonki/Dealmaker/contributors) who participated in this project.

## Acknowledgments

* Capital One sponsoring HackNotts2019
* UoN and the HackNotts-Staff for hosting a beautifull event!
