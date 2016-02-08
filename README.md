# UU Integration
Uppsala University integration components.


## Dependencies
UU integration depends on common-integration, ladok, and ladok-integration. They are all available on github:

https://github.com/uppsala-university


## Build
Build the respective projects (common-integration, ladok, ladok-integration, and uu-integration) using maven:

    mvn install -Dmaven.test.skip=true


## Deploy in Karaf/Servicemix
Karaf has the ability to load features and OSGi bundles either by dropping them in the deploy folder or by
logging in to the Karaf shell and deploying them by a set of deploy commands.

Karaf will look for bundles and features referenced by a 'mvn:'-url in the local maven repository and in any
additional repositories specified in $SERVICEMIX_HOME/etc/org.ops4j.pax.url.mvn.cfg.

To deploy all of the UU Integration features and bundles, in Karaf/Servicemix shell type the following commands:

    repo-add mvn:se.uu.its.integration/uu-integration-packaging-karaf/1.0.0-SNAPSHOT/xml/features
    feature:install uu-integration-all

Or, drop uu-integration-packaging-karaf/target/classes/features.xml in $SERVICEMIX_HOME/deploy/