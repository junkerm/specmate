1. Introduction
This document describes the process that shall be followed in case the Specmate data model changes. Section 2 describes the migration paths that are supported and discusses eventual caveats. Section 3 describes the migration paths that are NOT supported. Therefore, before changing the model, it is advised to check Section 3 whether the data model change is even feasible without loss of data. Section 4 discusses possible migration paths that are currently not implemented. Section 5 depicts the typical migration process.

2. Supported migration paths
Only migrations that imply preservation of information are permitted (see Section 3 for examples of migrations paths that imply loss of information and are therefore not supported).

2.1 Add an attribute to an object
All built-in ecore types and custom objects are supported.  
Caveat 2.1.1: In an abstraction hierarchy, added attributes to a parent object are NOT automatically propagated to child objects. The migrator must be written such that the new attribute is added to the respective child objects.
Caveat 2.1.2: In the case of custom objects, the attribute that needs to be added to that object is of type "long". This attribute acts then as a reference to the custom object. The referred object is NOT created. See Section 2.5 on adding an object.

2.2 Add an attribute that refers to custom objects
The typical use case for this migration path is when an object acts as a container for other objects, that is, there is a 1-n composition relationship between objects.   
Caveat 2.1.1 applies.
Caveat 2.2.1: The referred object is NOT created. See Section 2.5 on adding an object.

2.3 Rename an attribute
Caveat 2.3.1: In an abstraction hierarchy, renamed attributes of a parent are NOT automatically propagated to child objects. The migrator must be written such that the attribute is renamed in the respective child objects.

2.4 Change the type of an attribute
All built-in ecore types are supported. Only widening type changes are supported, i.e. type migrations the guarantee preservation of information. These are the following:
- short to int, long, float, or double
- char to int, long, float, double, or string
- int to long, float, or double
- long to float or double
- float to double
- boolean to string
- string to boolean 

Since these type changes are widening, the type conversions are implicit, i.e. supported "out-of-the-box" by the database. We do NOT perform any manual data conversion. 

Caveat 2.4.1: Even though ecore supports the type "byte", the h2 database stores these as short. Hence "byte" migration is not supported.
Caveat 2.4.2: Char values must be all numerical to be able to convert them to int, long, float, double. While this can be tested with Character.isDigit(ch), the migrator does NOT perform this check.
Caveat 2.4.3: Converting float to double has an inherent loss of precision, i.e. rounding errors occur in the conversion (see https://docs.oracle.com/cd/E19957-01/806-3568/ncg_goldberg.html).
Caveat 2.4.4: boolean true/false converts to the strings "TRUE" and "FALSE" 
Caveat 2.4.5: Only the strings t,T,TRUE,true,f,F,False,false are parsed into boolean values

2.5 Add an object
Add a custom object to the data model. Currently, not caveats are known.

3. Unsupported migration paths
The following migrations are not supported as they imply loss of information in the database. Currently, the standpoint is that removal of data from the model does not happen. Future work may consider implementing an archiving mechanism that flags attributes/objects as retired, without removing them from the database. The cost/benefit of such an approach needs to be evaluated first.

3.1 Removal of an attribute
3.2 Removal of an object
3.3 Narrowing type conversions

4. Feasible, but currently not supported migration paths
The following migrations are currently not supported but technically feasible. As soon as a need for such migrations arises, they can likely be implemented in a short period of time.

4.1 Rename an object
This migration was not explicitly mentioned in the requirements and has therefore not been implemented.

4.2 String conversions
All ecore built-in could be converted to/from strings, given explicit conversion rules. The database can not convert strings implicitly, except for the conversions already supported in Section 2.4.
Hence a manual conversion needs to be implemented, likely along these steps:
1) Read in all values from the column that contains the to be migrated attribute
2) Convert the values to the target type
3) Delete the original column with the old data type
4) Create a new column with the new data type
5) Insert the converted values into the new column
  
5. Migration process
This section is work in progress and should be updated with experiences from actual migrations once they are performed.

In principle, the migration process consists of the following 5 steps:
1) Change the specmate ecore data model
2) Implement a Migrator. Examples of how to implement the Migrator can be found in TestMigratorImpl.
3) Test the migration on a snapshot of the production data [This step is currently not well defined yet. From where to get the data?]
4) Deploy a new version of specmate
5) Restart specmate. The migration will be performed on startup. A log of the migration is stored in Y [This is also not clear yet].

