@Library('Calulator_app') _
pipeline {
    agent { label 'agent' }

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
                script{
                    gitCheckout(
                        'https://github.com/amolgaj7/Calulator_app.git',
                        'master'
                    )
                    
                }
            }
        }
    }
}