def call() {

    stage('Build') {
        echo "Building the project"

        sh '''
            set -e

            echo "===== Jenkins environment ====="
            whoami
            pwd

            echo "===== Configure Java ====="
            export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
            export PATH=$JAVA_HOME/bin:$PATH

            java --version

            echo "===== Configure Gradle ====="
            export GRADLE_HOME=/opt/gradle/current
            export PATH=$GRADLE_HOME/bin:$PATH

            gradle --version

            echo "===== Configure Android SDK ====="
            export ANDROID_HOME=/opt/android-sdk
            export ANDROID_SDK_ROOT=/opt/android-sdk

            export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH
            export PATH=$ANDROID_HOME/platform-tools:$PATH
            export PATH=$ANDROID_HOME/emulator:$PATH

            echo "ANDROID_HOME=$ANDROID_HOME"
            echo "ANDROID_SDK_ROOT=$ANDROID_SDK_ROOT"

            echo "===== Android SDK verification ====="

            ls -ld $ANDROID_HOME
            ls -la $ANDROID_HOME

            echo "===== SDK Manager ====="
            which sdkmanager
            sdkmanager --version

            echo "===== ADB ====="
            which adb
            adb version

            echo "===== Android Platform ====="
            ls -la $ANDROID_HOME/platforms/

            echo "===== Android Build Tools ====="
            ls -la $ANDROID_HOME/build-tools/

            echo "===== Project directory ====="
            cd CalculatorApp

            echo "===== Project files ====="
            ls -la

            echo "===== Fix workspace ownership ====="
            sudo chown -R ubuntu:ubuntu .

            echo "===== Gradle Wrapper ====="
            chmod +x gradlew

            ls -lh gradle/wrapper/

            test -f gradle/wrapper/gradle-wrapper.jar
            test -f gradle/wrapper/gradle-wrapper.properties

            echo "===== Gradle Wrapper Version ====="
            ./gradlew --version

            echo "===== Create local.properties ====="
            echo "sdk.dir=$ANDROID_HOME" > local.properties

            cat local.properties

            echo "===== Verify Android SDK from Gradle ====="
            test -d "$ANDROID_HOME/platforms/android-34"
            test -d "$ANDROID_HOME/build-tools/34.0.0"

            echo "===== Cleaning project ====="
            ./gradlew clean --no-daemon

            echo "===== Building APK ====="
            ./gradlew assembleRelease --no-daemon

            echo "===== APK generated ====="
            ls -lh app/build/outputs/apk/release/
        '''
    }
}