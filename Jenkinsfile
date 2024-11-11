pipeline {
    agent any

    tools {
        maven 'Maven'  // Make sure this matches your Jenkins tool name
        jdk 'JDK-17'         // Make sure this matches your Jenkins tool name
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
    }

    post {
        always {
            // Clean workspace
            cleanWs()
        }
    }
}