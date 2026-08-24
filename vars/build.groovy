def call(){

    stage('build'){
        echo "Building the project"

        sh '''

        echo "Present directory"
        pwd

        echo "Moving to project directory"
        cd devops/Calculator_app/workspace/Calculator_app_Pipeline/CalculatorApp/

        echo "java version"
        java --version

        echo "Gradle Version"
        gradle --version

        echo "Android SDK Version"
        echo $ANDROID_HOME

        echo "Building APK"
        ./gradlew assembleRelease --no-daemon

        '''
    }
}