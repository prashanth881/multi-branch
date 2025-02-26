pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'npm install'
                sh 'npm run build'
            }
        }
        stage('Deploy') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'master') {
                        sh 'npm run deploy --env=prod'
                    } else if (env.BRANCH_NAME == 'dev') {
                        sh 'npm run deploy --env=dev'
                    } else if (env.BRANCH_NAME == 'staging') {
                        sh 'npm run deploy --env=staging'
                    }
                }
            }
        }
    }
}
