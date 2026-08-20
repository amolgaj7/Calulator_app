@Library('Shared_library') _
pipeline {
    agent { label 'root' }
    environment{
        APP_NAME = 'Calculator_app'
        SONARQUBE_SERVER = 'sonarqube'
    }

     options {
        //timestamps()
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

stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('sonarqube') {
            // Navigate into the root of the Android project
            dir('CalculatorApp') {
                // Tip: It's highly recommended to use the Gradle wrapper (./gradlew) for Android projects
                sh 'gradle sonarqube --no-daemon --stacktrace' 
                
                // Or if you strictly want to use your global gradle installation:
                // sh 'gradle sonarqube --no-daemon --stacktrace'
            }
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
//==============================================================================
// @Library('Shared_library') _

// pipeline {

//     agent { label 'Ubantu_machine' }

//     stages {

//         stage('Test Shared Library') {
//             steps {
//                 echo 'Shared Library loaded successfully!'
//             }
//         }

//         stage('Checkout') {
//             steps {
//                 gitCheckout(
//                     'https://github.com/amolgaj7/Calulator_app.git',
//                     'master'
//                 )
//             }
//         }
//     }
// }
