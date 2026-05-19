pipeline{
agent any
    tools {
        maven 'Maven1'
    }
        stages{
        stage('Checkout') {
                    steps {
                        git branch: 'main', url: 'https://github.com/hverma22/Selenium-Test-Framework.git'
                    }
                }
                stage('Build') {
                            steps {
                                bat 'mvn clean install'
                            }
                        }
                stage('Test') {
                            steps {
                                bat "mvn clean test -DseleniumGrid=true"
                            }
                        }
                stage('Publish Report') {
                    steps {
                        script {

                            def latestFolder = bat(
                                script: '''
                                for /f "delims=" %%i in ('dir /b /ad-h /o-d src\\test\\resources\\ExtentReport') do (
                                    echo %%i
                                    goto :done
                                )
                                :done
                                ''',
                                returnStdout: true
                            ).trim()

                            publishHTML([
                                allowMissing: false,
                                alwaysLinkToLastBuild: true,
                                keepAll: true,
                                reportDir: "src/test/resources/ExtentReport/${latestFolder}",
                                reportFiles: 'ExtentReport.html',
                                reportName: 'Automation Report'
                            ])
                        }
                    }
                }
        }
        post {
            always {
                script {

                    def latestFolder = bat(
                        script: '''
                        @echo off
                        for /f "delims=" %%i in ('dir /b /ad-h /o-d "src\\test\\resources\\ExtentReport"') do (
                            echo %%i
                            goto :done
                        )
                        :done
                        ''',
                        returnStdout: true
                    ).trim()

                    echo "Latest Report Folder: ${latestFolder}"

                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: "src/test/resources/ExtentReport/${latestFolder}",
                        reportFiles: 'ExtentReport.html',
                        reportName: 'Automation Report'
                    ])
                }
            }
        }

}

