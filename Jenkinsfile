pipeline{
agent any
    tools {
        maven 'Maven1'
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
                            junit 'target/surefire-reports/*.xml'
                        }

                        success {
                            emailext (
                                to: 'hitendraverma22@gmail.com',
                                subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                                body: """
                                <html>
                                <body>
                                <p>Hello Team,</p>
                                <p>The latest Jenkins build has completed successfully.</p>
                                <p><b>Project Name:</b> ${env.JOB_NAME}</p>
                                <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                                <p><b>Build Status:</b> <span style="color: green;"><b>SUCCESS</b></span></p>
                                <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                                <p><b>Extent Report:</b> <a href="http://localhost:8080/job/${env.JOB_NAME}/HTML_20Extent_20Report/">Click here</a></p>
                                <p>Best regards,</p>
                                <p><b>Automation Team</b></p>
                                </body>
                                </html>
                                """,
                                mimeType: 'text/html',
                                attachLog: true
                            )
                        }

                        failure {
                            emailext (
                                to: 'hitendraverma22@gmail.com',
                                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                                body: """
                                <html>
                                <body>
                                <p>Hello Team,</p>
                                <p>The latest Jenkins build has <b style="color: red;">FAILED</b>.</p>
                                <p><b>Project Name:</b> ${env.JOB_NAME}</p>
                                <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                                <p><b>Build Status:</b> <span style="color: red;"><b>FAILED &#10060;</b></span></p>
                                <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                                <p><b>Please check the logs and take necessary actions.</b></p>
                                <p><b>Extent Report (if available):</b> <a href="http://localhost:8080/job/${env.JOB_NAME}/HTML_20Extent_20Report/">Click here</a></p>
                                <p>Best regards,</p>
                                <p><b>Automation Team</b></p>
                                </body>
                                </html>
                                """,
                                mimeType: 'text/html',
                                attachLog: true
                            )
                        }
                    }

}}