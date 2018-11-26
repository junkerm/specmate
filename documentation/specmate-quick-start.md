Specmate Getting Started Guide

# Installation, Configuration & Startup

## Installation

- Make sure you have Java 1.8 installed. If not, obtain a Java 1.8 release, e.g. from here. To find out which Java version you are currently running, go to a console and type java -version.

- Obtain the latest Specmate relase from the download page.

- Unzip Specmate to a folder of your choice.

- On Windows run start.bat, on Linux/Mac run start.sh

## Configuration
Specmate is configured via the file specmate-config.properties. You can obtain a sample configuration file from here.

### Adding new projects
Specmate is structured in projects. A project represents a space for requirements, models and tests that can be accessed by a group of users. 
For each project you can configure a requirement source and an export target for test-procedures. 
To add a new project, add the project id to the project list via the property project.projects

Example:
project.projects = myproject1, myproject2

### Adding requirements sources
Specmate supports different kinds of requirements sources:
- Requirements files
- JIRA
- HP PPM

Currently, the requirements sources also define which users can access the project. E.g. if you configure a JIRA requirements source, every user that has access to the JIRA instance, will be able to access the project.

#### Requirement from Files
To configure a requirement file source for a project, provide the location of a folder on your file system. Specmate will search for *.txt files in this folder and import the requirements contained in these files.
The format of the requirement files is as follows:

[Requirement-ID]
[Requirement-Text (may not contain blank lines)]

[Requirement-ID]
[Requirement-Text (may not contain blank lines)]
...

A requirement file location for a project is configured as follows:

project.[project-id].connector.pid = com.specmate.FileConnector
project.[project-id].connector.fileConnector.folder = [location on file system]
project.[project-id].connector.fileConnector.user = [user name]
project.[project-id].connector.fileConnector.password = [password]
project.[project-id].connector.connectorID = [project-id]

#### JIRA Import

## Starting Specmate
In order to start specmate enter a terminal and type
java -jar specmate.jar -configurationFile [path-to-your-config-file]

Now, you can open a browser and navigate to http://localhost:8080 to access the Specmate front page.
 
# Usage

## Login
On the Specmate front page, select a project and enter a valid login for this project. Note that the credentials are generally specific for a certain project and will not work for every project.

## Overview
After logging in to Specmate you see the following views

- On the left you see the project explorer. The project explorer shows the imported requirements in a tree. You can navigate through the tree (i.e. open the folders) and select a requirement.
- In the project explorer you can switch between the project view showing the imported requirements and the library view. In the library you can freely add folders and models.
- Above the project explorer is a search field. On entering a keyword the project explorer shows requirements and models matching the keyword. Note that currently the library is not included in the search.
- In the top section of the screen right beside the Specmate logo, you find buttons to saving the currently opened model, for navigation back and forth and to undo the last action in an model editor.


## Modeling Requirements
For modeling, you have the choice between Cause-Effect-Graphs and Proccess Diagrams. Depending on whether the type of requirement is rule-based (“If this and that, then the following … except for … then …”) or process-based (“First, the user enters A. Based on the input, the system does either B or C. Afterwards, the system asks the user for D, after that….”) you can choose a modelling technique. With Cause-Effect-Graphs it is quite intuitive to model rule-based reuqirements, whereas with process diagrams you can model process-based requirements more easily.

### Modelling with Cause-Effect-Graphs (CEG)
After opening the cause-effect editor you can see in the center a grid area, where you can model your CEG. 
Above the modeling area is a toolbox. 
After selecting *Node* from the toolbox, you can click in the grid area to create a new node. By default the node's name is *variable* and the condition is set to *is present*. You can change the attributes of the selected node on the right side under *Properties*. 
~~Conditions should always be written as positive conditions. ~~
Choose the *Select* tool from the toolbox to move created nodes. 
For connecting the variables (nodes) choose the *Connection* tool and select the node which should represent the cause and afterwards select the node which should represent the effect. 
When a connection is created and selected, you have the possibilty to negate the connection. (TODO: explanation negate connection) 



TODO: 
- How to add nodes
- How to add connections
- Negate connections
- AND OR
- 

### Modeling with Process Diagrams
TODO

## Generating a Test-Case-Specification
TODO

## Creating a Test-Procedure
