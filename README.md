# Service de notification via mail
## Description
Notification-service est un micro-service **Java** développé grâce au framework **SpringBoot**.  
Il a pour vocation d'envoyer des notifications par **mail** aux utilisateur ayant emprunté un ou plusieurs livre depuis un trop longue periode.  

## Fonctionnement
Cette liste de livre est fourni par le micro-service ***Landing Management*** via une requète http *GET*. Ce micro-service est requèté tous les jours.
En réponse de cette requète le micro-service reçois les données formatées en *JSON*.
Les données en *JSON* sont extraites et transformées afin de former le mail.
Le mail est ensuite envoyé via le serveur *smtp* de *google*.  

## Environnement
Maven pour la gestions des dépendances et le build  
JDK version: 1.8
Spring-boot: 2.1.8

### Packages
**com.microservice.application**: Point d'entré de l'application, on y trouve le main

| Packages        | Contenu      
| ------------- |:-------------:|
|com.microservice.application|Point d'entré de l'application <br> on y trouve le main|
