@Library('Calulator_app') _
pipeline {
    agent { label 'agent' }
    Environment{
        APP_NAME = 'Calculator_app'
        SONARQUBE_SERVER = 'sonarqube'
    }

     options {
        skipDefaultCheckout(true)
    }

    stages {
        stage('Checkout') {
            steps {
                sh '''
                    whoami
                    hostname
                    pwd
                    java -version
                    git --version
                '''
                    
                }
            }
        }
    }
