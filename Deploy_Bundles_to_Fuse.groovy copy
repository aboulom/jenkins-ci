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
BUNDLE_URL=args[0];
def version = BUNDLE_URL.substring(BUNDLE_URL.indexOf("fuse-blueprint/")+'fuse-blueprint/'.length());
println "version >>> $version\n\n"
J4pClient j4pClient = J4pClient.url("$JOLOKIA_URL")
					.user("$USER")
					.password("$PASSWORD")
					.authenticator(new BasicAuthenticator().preemptive())
					.connectionTimeout(5000)
					.build();

mbeanName="org.apache.karaf:type=bundles,name=$KARAFNAME";

// Remove bundle
println "*** Removing: BUNDLE: $BUNDLE_URL"
J4pExecResponse BUNDLE_ID = j4pClient.execute(new J4pExecRequest(mbeanName,"install(java.lang.String)", BUNDLE_URL));
j4pClient.execute(new J4pExecRequest(mbeanName,"uninstall(java.lang.String)", BUNDLE_ID.getValue().toString()));
println "*** BUNDLE: $BUNDLE_URL --> *** Removed ***\n\n"

// Install bundle
println "*** Installing: BUNDLE: $BUNDLE_URL"
BUNDLE_ID = j4pClient.execute(new J4pExecRequest(mbeanName,"install(java.lang.String, boolean)", BUNDLE_URL, true));
println "*** BUNDLE: $BUNDLE_URL --> *** Installed ***\n\n"