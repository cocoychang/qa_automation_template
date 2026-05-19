pipeline {
    agent any

    tools {
        maven 'Maven1'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/cocoychang/qa_automation_template.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test -DseleniumGrid=true'
            }
        }

        stage('Find Latest Report Folder') {
            steps {
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

                    env.LATEST_REPORT = latestFolder

                    echo "Latest Report Folder: ${env.LATEST_REPORT}"
                }
            }
        }

        stage('Publish Report') {
            steps {
                script {

                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: "src/test/resources/ExtentReport/${env.LATEST_REPORT}",
                        reportFiles: 'ExtentReport.html',
                        reportName: 'Automation Report'
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "Build completed"

            archiveArtifacts artifacts: 'src/test/resources/ExtentReport/**/*.*', fingerprint: true
        }
    }
}