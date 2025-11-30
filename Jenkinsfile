pipeline {
    agent any

    tools {
        maven 'M3'       // Nom du Maven configuré dans Jenkins
        jdk 'Java17'     // Nom du JDK configuré dans Jenkins (Java 17)
    }

    environment {
        GIT_CREDENTIALS = 'dev_projet' // ID des credentials Jenkins pour GitHub
        TOMCAT_PATH = '/opt/tomcat/webapps' // Chemin vers Tomcat sur Linux
    }

    stages {
        stage('Checkout') {
            steps {
                // Dépôt Git avec credentials
                git branch: 'main',
                    credentialsId: "${GIT_CREDENTIALS}",
                    url: 'https://github.com/affoul/devops_projet.git'
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
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh "cp target/projet_dev-1.0-SNAPSHOT.jar ${TOMCAT_PATH}/"
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
