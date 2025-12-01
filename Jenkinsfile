pipeline {
    agent any

    tools {
        maven 'M3'         // Nom de ton installation Maven dans Jenkins
        jdk 'openjdk17'    // Nom de ton JDK dans Jenkins
    }

    environment {
        SONARQUBE_ENV = 'sonarqube'  // Nom du serveur SonarQube configuré dans Jenkins
        SONAR_TOKEN_ID = 'sonarqube-token' // ID des credentials Jenkins pour le token SonarQube
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-auth-devops',  // Ton identifiant GitHub
                    url: 'https://github.com/affoul/devops_projet.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Tests') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: "${SONAR_TOKEN_ID}", variable: 'SONAR_TOKEN')]) {
                    sh """
                        mvn sonar:sonar \
                            -Dsonar.projectKey=devops_projet \
                            -Dsonar.projectName=devops_projet \
                            -Dsonar.sources=src \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.login=$SONAR_TOKEN
                    """
                }
            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminé'
        }
        success {
            echo '🎉 BUILD SUCCESSFUL !'
        }
        failure {
            echo '❌ BUILD FAILED !'
        }
    }
}
