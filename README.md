<!-- README.md -->
<h1 align="center">🚗 Site Automobile Reparation Garages - Microservices</h1>

<p align="center">
  <b>Une plateforme complète pour la gestion des services automobiles</b> <br>
  <i>Réparations, services à domicile, équipements, paiements et plus...</i>
</p>

---

<h2>📌 Introduction</h2>
<p>
Ce projet est une application de gestion automobile basée sur une architecture microservices. 
Il permet aux clients de réserver des services, d’acheter des équipements et de demander des services à domicile, 
tandis que les administrateurs peuvent gérer les équipements, les réservations et les clients.
</p>

<h2>📂 Structure du projet</h2>
<table>
  <tr>
    <th>Microservice</th>
    <th>Description</th>
    <th>Technologie</th>
  </tr>
  <tr>
    <td><b>UserService</b></td>
    <td>Gestion des utilisateurs et authentification</td>
    <td>Spring Boot</td>
  </tr>
  <tr>
    <td><b>CarService</b></td>
    <td>Gestion des véhicules des clients</td>
    <td>Spring Boot</td>
  </tr>
  <tr>
    <td><b>ReservationService</b></td>
    <td>Gestion des réservations de services</td>
  </tr>
  <tr>
    <td><b>MarketplaceService</b></td>
    <td>Gestion des équipements et commandes</td>
  </tr>
  <tr>
    <td><b>PaiementService</b></td>
    <td>Gestion des paiements</td>
    <td>Spring Boot</td>
  </tr>
  <tr>
    <td><b>IAService</b></td>
    <td>Estimation des réparations et prédiction des revenus</td>
    <td>Python / TensorFlow</td>
  </tr>
</table>

<h2>🛠️ Technologies utilisées</h2>
<ul>
  <li><b>Backend :</b> Spring Boot, ASP.NET Core, Node.js (NestJS)</li>
  <li><b>Base de données :</b> PostgreSQL, MongoDB</li>
  <li><b>Communication :</b> REST API, RabbitMQ</li>
  <li><b>Sécurité :</b> JWT, OAuth2</li>
  <li><b>Déploiement :</b> Docker, Kubernetes</li>
</ul>

<h2>🚀 Installation</h2>
<p>
<b>1. Cloner le projet :</b>
</p>
<pre><code>git clone https://github.com/votre-repo/site-automobile.git</code></pre>

<p>
<b>2. Lancer les services avec Docker :</b>
</p>
<pre><code>docker-compose up -d</code></pre>

<p>
<b>3. Accéder aux services :</b>
</p>
<ul>
  <li>API Utilisateurs : <code>http://localhost:8081</code></li>
  <li>API Voitures : <code>http://localhost:8082</code></li>
  <li>API Réservations : <code>http://localhost:8083</code></li>
  <li>API Marketplace : <code>http://localhost:8084</code></li>
</ul>

<h2>📜 Licence</h2>
<p>
Ce projet est sous licence MIT - voir le fichier <a href="LICENSE">LICENSE</a> pour plus de détails.
</p>

<h2>📞 Contact</h2>

<H1>EL-QASEMY MOHAMED</H1>
<H1>OTMANE KARYM</H1>
<H1>ACHRAF OUAHAB</H1>