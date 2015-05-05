mvn3 -Dmaven.test.skip=true clean package;cp -v */target/*.jar ../integration-runtime/smx/

ssh -p 8101 smx@localhost

features:chooseurl hawtio 1.4.48
#features:install hawtio-offline

#features:install camel-jaxb
#features:install camel-jpa
#features:install jpa
#features:install open-jpa

#features:install hawtio-offline camel-jaxb camel-jpa jpa open-jpa
features:install hawtio-offline camel-jaxb camel-jpa jpa

sudo rm /opt/servicemix/apache-servicemix-5.1.2/deploy	
sudo mkdir /opt/servicemix/apache-servicemix-5.1.2/deploy
sudo chown smx /opt/servicemix/apache-servicemix-5.1.2/deploy

sudo cp /vagrant/smx/esb-model-1.0-SNAPSHOT.jar /opt/servicemix/apache-servicemix-5.1.2/deploy/
sudo cp /vagrant/smx/esb-event-logger-0.0.1-SNAPSHOT.jar /opt/servicemix/apache-servicemix-5.1.2/deploy/
sudo cp /vagrant/smx/esb-service-identity-rest-1.0-SNAPSHOT.jar /opt/servicemix/apache-servicemix-5.1.2/deploy/

less +F /var/lib/servicemix/log/servicemix.log


sudo service apache-servicemix stop
sudo service apache-servicemix start

curl -X POST -H "Content-Type: application/xml" -d @/vagrant/smx/person-changed-event.xml http://localhost:8989/esb/rest/identity/event/person/

mysql --user=idintegration --password=foobar logdb
