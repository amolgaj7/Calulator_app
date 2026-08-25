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
        stage('K3d Cluster Check') {
            steps {
                sh '''
                    whoami
                    hostname
                    pwd
                    k3d cluster start devops-cluster 2>/dev/null || echo "Cluster already running"
                    kubectl cluster-info
                    kubectl get nodes
                    k3d cluster describe devops-cluster
                '''
            }
        }

          stage('K3d Cluster Checks and CoreDNS Patch') {
            steps {
                sh '''
                set -e
                k3d cluster start devops-cluster 2>/dev/null || true

                # Patch CoreDNS using # as the sed delimiter to avoid backslash escaping
                kubectl get configmap coredns -n kube-system -o yaml | sed 's#forward . /etc/resolv.conf#forward . 8.8.8.8 1.1.1.1#' | kubectl apply -f -

                kubectl rollout restart deployment coredns -n kube-system
                '''
                }
            }

        stage('Checkout Repo') {
            steps {
                script {
                    gitCheckout('https://github.com/amolgaj7/Calulator_app.git', 'master')
                }
            }
        }



    stage('Build in K8s Pod') {
    steps {
        script {
            def podSpec = readFile('pod.yaml')

            podTemplate(label: 'android-build-agent', yaml: podSpec) {
                node('android-build-agent') {
                    container('android-builder') {
                        script {
                            gitCheckout('https://github.com/amolgaj7/Calulator_app.git', 'master')
                        }

                        sh '''
                            set -e

                            # 1. Download and extract portable JDK 17 (bypasses broken APT repositories)
                            if [ ! -d "/tmp/jdk-17/bin" ]; then
                                echo "Downloading OpenJDK 17..."
                                mkdir -p /tmp/jdk-17
                                curl -sL "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.10%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz" | tar -xz -C /tmp/jdk-17 --strip-components=1
                            fi

                            export JAVA_HOME="/tmp/jdk-17"
                            export PATH="$JAVA_HOME/bin:$PATH"

                            echo "================================="
                            echo "Using Java runtime:"
                            java -version
                            echo "================================="

                            # 2. Locate gradlew and run assembleDebug
                            GRADLEW_PATH=$(find . -maxdepth 3 -name gradlew | head -n 1)

                            if [ -n "$GRADLEW_PATH" ]; then
                                cd "$(dirname "$GRADLEW_PATH")"
                                chmod +x gradlew
                                
                                ./gradlew clean assembleDebug \
                                    --no-daemon \
                                    -Dorg.gradle.jvmargs="-Xmx1024m -XX:+UseG1GC -XX:MaxMetaspaceSize=256m" \
                                    -Dorg.gradle.workers.max=1 \
                                    -Dorg.gradle.parallel=false \
                                    -Dorg.gradle.internal.http.connectionTimeout=120000 \
                                    -Dorg.gradle.internal.http.socketTimeout=120000
                            else
                                echo "gradlew wrapper not found!"
                                exit 1
                            fi
                        '''
                    }
                }
            }
        }
    }
}

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: '**/*.apk', fingerprint: true, allowEmptyArchive: false
            }
        }
    }
}


//     stages {
//         stage('K3d Cluster Check') {
//             steps {
//                 sh '''
//                     whoami
//                     hostname
//                     pwd
//                     k3d cluster start devops-cluster 2>/dev/null || echo "Cluster already running"
//                     kubectl cluster-info
//                     kubectl get nodes
//                     k3d cluster describe devops-cluster
//                 '''
//                 script{
//                     gitCheckout('https://github.com/amolgaj7/Calulator_app.git', 'master')
//                 }
                    
//                 }
//             }

// // stage('SonarQube Analysis') {
// //     steps {
// //         withSonarQubeEnv('sonarqube') {
// //             // Navigate into the root of the Android project
// //             dir('CalculatorApp') {
// //                 // Tip: It's highly recommended to use the Gradle wrapper (./gradlew) for Android projects
// //                 sh 'gradle sonarqube --no-daemon --stacktrace --max-workers=2' 
                
// //                 // Or if you strictly want to use your global gradle installation:
// //                 // sh 'gradle sonarqube --no-daemon --stacktrace'
// //             }
// //         }
// //     }
// // }
//         stage('build'){
//             steps {
//                 app_build()
//             }
//         }
//         // stage('SAST Security Sanity Check') {
//         //     steps {
//         //         sast_scan()
//         //     }
//         // }
//     }
// }
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
