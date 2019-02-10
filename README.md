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
 - [desktop standalone application](https://github.com/osrmt/osrmt/raw/release/v1.6/build/OSRMT.desktop_v1.6.zip)
 - [web application](https://github.com/osrmt/osrmt/raw/release/v1.6/build/OSRMT.web_v1.6.zip)

### Pre-installation requirements
 - Windows, Linux or MacOS
 - Achiver (winrar, 7-Zip, etc)
 - Installed JRE 1.5+
 - Installed DB server, one of the following: MySQL, Oracle, Postgres, MS Sql, MS Access

### Installation process
#### Desktop/Web app
 1) Download the build
 2) Unzip archive

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
 1) follow 1-4 steps from Desktop installation process if you haven't passed them yet
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

This project is licensed under the GNU Gpl License - see the [LICENSE](LICENSE) file for details

