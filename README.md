UU Integration
==============

Uppsala University integration components.


Dependencies
------------

UU integration depends on common-integration, ladok, and ladok-integration. They are all available on github:

https://github.com/uppsala-university


Build
-----

Build the respective projects (common-integration, ladok, ladok-integration, and uu-integration) using maven:

    mvn install


Deploy
------

In Karaf/Servicemix shell:

    features:addurl mvn:se.uu.its.integration/uu-integration-packaging-karaf/1.0.0-SNAPSHOT/xml/features
    features:install uu-integration-all

Or, drop uu-integration-packaging-karaf/target/classes/features.xml in $SERVICEMIX_HOME/deploy/


Vagrant
-------

If you are using Vagrant and VirtualBox on Mac OSX to host ServiceMix, there currently seems to be a
problem with using a synced directory as a local maven repository for ServiceMix.

A work-around is to sync the local maven repo of the host system to a temp dir on the guest system and
do a cp -ru to a dir that ServiceMix can use as a local maven repo.

E.g. in the Vagrant file add

    config.vm.synced_folder "~/.m2", "/vagrant/m2"

And after building with maven on the host system, do

    vagrant ssh -- sudo cp -ru /vagrant/m2/repository /opt/servicemix/.m2/

(assuming /opt/servicemix is configured as guest system home of ServiceMix, otherwise you can configure
wich directory to use in $SERVICEMIX_HOME/etc/org.ops4j.pax.url.mvn.cfg)

After that you should be able to follow the instructions in section 'Deploy'.

