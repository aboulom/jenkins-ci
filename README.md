
jenkins-ci: Demonstrates Continuous Integration with Jenkins
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

6. Select the groovy version to use, and check in "Groovy script file". 

	1) To deploy a feature, input the Groovy script path: 

		 $JENKINS_HOME/scriptler/scripts/Deploy_Features_to_Fuse.groovy
		 
	OR
	
	2) To deploy a bundle, input the Groovy script path:
	
		 $JENKINS_HOME/scriptler/scripts/Deploy_Bundles_to_Fuse.groovy

7. Click on "Advanced" on the bottom of the "Execute Groovy Script" section.

8. In the "Script parameters" box,

   1) To install a feature, input the feature name followed by the URI of the feature xml file. For example:

		 features-bundles mvn:com.hellofuse/features/1.0-SNAPSHOT/xml
		 
   Example "features.xml" file of where feature name and location is derived from:
	
   ![Imgur](http://i.imgur.com/dLZZU65.png)
   
   OR
   
   2) To install a bundle, input the bundle URI. For example:
   
   		mvn:com.hellofuse/fuse-blueprint/1.0-SNAPSHOT

9. Navigate to Scriptler plugin, which should be on the Jenkins home page, and add a new script with Id and Name : 

	1) To deploy features, add "Deploy_Features_to_Fuse.groovy"
	
	OR 
	
	2) To deploy bundles, add  "Deploy_Bundles_to_Fuse.groovy"

10. Paste your groovy script based on the script you chose in the last step into the script box, and submit.

11. Build the Core-Components-Deploy-DEV job and check the console output to confirm that the build was successful.