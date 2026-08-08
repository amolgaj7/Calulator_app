@Library('Calulator_app') _
pipeline {
    agent { label 'agent' }
    environment{
        APP_NAME = 'Calculator_app'
        SONARQUBE_SERVER = 'sonarqube'
    }

     options {
        skipDefaultCheckout(true)
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
        timestamps(time: true, format: "yyyy-MM-dd HH:mm:ss")
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
                script{
                    gitCheckout('https://github.com/amolgaj7/Calulator_app.git', 'master')
                }
                    
                }
            }
        }
    }
