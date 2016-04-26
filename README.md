
super-guide: Demonstrates JBoss Fuse
======================================================
Author: Allen Boulom
Level: Beginner
Technologies: Camel, Karaf
Summary: This project demonstrates how to send files between two camel routes using bundles and features with continuous integration via Jenkins.   
Target Product: Jenkins 
Source: https://github.com/aboulom/JenkinsCIProject

Plugin Requirements
-----------------

This project requires additional plugins to work

Here’s the list of additional plugins:

- Environment injector plugin
- GitHub plugin
- Groovy Plugin
- Scriptler

Steps To Build and Run Script
---------------------------

1. First create a freestyle job in Jenkins named Core-Components-Deploy-DEV

2. Add String parameters with the following names then input corresponding values(make sure jolokia name and password match fuse login):
	jolokia.username
	jolokia.password
	jolokia.url
	FEATURE_NAME
	FEATURE_URL

3. Add Git project in Source Code Management

4. In Build Environment, add the JAVA_HOME path in the properties content.

5. In Build, set the Groovy script path: $JENKINS_HOME/scriptler/scripts/Deploy_Features_to_Fuse.groovy

6. Navigate to Scriptler plugin, and add a new script with Id and Name: Deploy_Features_to_Fuse.groovy

7. Paste Deploy_Features_to_Fuse.script into the script box

8. Build Core-Components-Deploy-DEV job with parameters