# Démo pour Consul Config
Ce projet permet de tester l'utilisation de consul pour stocker la configuration d'une application.
Le projet est basé sur Spring Cloud Consul et déployable sur un cluster K8s, il contient tous les artefacts pour ce faire.
Ce projet a été testé sur un poste de travail Windows 10 et 7 et déployé sur une VM Vagrant. 

#### Build
Pour un build complet des artefacts, il est nécessaire que les fichiers suivants soient présents dans le projet:
* src/main/resources/application.yml
* src/main/resources/bootstrap.yml
* src/main/docker/Dockerfile
* src/main/docker/run.sh
* src/main/k8s/deployment.yml
* src/main/k8s/service.yml

Il est possible de changer la configuration du serveur consul qui se trouve dans:
* src/main/resources/application.yml
* src/main/resources/bootstrap.yml

Dans ce cas, les paramètres ci-dessous désignent:
* le hostname du serveur consul (dans notre cas, il s'agira du nom du service K8s),
* le numéro de port d'écoute du serveur consul
```shell
  cloud:
    consul:
      host: consul-nonheadless
      port: 8500
```

Le build du projet se fait via la commande:
```shell
mvn clean package install
```
Ce qui produit les artefacts suivants dans le répertoire target pour le build, la publication d'une image docker et son déploiement sur un cluster K8s:
* sample-consulconfig-1.0.1-SNAPSHOT.jar
* dockerfile/Dockerfile
* dockerfile/run.sh
* dockerfile/sample-consulconfig-1.0.1-SNAPSHOT.jar
* k8s/deployment.yml
* k8s/service.yml

#### Déploiement
Le déploiement consiste à copier les fichiers générés par le build, sur la VM Vagrant où se trouve K8s et Consul.
En prérequis sur un poste Windows, il faut disposer de pscp.exe (utilitaire installé avec la suite putty).

A partir d'une console Windows cmd:

```shell
set VAGRANT_HOME=C:\workdir\machines\centos7-k8s-10
set PROJECT_HOME=C:\workdir\workspace\sample-consulconfig
pscp.exe -P 2222 -i %VAGRANT_HOME%\.vagrant\machines\devbox\virtualbox\vagrant.ppk %PROJECT_HOME%\target\dockerfile\* vagrant@127.0.0.1:/home/vagrant/demo/docker
pscp.exe -P 2222 -i %VAGRANT_HOME%\.vagrant\machines\devbox\virtualbox\vagrant.ppk %PROJECT_HOME%\target\k8s\* vagrant@127.0.0.1:/home/vagrant/demo/k8s
```

Publication du package sur une registry Docker sur la VM Vagrant:
```shell
cd /home/vagrant/demo/docker
sudo docker build -t sample-configconsul:0.1 .
sudo docker tag sample-configconsul:0.1 localhost:5000/sample-configconsul:0.1
sudo docker push localhost:5000/sample-configconsul:0.1
```

Déploiement de l'application sur le cluster K8s:
Avant cela, ne pas oublier de changer le tag de l'image Docker dans le fichier deployment.yml.
```shell
kubectl apply -f /home/vagrant/demo/k8s/deployment.yml
kubectl apply -f /home/vagrant/demo/k8s/service.yml
```