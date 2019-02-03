# Open Source Requirements Management Tool
Requirements management tool designed to achieve full SDLC traceability for features, requirements, design, implementation and testing. UI for requirements derivation, version control, attributes etc.
Designed to be a continuation for OSRMT v1.5 (https://sourceforge.net/projects/osrmt/).

## Goals (Why is this project useful?)
 - OSRMT can be used via browser without any local installation, at no cost with no ads and no registration;
 - User can "load and go" with a simple installation and one or more users can read/write each project locally or over their network.

### Development requirements
 - JDK 1.5+
 - IDE
 - Database server, one of the following: MySQL, Oracle, Postgres, MS Sql, MS Access
 - Maven, version 3.0+
 - GIT client.

## Getting started
Currently available latest version 1.5 can be downloaded by following link https://sourceforge.net/projects/osrmt/files/latest/download

### Pre-installation requirements
 - Windows, Linux or MacOS
 - Installed JRE 1.5+
 - Installed DB server, one of the following: MySQL, Oracle, Postgres, MS Sql, MS Access

### Installation process
 - unzip downloaded archive
 - from extracted folder run command 'java -jar osrmt15.jar'
 - proceed by steps in installation wizard.

### Configuration
#### Desktop
 1) go to installation folder
 2) enter 'client' directory
 3) enter 'schema' directory
 4) execute <user\_database>\_create\_user.sql, <user\_database>\_create\_schema.sql and <user\_database>\_create\_view.sql scripts, where <user\_database> corresponds to DB server installed
 5) go back to 'client' folder
 6) find connection.<user_database>.xml, where <user_database> corresponds to DB server installed
 7) open and edit connection properties: username, password, URL
 8) rename connection.<user_database>.xml to connection.xml
 9) you are ready to work with desktop OSRMT application
 10) run run.bat\/run.sh depending on target OS

#### Web
 1) pass 1-4 steps from Desktop installation process if you haven't passed them yet
 2) go to installation folder -> 'server\jboss-4.0.3\bin'
 3) configure connection.xml configuration file according to DB you've set up (or copy connection.xml file from client/connection.xml if Desktop application had been already configured)
 4) run run.bat\/run.sh depending on target OS
 5) application will be deployed in JBoss server on 8080 port

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **[Aron Smith](https://sourceforge.net/u/aron-smith/)** - *Initial work*

See also the list of [contributors](https://github.com/osrmt/osrmt/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

 ## Tutorials
 https://www.youtube.com/watch?v=TisBqplJmF8
