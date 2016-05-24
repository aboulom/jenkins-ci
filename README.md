
jenkins-ci: Demonstrates JBoss Fuse
======================================================
Author: Allen Boulom
Level: Beginner
Technologies: Camel, Karaf
Summary: This project demonstrates how to send files between two camel routes using bundles and features with continuous integration via Jenkins.   
Target Product: Jenkins 
Source: https://github.com/aboulom/jenkins-ci

Plugin Requirements
-----------------

This project requires additional plugins to work

Here’s the list of additional plugins:
- Groovy Plugin
- Scriptler

Steps To Build and Run Script
---------------------------

1. First, create a freestyle job in Jenkins with whichever name you choose. For this example, the name "Core-Components-Deploy-DEV" was used.

2. In the configuration page, check in "This build is parameterized" and click on "Add Parameter".

3. Click on "String parameters" and input the following values:

		 Name: jolokia.username
		 Default Value: <fuse admin username>
		 
		 Name: jolokia.url
		 Default Value: http://localhost:8181/hawtio/jolokia

4. Click on "Add Parameter" and choose "Password Parameter" with the following values:
		 
		 Name: jolokia.password
		 Default Value: <fuse admin password>

5. Under the Build section, click on "Add Build Step", then "Execute Groovy Script". Make sure you have groovy installed on the Jenkins server in
the "Manage Jenkins" -> "Configure System" page.

6. Select the groovy version to use, and check in "Groovy script file". Input the Groovy script path: 

		 $JENKINS_HOME/scriptler/scripts/Deploy_Features_to_Fuse.groovy

7. Click on "Advanced" on the bottom of the "Execute Groovy Script" section.

8. In the "Script parameters" box, input the bundle name followed by the location of the bundle. For example:

		 features-bundles mvn:com.hellofuse/features/1.0-SNAPSHOT/xml

9. Navigate to Scriptler plugin, which should be on the Jenkins home page, and add a new script with Id and Name: Deploy_Features_to_Fuse.groovy

10. Paste Deploy_Features_to_Fuse.groovy into the script box, and submit.

11. Build the Core-Components-Deploy-DEV job and check the console output to confirm that the build was successful.