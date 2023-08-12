/**************************************************************************
*						SUJET DE TEST									  *
/**************************************************************************

Le but de l’exercice est de :  Développer une application “serveur” exposant une API REST pour permettre à une application mobile, ou un site Web, d’afficher la liste des parkings à proximité comme illustré sur l’écran en pièce jointe.
 
Ici, on utilisera la source de données suivante disponible à Poitiers pour récupérer la liste des parkings : https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilite-parkings-grand-poitiers-donnees-metiers&rows=1000&facet=nom_du_parking&facet=zone_tarifaire&facet=statut2&facet=statut3
Le nombre de places disponibles du parking en temps réel est récupérable via : https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom
 
Consignes :
•	L’application doit pouvoir fonctionner dans d’autres villes : l’URL et format des données parking pourront donc être complètement différents, cependant l’API REST exposée pour l’application mobile ne devra pas évoluer.
•	L’application à développer dans le cadre de l’exercice comporte uniquement la partie serveur (il n’est pas demandé de développer l’application mobile).
 
Les livrables que nous attendons sont :
•	Le code source de l’application (via Github par exemple)
•	Un document (type Readme) expliquant :  
	o	les choix que vous avez faits, 
	o	Les problèmes que vous n’avez peut-être pas traités, mais que vous avez identifiés,
	o	Toute autre information utile pour apprécier votre travail.
 
L’objectif : est de mettre en avant vos compétences et même si l’exercice est simple, de se placer dans un contexte professionnel (ou bien de préciser ce que vous auriez fait sur un “vrai” projet) en suivant les conventions et bonnes pratiques de programmation.

Bien que simple, ne prenez pas à la légère ce test, car il est très important pour nous dans la suite du process !
Dans tous les cas indiquez nous le temps que vous avez consacré à l’exercice.



/**************************************************************************
*				    SOLUTION ENVISAGE ET CHOISIS					      *
/**************************************************************************

Tout d'abord, n'ayant pas d'information à ce propos, je suis partis du principe que l'API devait également s'occuper d'identifier la ville dans laquelle se trouve l'utilisateur.
Je préfère centraliser le service de récupération de ville avec celui de recherche de parking dans mon application serveur. Ainsi, si l'un d'entre eux nécessite des modifications, 
alors le client restera inchangé et ne nécessitera aucune modification.

De plus, je n'ai identifié qu'une seule get request à mettre dans mon API "getNearParking" qui récupère tous les parkings dans la zone de recherche avec le nombre de places qu'il reste ("null" si pas renseigné)
celle-ci prend en paramètre les coordonnées gps du client ainsi que la distance max de poximité/recherche.


J'ai compris à travers l'intitulé qu'il fallait privilégier un code très maintenable et générique. C'est pourquoi, je me suis reposé sur le principe ETL (extraction, transform, load) sans la partie Load
Je n'avais pas besoin de mettre en place une BDD et de stocker des données.
Solutions Envisagé :
•	Solution 1 - Système de factory avec :
	o	Mise en place d'une factory qui permet de récupérer l'API/l'objet ETL (abus de langage) de la ville utilisée. Ces objets hérites d'une classe abstraite commune APIVille qui force l'implémentation 
			de deux fonctions abstraites. getParkingsData (Extraction de data) et transformDataToParking (transformation)
•	Solution 2 - Système de factory avec une API de fédération et une application distincte par APIVille :
	o	l'application serveur n'a aucun échange avec des APIs de parkings. Seul les applications/API représentants des villes auront à échanger avec ces APIs.
	o	la factory crée l'objet avec le bon type cet objet aura seulement à se soucier de construire la bonne url pour interroger l'API de la ville correspondante.
	o	l'url doit être générique avec un début différent selon la ville par exemple "http://com.{nomVille}.v1/getNearParking{param}"
	o	Les endpoints (URLs) exposés par chaque API de ville doivent être identiques.
il y a très peu de différence entre les deux solutions. Je dirais que la deuxième à l'avantage d'isoler les responsabilités. Ainsi on peut modifier le contenu de l'API d'une ville 
déjà existante sans risque d'impacte et sans avoir à interrompre la prod par exemple. En revanche, cela ajoute une complexité dans l'architecture de par le nombre d'application (d'acteur).





/**************************************************************************
*				    TRAVAIL REALISE					      				  *
/**************************************************************************

J'ai choisi de faire la solution 1. Dans notre context de test le travail aurait été plus important pour la mise en place d'une même logique, même si l'architecture aurait été légèrement différente.

•	Mise en place de deux services complètement configurables ILocationService (récupérer la ville) et ParkingService (récupérer les Parking).
	Pour ces Services j'ai choisi une configuration programmable. C'est une méthode de programmation qui permet une flexibilité Maximale 
	Certe elle rajoute une certaine complexité, mais je préfère cela que des "qualifiers" qui imposent une modification et un redéploiement du code.
	J'évoquais notamment dans la partie "SOLUTION ENVISAGE ET CHOISIS" que j'avais deux solutions en tête. 
	Et bien grâce à la configuration programmable je peux ajouter facilement la deuxième solution à mon application.
	Pour cela j'aurais seulement à créer une nouvelle classe implémentant IParkingService et à changer la classe à utiliser dans la propriété correspondante au service dans le .properties 
	de l'environnement concerné.
	De la même manière je pourrais très bien changer d'api à utiliser pour récupérer la ville.

•	Utilisation d'une API gratuite qui se nomme nominatim. Elle permet de récupérer une adresse et donc un nom de ville en fonction des coordonnées GPS (latitude, longitude).

•	Mise en place d'un fichier properties par environnement (dev, int, qual, prod).
	permet de rendre certaine notion dynamique en fonction de l'environnement sur lequel est déployé l'application.

•	Mise en place de couches d'objets DTOs, entre le client et l'application serveur et entre les APIs utilisés et l'application serveur. Pour faciliter la transformation, j'utilise jackson 
	dont notamment l'annotation @JsonProperty automatisant la conversion d'un objet json en type DTO 
	Permet une Encapsulation qui améliore la Séparation des préoccupations, l'interopérabilité et la maintenance.

•	Mise en place d'un modèle de données internes à l'application serveur. (package "com.vbogi.APIGenericParking.models")

•	Mise en place d'une factory qui permet de récupérer l'API/l'objet ETL (abus de langage) de la ville utilisée. Ces objets hérites d'une classe abstraite commune APIVille qui force l'implémentation 
	de deux fonctions abstraites. getParkingsData (Extraction de data) et transformDataToParking (transformation).
	je précise que le deuxième paramètre de transformDataToParking est de type générique. Ainsi nous pouvons définir pour chaque ville son propre DTO associé afin de faire une transformation 
	DTO -> data modèle serveur plus facilement

•	Travail à Améliorer/Implémenter :
	o	une gestion des logs que je n'ai pas implémentés
	o	une gestion de langue sur les configurations comme par exemple le fichier messages.properties (fr, eng etc...).
	o	une implémentation de spring sécurity, https. Avec pourquoi pas l'identification et l'authentification mise en place (Token)
	o	pourquoi pas une gestion centralisée des erreurs spécifiques à l'application.
	o	tests unitaires/intégrations avec JUNIT5 et Mockito.
	o	prevoir un champ dans le modèle de donnée d'un Parking, pour les informations complémentaires (payant ou pas, quel prix etc ....)
	je n'ai pas implémenté les points cités précédemment afin de vous rendre un rendu le plus rapidement possible. J'ai essayé de faire ce qui me paraissait le plus pertinent et le plus intéressant.
	
	
J'estime le Temps consacré à trois jours de travail soignées comportant :
	o	la réflexion de l'architecture
	o	une recherche et une appropriation des APIs de Poitiers pour les parkings et des APIs de récupérations de villes via coordonnées gps (nominatim)
	o	le développement
	o	la rédaction du readme


/**************************************************************************
*				    URLs TEST					      				      *
/**************************************************************************

run ApiGenericParkingApplication puis testez les urls suivantes ou bien testez vos propres urls

Exemple d'url test de ville non trouvé à partir de la localisation
http://localhost:8080/rest/api/parking/getNearParking?latitude=0.337126307915689&longitude=46.57505317559496&searchDistance=8000

Exemple d'url test de ville trouvée mais non géré par l'API (Lyon)
http://localhost:8080/rest/api/parking/getNearParking?latitude=45.72978551291901&longitude=4.840787839022237&searchDistance=8000

Exemple d'url test de ville trouvée et géré par l'API (Marseille) mais avec des implémentations en attente d'élaboration.
http://localhost:8080/rest/api/parking/getNearParking?latitude=43.30957184431178&longitude=5.396439086164614&searchDistance=1000

Exemple d'url test de ville trouvée et géré par l'API (Poitiers). zone de recherche sur 1000 mètres autour du point
http://localhost:8080/rest/api/parking/getNearParking?latitude=46.57505317559496&longitude=0.337126307915689&searchDistance=1000

Exemple d'url test de ville trouvée et géré par l'API (Poitiers). zone de recherche sur 8000 mètres autour du point
http://localhost:8080/rest/api/parking/getNearParking?latitude=46.57505317559496&longitude=0.337126307915689&searchDistance=8000



