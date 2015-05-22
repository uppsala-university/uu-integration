mvn3 -Dmaven.test.skip=true clean package;cp -v */target/*.jar ../integration-runtime/smx/

ssh -p 8101 smx@localhost

features:chooseurl hawtio 1.4.48
features:install -r -v hawtio-offline

features:install -r -v camel-jaxb
features:install -r -v camel-cxf
features:install -r -v camel-jpa
features:install -r -v jpa
features:install -r -v openjpa


sudo rm /opt/servicemix/apache-servicemix-*/deploy; sudo mkdir /opt/servicemix/apache-servicemix-*/deploy;sudo chown smx /opt/servicemix/apache-servicemix-*/deploy

#sudo cp /vagrant/smx/deploy/abdera-1.1.3-feature.xml /opt/servicemix/apache-servicemix-*/deploy/
sudo cp /vagrant/smx/mariadb-java-client-1.1.7.ja /opt/servicemix/apache-servicemix-*/deploy/

sudo cp /vagrant/smx/esb-model-1.0-SNAPSHOT.jar /opt/servicemix/apache-servicemix-*/deploy/
sudo cp /vagrant/smx/esb-event-logger-0.0.1-SNAPSHOT.jar /opt/servicemix/apache-servicemix-*/deploy/
sudo cp /vagrant/smx/esb-service-identity-rest-1.0-SNAPSHOT.jar /opt/servicemix/apache-servicemix-*/deploy/

less +F /var/lib/servicemix/log/servicemix.log
tail -f /var/lib/servicemix/log/servicemix.log

sudo service apache-servicemix stop
sudo service apache-servicemix start

curl -X POST -H "Content-Type: application/xml" -d @/vagrant/smx/person-changed-event.xml http://localhost:8989/esb/rest/identity/event/person/

mysql --user=idintegration --password=foobar logdb


#The following bundles may need to be refreshed: org.springframework.context (86), org.springframework.core (82), org.springframework.context.support (87)
