pipeline {
agent any

```
tools {
    maven 'M3'       // Nom du Maven configuré dans Jenkins
    jdk 'openjdk version 17'     // Nom du JDK configuré dans Jenkins
}

environment {
    GIT_CREDENTIALS = 'GITHUB_CREDENTIALS_ID' // Remplace par l'ID de tes credentials si dépôt privé
    TOMCAT_PATH = 'C:\\chemin\\vers\\tomcat\\webapps' // Chemin vers ton Tomcat
}

stages {
    stage('Checkout') {
        steps {
            // Pour dépôt privé, ajoute credentialsId
            git branch: 'main', url: 'https://github.com/affoul/devops_projet.git', credentialsId: "${GIT_CREDENTIALS}"
        }
    }

    stage('Build') {
        steps {
            bat 'mvn clean install'
        }
    }

    stage('Tests') {
        steps {
            bat 'mvn test'
        }
    }

    stage('SAST') {
        steps {
            withSonarQubeEnv('SonarQube') {
                bat 'mvn sonar:sonar'
            }
        }
    }

    stage('Deploy') {
        steps {
            // Copier le jar vers Tomcat
            bat "copy target\\projet_dev-1.0-SNAPSHOT.jar ${TOMCAT_PATH}"
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
```

}
