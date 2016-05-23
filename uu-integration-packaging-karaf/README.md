# Application Packaging for Karaf
This module provides provisioning of the component in the repository by using the concept of Karaf Features.

## Build
To install the XML description of the integration components using Maven just do a

    mvn install

which will install into the target Maven repository.

## Deploy
Karaf has the ability to load features and OSGi bundles either by dropping them in the deploy folder or by
logging in to the Karaf shell and deploying them by a set of deploy commands.

Karaf will look for bundles and features referenced by a 'mvn:'-url in the local maven repository and in any
additional repositories specified in $OSGI_HOME/etc/org.ops4j.pax.url.mvn.cfg.

To deploy all of the UU Integration features and bundles, in Karaf/Servicemix shell type the following commands:

    repo-add mvn:se.uu.its.integration/uu-integration-packaging-karaf/1.0.0-SNAPSHOT/xml/features
    feature:install uu-integration-all
    
Or, drop uu-integration-packaging-karaf/target/classes/features.xml in $OSGI_HOME/deploy/
