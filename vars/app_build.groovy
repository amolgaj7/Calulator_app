def call() {
    def podYaml = libraryResource('android-build-pod.yaml')

    podTemplate(
        label: 'android-builder-agent',
        yaml: podYaml
    ) {
        node('android-builder-agent') {
            try {
                container('android-builder') {
                    stage('Build APK') {
                        echo "Building with system Gradle under 3Gi limit"

                        sh '''
                            set -e

                            echo "===== Environment Setup ====="
                            export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
                            export GRADLE_HOME=/opt/gradle/current
                            export ANDROID_HOME=/opt/android-sdk
                            export ANDROID_SDK_ROOT=/opt/android-sdk
                            export PATH=$JAVA_HOME/bin:$GRADLE_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH

                            cd CalculatorApp

                            echo "sdk.dir=$ANDROID_HOME" > local.properties

                            echo "===== Cleaning Project ====="
                            gradle clean --no-daemon \
                                -Dorg.gradle.workers.max=2 \
                                -Dorg.gradle.jvmargs="-Xmx1500m -XX:MaxMetaspaceSize=384m"

                            echo "===== Building APK with System Gradle ====="
                            # Uses global 'gradle' binary with memory safety flags
                            gradle assembleRelease --no-daemon \
                                -Dorg.gradle.workers.max=2 \
                                -Dorg.gradle.jvmargs="-Xmx1800m -XX:MaxMetaspaceSize=384m"
                        '''
                    }

                    stage('Archive Artifacts Outside Pod') {
                        archiveArtifacts artifacts: 'CalculatorApp/app/build/outputs/apk/release/*.apk', 
                                         fingerprint: true, 
                                         onlyIfSuccessful: true
                    }
                }
            } finally {
                cleanWs()
            }
        }
    }
}
