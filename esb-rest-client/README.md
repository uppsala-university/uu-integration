# Klient för REST-gränssnitt till UU:s integrationsteknik

ESB REST Client är en klient för att registrera händelser i Uppsala universitets händelsedrivna integrationsteknik.

Konfiguration för att tala om vilka URL:er som ska användas görs i `src/main/resources`. Där finns en exempelfil som beskriver fordrade egenskaper. Använd den genom

`mv restclient.properties.sample restclient.properties`

Redigera sedan filen `src/main/resources/restclient.properties` för att innehålla rätt URL.

Testa genom

`mvn test`