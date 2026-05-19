pipeline{
agent any
    tools {
        maven 'maven-3.9.9'
    }

    stages {
        stage('checkout') {
            steps {
                git branch: 'main', url:'https://github.com/cocoychang/qa_automation_template.git'
            }
        }

        stage('build') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Reports') {
                    steps {
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
                post {
                        always {
                            archiveArtifacts artifacts: '**/src/test/resources/ExtentReport/*.html', fingerprint: true
                        }
                        }

}}