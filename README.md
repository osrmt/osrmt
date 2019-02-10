# Open Source Requirements Management Tool
Requirements management tool designed to achieve full SDLC traceability for features, requirements, design, implementation and testing. UI for requirements derivation, version control, attributes etc.
Designed to be a continuation for OSRMT v1.5 (https://sourceforge.net/projects/osrmt/).

## Goals (Why is this project useful?)
 - OSRMT can be used via browser without any local installation, at no cost with no ads and no registration;
 - User can "load and go" with a simple installation and one or more users can read/write each project locally or over their network.

### Development requirements
 - JDK 1.5+
 - Eclipse (as of now project is configured for Eclipse IDE)
 - Database server, one of the following: MySQL, Oracle, Postgres, MS Sql, MS Access
 - Ant
 - GIT client.

## Getting started
Currently available latest version 1.6 can be downloaded by one of the following links:
 - [desktop standalone application v1.6](https://github.com/osrmt/osrmt/raw/release/v1.6/build/OSRMT.desktop_v1.6.zip)
 - [web application v1.6](https://github.com/osrmt/osrmt/raw/release/v1.6/build/OSRMT.web_v1.6.zip)

### Pre-installation requirements
 - Windows, Linux or MacOS
 - Achiver (winrar, 7-Zip, etc)
 - Installed JRE 1.5+
 - Installed DB server, one of the following: MySQL, Oracle, Postgres, MS Sql, MS Access

### Installation process
#### Desktop/Web app
 1) Download the build
 2) Extract archive

### Configuration
#### Desktop
 1) go to installation folder
 2) enter 'schema' directory
 3) Setup initial database structure (refer to official database provide guides for more details regarding how to create user/schema and apply scripts to DB instance):
 3.1) create osrmt user and database, grant all priveleges to that user,
 3.2) execute <user\_database>\_create\_user.sql, <user\_database>\_create\_schema.sql and <user\_database>\_create\_view.sql scripts against osrmt database, where <user\_database> corresponds to DB server installed 
 4) go back to the root of extracted folder
 5) find connection.<user_database>.xml, where <user_database> corresponds to DB server installed
 6) open and edit connection properties: username, password, URL
 7) rename connection.<user_database>.xml to connection.xml
 8) setup initial database content by following steps:
 8.1) execute run.bat\/sh depending on target OS
 8.2) enter option 4, which stands for "4) Initialize a new database"
 8.3) check target database and press Enter key
 8.4) when you are prompted to initialize empty database press Enter key
 8.5) Press Enter when you are prompted to choose next option
 9) you are ready to work with desktop OSRMT application
 10) run run.bat\/run.sh depending on target OS

#### Web
 1) go to installation folder
 2) enter 'dbscripts/schema' directory
 3) Setup initial database structure (refer to official database provide guides for more details regarding how to create user/schema and apply scripts to DB instance):
 3.1) create osrmt user and database, grant all priveleges to that user,
 3.2) execute <user\_database>\_create\_user.sql, <user\_database>\_create\_schema.sql and <user\_database>\_create\_view.sql scripts against osrmt database, where <user\_database> corresponds to DB server installed 
 4) go back to the root of extracted folder, and enter to 'jboss-4.0.3\bin'
 5) find connection.<user_database>.xml, where <user_database> corresponds to DB server installed
 6) open and edit connection properties: username, password, URL
 7) rename connection.<user_database>.xml to connection.xml
 8) setup initial database content by following steps:
 8.1) execute run.bat\/sh depending on target OS
 8.2) enter option 4, which stands for "4) Initialize a new database"
 8.3) check target database and press Enter key
 8.4) when you are prompted to initialize empty database press Enter key
 8.5) Press Enter when you are prompted to choose next option
 4) run run.bat\/run.sh depending on target OS
 5) application will be deployed in JBoss server on 8080 port, it will be accessible locally by following address http://localhost:8080/osrmt

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **[Aron Smith](https://sourceforge.net/u/aron-smith/)** - *Initial work*

See also the list of [contributors](https://github.com/osrmt/osrmt/contributors) who participated in this project.

## License

This project is licensed under the GNU Gpl License - see the [LICENSE](LICENSE) file for details

