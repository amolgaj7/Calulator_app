@Library('Calulator_app') _
pipeline {
    agent { label 'agent' }

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
                    checkout(
                        'https://github.com/amolgaj7/Calulator_app.git',
                        'master'
                    )
                    
                }
            }
        }
    }
}