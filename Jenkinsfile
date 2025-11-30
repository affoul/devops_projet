pipeline {
    agent any
    tools {
        maven 'M3'
        jdk 'Java17'
    }
    environment {
        GIT_CREDENTIALS = 'github-auth-devops'
    }
    stages {
        stage('Preparation') {
            steps {
                git branch: 'main',
                    credentialsId: "${GIT_CREDENTIALS}",
                    url: 'https://github.com/affoul/devops_projet.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore clean package'
            }
        }
        stage('Results') {
            steps {
                junit '**/target/surefire-reports/TEST-*.xml'
                archiveArtifacts 'target/*.jar'
            }
        }
        stage('Deploy') {
            steps {
                script {
                    try {
                        sh 'cp target/projet_dev-1.0-SNAPSHOT.jar /opt/tomcat/webapps/'
                        echo '✅ Déploiement réussi vers Tomcat'
                    } catch (Exception e) {
                        echo '⚠️  Tomcat non disponible, archivage seulement'
                    }
                }
            }
        }
    }
    post {
        always {
            echo 'Pipeline terminé'
        }
        success {
            echo '✅✅✅ BUILD RÉUSSI ! ✅✅✅'
        }
    }
}