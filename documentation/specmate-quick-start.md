# Specmate Getting Started Guide
## Table of Contents

- [Installation, Configuration & Startup](#installation-configuration--startup)
  * [Installation](#installation)
  * [Configuration](#configuration)
    + [Adding new projects](#adding-new-projects)
    + [Adding requirements sources](#adding-requirements-sources)
  * [Starting Specmate](#starting-specmate)
- [Usage](#usage)
  * [Login](#login)
  * [Overview](#overview)
  * [Modelling Requirements](#modelling-requirements)
    + [Modelling with Cause-Effect-Graphs](#modelling-with-Cause-Effect-Graphs-ceg)
    + [Modelling with Process Diagrams](#modelling-with-process-diagrams)
    + [Basic functionalities available on both editors](#basic-functionalities-available-on-both-editors)
  * [Generating a Test-Case-Specification](#generating-a-test-case-specification)
  * [Creating a Test-Procedure](#creating-a-test-procedure)
   

# Installation, Configuration & Startup

## Installation

- Make sure you have Java 1.8 installed. If not, obtain a Java 1.8 release, e.g. from [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). To find out which Java version you are currently running, go to a console and type java -version.

- Obtain the latest Specmate relase from the download page.

- Unzip Specmate to a folder of your choice.

- On Windows run start.bat, on Linux/Mac run start.sh

## Configuration
Specmate is configured via the file specmate-config.properties. You can obtain a sample configuration file from [here](https://github.com/junkerm/specmate/blob/develop/bundles/specmate-config/config/specmate-config.properties).

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
In order to import requirements from Jira you can specify the following properties in the configuration file.

project.projects = [project-id]

project.[project-id].connector.pid           = com.specmate.connectors.jira.JiraConnector
project.[project-id].connector.jira.url      = [JIRA URL]

project.[project-id].connector.jira.project  = [JIRA PROJECT]

project.[project-id].connector.jira.username = [TECHNICAL USER NAME]

project.[project-id].connector.jira.password = [TECHICAL USER PASSWORD]

project.[project-id].connector.connectorID   = [project-id]

## Starting Specmate
In order to start specmate enter a terminal and type
java -jar specmate.jar -configurationFile [path-to-your-config-file]

Now, you can open a browser and navigate to http://localhost:8080 to access the Specmate front page.
 
# Usage

## Login
On the Specmate front page, select a project and enter a valid login for this project. Note that the credentials are generally specific for a certain project and will not work for every project.

## Overview
After logging in to Specmate you see the following views

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Welcome.png "Welcome page")

- On the left you see the project explorer. The project explorer shows the imported requirements in a tree. You can navigate through the tree (i.e. open the folders) and select a requirement.
- In the project explorer you can switch between the project view showing the imported requirements and the library view. In the library you can freely add folders and models.
- Above the project explorer is a search field. On entering a keyword the project explorer shows requirements and models matching the keyword. Note that currently the library is not included in the search.
- In the top section of the screen right beside the Specmate logo, you find buttons to saving the currently opened model, for navigation back and forth and to undo the last action in an model editor.


When a folder is selected you are presented with the following view

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Folder%20Overview.png "Folder overview")

- In the first section you can retrieve details about the selected folder
- Modifying the structure of the library (e.g add/remove folders) can be done in the *Sub-Folders* section
- Creating/Viewing Cause-Effect Models or Process Models can be done in the respective section 

## Modelling Requirements
For modeling requirements, you have the choice between Cause-Effect-Graphs and Proccess Diagrams. Depending on whether the type of requirement is rule-based (“If this and that, then the following … except for … then …”) or process-based (“First, the user enters A. Based on the input, the system does either B or C. Afterwards, the system asks the user for D, after that….”) you can choose the appropriate modelling technique. Cause-Effect-Graphs are used when modelling rule-based requirements, whereas process-based requirements can be modelled with Process Diagrams.  

### Modelling with Cause-Effect-Graphs (CEG)

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/CEG%20Editor.png "CEG Editor")


After opening the cause-effect edtior a modelling area in the center is presented to you, where you can create your CEG.
In order to model a CEG, you can choose a tool from the toolbox.
After selecting *Node* from the toolbox, you can click in the modelling area to create a new node. 
By default the name of the node is *variable* and the condition is set to *is present*. You can change the attributes of the selected node on the right side in the *Properties* section. 

Following best practice you should always declare variables as postive statements (e.g. *doors locked: true* instead of *doors not locked: not true*)
 
For connecting two nodes, choose the *Connection* tool and select the node which should represent the cause and afterwards select the node which should represent the effect. 
When a connection is created and selected, you have the possibilty to negate the connection.

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/CEG-Graph.png "CEG Graph")

When a node has multiple incoming connections you can change the type of the node. 
Depending on the type of the node, incoming connections can be defined as OR conjunctions or AND conjuctions. If the type of a node is set to AND, all predecessor nodes with a connection to the specific node need to be fullfiled in order to meet the constraints of this node. 
Only one direct predessecor needs to be fullfiled, when the type of a node is set to OR.


### Modelling with Process Diagrams
When modeling process diagrams you can open the associated editor. 
With the *Step* tool you can add an action to the model. Each model needs to have one start node and at least one end node.
Depending on the type of node you want to create choose from the toolbox either the *Start* or the *End* tool.

To increase the complexity of the model you can add a decision node by selecting the *Decision* tool.  
In order to connect two elements, you have to select the *Connection* tool and choose the nodes you want to connect. 
For each connection you can set a condition the variable has to fulfil. When using the decision node you can declare the condition of the outgoing connections which need to be fullfiled in order to follow the specific connection. 
When a node is selected Specmate displays the properties of the node on the right side. Furthermore you can specify the expected result of this step in the properties panel.

The following image shows a process of an ATM modeled with the process diagram editor.

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Process%20diagram.png "Process diagram")


### Basic functionalities available on both editors 
If you want to delete all elements in the editor, you can press the *Clear* button located in the toolbar. 
When choosing the *Delete* tool you are able to remove certain elements from the model. When the tool is selected you can click the element you want to remove. 
You have the ability to reorder the elements in the editor when you select the *Move* tool from the toolbox. 
  
On the right side of the editor you can change the name of the model and add a description. You can also add a description for every node in the model. Under the column *Links & Actions* you can go back to the requirement, see the description of the requirment, for which you currently creating a model. 
Links to already generated test specifiactions are also shown. In the last column *Change History* you can view which user made changes to the graph. If there are any erorrs in the created model, Specmate will display them above the *Change History* column. 

## Generating a Test-Case-Specification

You have the ability to create a Test-Case-Specification manually or generate it automatically from a model. You can see based on the icon of the specification in the project explorer if it is generated automatically or manually. 

Manually created: <img src="https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Manually.png" width="25" height="25" />  Automaticllay generated: <img src="https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Automatic.png" width="25" height="25" /> 

The name of the Test-Case-Specification is based on the date and time the specification was created. You have the ability to change the name of the specification and add a desription. 
The specification consists of multiple test cases, where each test case has a specific configuration.
A test case assigns each variable a value. In certain test cases Specmate leaves the value of a variable blank. If this is the case, the variable is not restricted to a certain value.
The generation of the specification follows the rules of Liggesmeyer. The application of these rules lead to a optimal relation between test coverage and number of test cases. 

The nodes which are in the *Input* column are variables which represent the causes from the model. Below the *Output* column you find the variables which represent the effects. 

You are also able to delete a test case when pressing the trashcan icon of the specific test case.
If you want to add test cases manually, you can press the *Create test case* button at the bottom. 
The order of the test cases can be changed by drag and drop. 

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Testcases.png "Test-case-specification")

## Creating a Test-Procedure
From each test case you can create a test procedure. Here you can define all necessary steps for the specific test case. 
You can add another step by pressing the *Create test step* button. At each step of the test procedure you are able to reference parameters from the created model. The parameters from the model can be set to a specific value in the *Parameter Mappings*. 
When the creation of a test procedure is finished, you can export it with the button on the right. 
You can also open and edit a test procedure which is already created, by clicking on it in the project Explorer or in the Requirements overview. Furthermore you have the ability to mark a test procedure as a regression test. 
The order of the test steps can be changed by drag and drop and you are also able to delete a test step by pressing the trashcan icon of the specific step.

![alt text](https://github.com/tobi321/specmate/blob/patch-1/documentation/images/Test-procedure.png "Test-Procedure")




