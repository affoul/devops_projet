pipeline {
    agent any

    tools {
        maven 'Maven3' // Nom du Maven configuré dans Jenkins
        jdk 'Java21'   // Nom du JDK configuré
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/affoul/devops_projet.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SAST') {
            steps {
                // Exemple SonarQube
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy') {
            steps {
                // Exemple de déploiement vers Tomcat
                sh 'cp target/projet_dev-1.0-SNAPSHOT.jar /chemin/vers/tomcat/webapps/'
            }
        }
    }

    post {
        success {
            echo 'Pipeline terminé avec succès !'
        }
        failure {
            echo 'La pipeline a échoué.'
        }
    }
}
