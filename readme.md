# DEAL MAKER

A brand-new fin-tech approach to a match-making app. 
Deal Maker takes away the awkwardness on discussing the touchy subject of personal finance in dating.

Apart from the traditional matching criteria in main-stream dating apps (e.g. age / geo location), our matching algorithm bases on an overview of a users spending habits to determine the compatibility between other users on the platform.
Deal Maker will challenge a user and their potential partner with a little test before they are off to their first date.
The cost and the division of a first date must be agreed upon the matchees found their compatible profile.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.


### Prerequisites
You will need:
Mobile App Component
- Android Studio
- Java SDK 1.8

Data Integration Component
- Capital One Hackathon Developer API (with a valid Bearer token)
- Java SDK 1.8
- SQLite

### Installing
Generating data:
Obtain a Bearer token following the instructions stated in:
https://developer.capitalone.co.uk/hackathon and paste into a key.txt file

move key.txt file to > Dealmaker/preprocc/src/main/resources/key.txt
maven build with goals in __preprocc__  mvn clean package" then 
```execute java -jar target/preprocc-1.0-SNAPSHOT-jar-with-dependencies.jar ```

CREATE MATCHING API
```
Give the example


## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management for Data Integration
* [Gradle](https://gradle.org) - Dependency Management for Mobile App Component
* [Docker] (https://www.docker.com) - Container for Sqlite database and matching API

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Leonhard Applis** - *Morgan David* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Capital One sponsoring HackNotts2019
