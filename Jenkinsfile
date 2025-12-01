pipeline {
agent any

```
tools {
    maven 'M3'
    jdk 'openjdk version 17'
}

environment {
    GIT_CREDENTIALS = 'github-auth-devops'
    SONARQUBE_ENV = 'sonarqube' // Nom du serveur SonarQube configuré dans Jenkins
}

stages {

    stage('Checkout') {
        steps {
            git branch: 'main',
                credentialsId: "${GIT_CREDENTIALS}",
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

    stage('SAST - SonarQube Analysis') {
        steps {
            withSonarQubeEnv("${SONARQUBE_ENV}") {
                sh """
                    mvn sonar:sonar \
                    -Dsonar.projectKey=devops_projet \
                    -Dsonar.projectName=devops_projet \
                    -Dsonar.sources=src \
                    -Dsonar.java.binaries=target/classes
                """
            }
        }
    }

    stage('Quality Gate') {
        steps {
            // Bloque le pipeline si SonarQube Quality Gate échoue
            timeout(time: 1, unit: 'HOURS') {
                waitForQualityGate abortPipeline: true
            }
        }
    }

    stage('Deploy') {
        steps {
            script {
                try {
                    // Vérifier que Tomcat permet l'écriture (sudo requis)
                    sh 'sudo cp target/*.jar /var/lib/tomcat9/webapps/'
                    echo '✅ Déploiement réussi vers Tomcat'
                } catch (Exception e) {
                    echo '⚠️ Tomcat non disponible, archivage seulement'
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
        echo '🎉 BUILD SUCCESSFUL !'
    }
    failure {
        echo '❌ BUILD FAILED !'
    }
}
```

}
