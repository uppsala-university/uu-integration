# UU Integration
This is the Uppsala University integration components. The overall picture of the components is an event routing network containing message brokering along with a set of channel adapters and messaging gateways. 

![alt text](https://raw.githubusercontent.com/uppsala-university/uu-integration/master/docs/event-routing-network-map.png "Uppsala university Event Routing Network Map")

The target environment for the components in the event routing map is an OSGi container, the target environment for channels is a MQ Broker and the target environment for the log database is a RDBMS. An example of a fully provisioned target environment is found at the GitHub repository [integration-runtime](https://github.com/uppsala-university/integration-runtime).

## Dependencies
The total set of integration components is supplied by dependencies of components from repositories 

*  [common-integration](https://github.com/uppsala-university/common-integration)
*  [ladok](https://github.com/uppsala-university/ladok)
*  [ladok-integration](https://github.com/uppsala-university/ladok-integration)

They are all available on [Uppsala University GitHub repository](https://github.com/uppsala-university).

## Build
Build the project along with its dependencies 

* common-integration
* ladok
* ladok-integration
* uu-integration

using maven:

    mvn install -Dmaven.test.skip=true

## Deployment
Deployment is bound to the execution enviroment. For deployment in a Karaf/ServiceMix environment read the [Karaf deployment documentation](https://github.com/uppsala-university/uu-integration/blob/master/uu-integration-packaging-karaf/README.md). 