# ESB Model
ESB model is the language used within the message bus. The language is a vital part of the "demilitarized zone of middleware to negotiate between the applications". This is described in the section [platform/language integration](http://www.enterpriseintegrationpatterns.com/patterns/messaging/Introduction.html) in the introduction to the messaging integration pattern. 

The purpose of the integration internal language is to provide "a generally accepted data representation layer that allows us to transfer data from one application to another in a platform and language independent manner". 

The ESB language is implemented with both Java objects and a XML scheme. The Java objects are annotated with JAXB that is the Java Architecture for XML Binding. Along with the model itself the ESB Model Library contains a ModelUtil for explicit marchalling and unmarchalling. The ESB model are also implemented for use within Apache Camel and the JAXB component.

![alt text](https://raw.githubusercontent.com/uppsala-university/uu-integration/master/esb-model/docs/integration-event-model.png "Uppsala University Event Routing Internal Language")