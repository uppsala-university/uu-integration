Application Packaging for Karaf
===============================

Features XML for Application Packaging in Karaf:


Build
-----

    mvn install


Deploy
------

In Karaf/Servicemix shell:

    # features:repo-add mvn:se.uu.its.integration/uu-integration-packaging-karaf/1.0.0-SNAPSHOT/xml/features
    features:addurl mvn:se.uu.its.integration/uu-integration-packaging-karaf/1.0.0-SNAPSHOT/xml/features
    features:install uu-integration-all
