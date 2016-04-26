@Grab(group='org.jolokia', module='jolokia-client-java', version='1.2.3')

import org.jolokia.client.*
import org.jolokia.client.request.*
import org.jolokia.client.exception.*
import groovy.json.JsonOutput

// Define connection parameters as Jenkins project parameter, groovy will get them as environment variable
JOLOKIA_URL=System.getenv('jolokia.url');
USER=System.getenv('jolokia.username');
PASSWORD=System.getenv('jolokia.password');
KARAFNAME=(System.getenv('jolokia.karafname') ? System.getenv('jolokia.karafname') :'root');


// The bundle properties are received as script parameter
FEATURE_NAME=args[0];
FEATURE_URL=args[1];
def version = FEATURE_URL.substring(FEATURE_URL.indexOf("features/")+'features/'.length(),FEATURE_URL.indexOf("/xml"));
println "version >>> $version\n\n"
J4pClient j4pClient = J4pClient.url("$JOLOKIA_URL")
					.user("$USER")
					.password("$PASSWORD")
					.authenticator(new BasicAuthenticator().preemptive())
					.connectionTimeout(5000)
					.build();

mbeanName="org.apache.karaf:type=features,name=$KARAFNAME"


// Uninstall feature, there is no exception if it's not installed
println "*** Uninstalling: FEATURE: $FEATURE_NAME --> URL: $FEATURE_URL\n\n"
// Ignore Exception of Feature does not exist
try {
  	
  	J4pExecRequest infoFeatures = new J4pExecRequest(mbeanName, "infoFeature(java.lang.String)",FEATURE_NAME);
  	J4pExecResponse resp = j4pClient.execute(infoFeatures);
  
  	println JsonOutput.prettyPrint(resp.getValue().toString());
  	def existFeature=resp.getValue().toString();
  
  	def existFeatureVersion=existFeature.substring(existFeature.indexOf("Version")+"Version\":\"".length(),existFeature.indexOf("Version")+"Version\":\"".length()+(12));
  	println "\n\nexistFeatureVersion $existFeatureVersion"
  
  	//j4pClient.execute( new J4pExecRequest(mbeanName,"uninstallFeature(java.lang.String)",FEATURE_NAME) );
  	j4pClient.execute( new J4pExecRequest(mbeanName,"uninstallFeature(java.lang.String,java.lang.String)",FEATURE_NAME,existFeatureVersion) );
}
catch (org.jolokia.client.exception.J4pRemoteException ignore) {
}

// Remove repository (also removes the feature's url)
println "*** Removing: FEATURE: $FEATURE_NAME --> URL: $FEATURE_URL"
j4pClient.execute( new J4pExecRequest(mbeanName,"removeRepository(java.lang.String)",FEATURE_NAME));
println "*** FEATURE: $FEATURE_NAME | URL: $FEATURE_URL --> *** Removed ***\n\n"

// Add repository url
println "*** Adding: URL: $FEATURE_URL"
j4pClient.execute( new J4pExecRequest(mbeanName,"addRepository(java.lang.String)",FEATURE_URL));
println "*** URL: $FEATURE_URL --> *** Added ***\n\n"

// Install feature
println "*** Re-Installing: FEATURE: $FEATURE_NAME --> URL: $FEATURE_URL"
//J4pExecRequest installReq = new J4pExecRequest(mbeanName,"installFeature(java.lang.String)",FEATURE_NAME);
J4pExecRequest installReq = new J4pExecRequest(mbeanName,"installFeature(java.lang.String,java.lang.String)",FEATURE_NAME,version);
//J4pExecRequest installReq = new J4pExecRequest(mbeanName,"installFeature(java.lang.String)","MIMAMAMEAMAMUCHO");
J4pExecResponse installResp = j4pClient.execute(installReq);
println "*** FEATURE: $FEATURE_NAME | URL: $FEATURE_URL --> *** Installed ***\n\n"

// Check if feature was successfully installed
J4pExecRequest infoReq = new J4pExecRequest(mbeanName,"infoFeature(java.lang.String)",FEATURE_NAME);
//J4pExecRequest infoReq = new J4pExecRequest(mbeanName,"infoFeature(java.lang.String,java.lang.String)",FEATURE_NAME,version);
//J4pExecRequest infoReq = new J4pExecRequest(mbeanName,"infoFeature(java.lang.String)","MIMAMAMEAMAMUCHO");
J4pExecResponse infoResp = j4pClient.execute(infoReq);

println JsonOutput.prettyPrint(infoResp.getValue().toString());