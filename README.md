- [Desktop Application v1.8](https://github.com/osrmt/osrmt/releases/download/1.8/osrmt_1.8_desktop.zip)
- [Web Application v1.6](https://github.com/osrmt/osrmt/releases/download/v1.6/OSRMT.web_v1.6.zip)

# Open Source Requirements Management Tool
Open Source Requirements Management Tool (OSRMT) is a configurable, free open source solution that gives you an easy-to-install and easy-to-use solution for defining and managing software requirements. 
Designed to be a continuation for [OSRMT v1.5](https://sourceforge.net/projects/osrmt/).

Requirements analysis is critical to the success of software products. It includes determining the needs to satisfy, resolving conflicting requirements as well as analyzing, documenting, validating and managing requirements. - adapted from Wikipedia Requirements Analysis

A problem clearly stated is a problem half solved. - Dorothea Brande

Every hour spent understanding the requirements better saves a week of implementation. - unknown


## Goals (Why is this project useful?)
- user can "load and go" with a simple installation and one or more users can read/write each project locally or over their network;
- application can be be used as single user desktop app or multiuser web application with centralized server and desktop or web client.

## Supported OS
OSRMT is cross platform application, It was tested on: Windows, Linux (Centos distribution) and MacOS.

## Supported Databases
Initially OSRMT doesn't need any additional DB set up after installation - just install and run the application.

But if you need external storage for the application data you can use one of the following supported DBMS systems:
- Oracle;
- MySQL;
- MS Sql;
- PostgreSQL.
Follow 'Create and Install new database' and 'Import database contents' sections steps in [Installation manual]
Make sure to update connection.xml from corresponding connection.<DBMS>.xml

## Documentation
Kindly refer documentation folder available under build zip file for documentation of the product.

## Users support
If you found any problem review [here](https://github.com/osrmt/osrmt/issues/new) if your issue is not published yet or [create request to fix the issue](https://github.com/osrmt/osrmt/issues/new). As of 20-Feb-2019 project is active and going to be supported in the future.

For defects create new request to fix with the following information:
 - detailed steps to reproduce; E.g. 1. Start application -> 2. Login -> 3. Open Product -> 4. Menu - Tools - Report -> 5. All details report -> 6. Select product -> 7. Generate
 - expected result and actual results. They should be put right after steps to reproduce;
 - screenshots;
 - system logs which can be found in OSRMT installation folder.


For feature requests provide with the following information:
 - detailed description of the feature;
 - mockups if needed or examples screenshots.

If you have any questions refer to [FAQ](https://drive.google.com/open?id=1BTU3Bh0stVKCzl6Lgdd8uY0NbNs7CRWX)

## If you join our community you will be:
- welcomed and supported until you are understand what is expected;
- thanked for your contribution no matter how small or in what area.

## Getting started for users
Currently available latest version 1.8 can be downloaded by one of the following links:
 - [Desktop application v1.8](https://github.com/osrmt/osrmt/releases/download/1.8/osrmt_1.8_desktop.zip)
Currently available latest version 1.6 can be downloaded by one of the following links:
 - [Web application v1.6](https://github.com/osrmt/osrmt/raw/release/v1.6/build/OSRMT.web_v1.6.zip)

### Pre-installation requirements
 - Windows, Linux or MacOS
 - Archiver (winrar, 7-Zip, etc)
 - Installed JRE 1.8+

### Installation process
#### Desktop/Web app
 1) Download the build
 2) Extract archive

### Configuration
#### Desktop
Follow next steps only in case you want to use external DBMS (Oracle, MySQL, MS SQL, PostgreSQL), in the other case go to step 10)
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
 10) run run.bat\/run.sh depending on target OS (Note: for Linux/MacOS users run in termial 'chmod +x run.sh' in order to give permission to execute it)

#### Web
Follow next steps only in case you want to use external DBMS (Oracle, MySQL, MS SQL, PostgreSQL), in the other case go to step 10)
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
 9) run run.bat\/run.sh depending on target OS (Note: for Linux/MacOS users run in termial 'chmod +x run.sh' in order to give permission to execute it)
 10) application will be deployed in JBoss server on 8080 port, it will be accessible locally by following address http://localhost:8080/osrmt

### Development requirements
 - JDK 1.8+
 - Eclipse (as of now project is configured for Eclipse IDE)
 - Database server, one of the following: MySQL, Oracle, Postgres, MS Sql, MS Access
 - Ant
 - GIT client.

### Getting started for developers
Download sources or clone them via git clone command to chosen directory: git clone https://github.com/osrmt/osrmt.git and create branch 

#### Development
As of now project is configured for Eclipse IDE. You can use any IDE you want, just make sure to configure project classpath. All dependencies are in osrmt\build-resources\common\runtime-lib and osrmt\build-resources\common\compile-lib directories.

#### Building applications
Project can be built via Ant script. Open command line prompt from in the root of sources folder and execute following commands:
 - **ant app.client.assemble** - desktop application build. After build is finished assembled application will be available in 'dist' folder
 - **ant web.app.assemble** - web application build. After build is finished assembled application will be available in 'dist/web' folder

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **[Aron Smith](https://sourceforge.net/u/aron-smith/)** - *Initial work*

See also the list of [contributors](https://github.com/osrmt/osrmt/contributors) who participated in this project.

## License

This project is licensed under the GNU Gpl License - see the [LICENSE](LICENSE) file for details
