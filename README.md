# Projet_SGBD
Application de gestion de stock d'un grossiste en matériaux de construction pour le cours "Projet de développement SGBD".

Enoncé :
Il est demandé de développer une application en Java permettant de gérer le stock d’un grossiste en
matériaux de construction.
Cette société possède un assez grand nombre d’articles liés à la construction et souhaite automatiser
sa gestion de stock ainsi que la gestion des factures pour ses différents clients.


Les fonctionnalités de l’application :
Pour accéder à l’application, un utilisateur devra se connecter (et, avant tout, s’inscrire).
L’utilisateur inscrit et connecté pourra :
Gérer les utilisateurs inscrits de l’application (CRUD)
Gérer les articles (CRUD)
Gérer les clients de la société (CRUD)
Gérer les différentes factures émises pour les clients (CRUD et même le CRUD sur les détails
de la facture)
Les différentes données seront stockées dans une base de données. 

Les contraintes :
Le projet Java doit être bien organisé (package regroupant les classes semblables).
Chaque classe devra comporter un minimum de JavaDoc.
De plus, la programmation orientée objet doit être respectée.
Gérer un maximum d'erreurs possibles en affichant des messages d'erreur personnalisés
partout où c’est possible.
Gérer l'intégrité des données au niveau de la BD mais également au niveau de la couche
applicative.
Ajouter des transactions dans votre application aux endroits nécessaires.
Soigner votre programme afin que les informations soient présentées clairement à l'écran.
Il faut que l'utilisation du programme soit aisée.
L’interface de l'application doit garder une même structure et cohérence (couleurs, polices)
NB : l'interface peut être aussi bien une interface graphique de type JavaFx/Swing/AWT qu'une
interface console.
Il est demandé d’implémenter votre application en utilisant les 3 design pattern vus en cours. 

Lancement du projet :
Editer les variables url, user et password dans le fichier src/main/java/singleton/DatabaseConnection.
url: chemin vers votre db MySQL.
user: utilisateur ayant accès en lecture/écriture à la db.
password: mot de passe de l'utilisateur utilisé pour la connexion à la db (user).
Créer une base de données MySQL ayant pour nom "wholesaler" et procéder au restore du dump du fichier wholesaler.sql accessible à la racine du répertoire.
