@Library('Shared_library') _
pipeline {
    agent { label 'Ubantu_machine' }
    environment{
        APP_NAME = 'Calculator_app'
        SONARQUBE_SERVER = 'sonarqube'
    }

     options {
        timestamps()
        skipDefaultCheckout(true)
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
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
        // stage('build'){
        //     steps {
        //         app_build()
        //     }
        // }
        // stage('SAST Security Sanity Check') {
        //     steps {
        //         sast_scan()
        //     }
        // }
    }
}
