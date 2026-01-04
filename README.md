# TP2 – Architecture Orientée Services (SOA)  
## Gestion d’un Cabinet Médical

Cours assuré par : **Jaouad OUHSSAINE**  
Contact : jaouad.ouhs@gmail.com | jaouad_ouhssaine@um5.ac.ma

---

## Contexte

Ce TP correspond à la **deuxième phase** du projet pédagogique évolutif  
**Gestion d’un Cabinet Médical**.

Il consiste à **transformer l’application monolithique du TP1** en une  
**architecture orientée services (SOA)** basée sur des services métiers indépendants
et un **ESB Apache Camel**.

---

## Objectifs du TP

- Mettre en place une architecture SOA
- Séparer les responsabilités métiers par service
- Centraliser l’exposition des APIs via un ESB
- Réutiliser un module commun pour la persistance
- Comprendre les principes de découplage et de gouvernance des services

---

## Architecture globale

L’architecture est basée sur :
- Des services métiers indépendants
- Un module commun de données
- Un ESB servant de point d’entrée unique

---

## Structure du projet

```text
cabinetMedicalTp2SOA/
│
├── cabinet-repo                 # Entités JPA + Repositories
├── patient-service-api          # Service Patient
├── medecin-service-api          # Service Médecin
├── rendezvous-service-api       # Service Rendez-vous
├── consultation-service-api     # Service Consultation
├── cabinet-esb                  # ESB Apache Camel
└── pom.xml                      # Projet parent (packaging pom)
