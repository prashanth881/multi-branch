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
                        sh 'npm run deploy'
                      }
            steps {
                script {
                    
                    if (env.BRANCH_NAME == 'master') {
                        // Ensure the destination directory exists
                        sh 'if [ ! -d /var/www/html/prod ]; then mkdir -p /var/www/html/prod; fi'
                        
                        // Use rsync to copy only changed files to the destination directory
                        sh 'rsync -av --delete new/ /var/www/html/prod/'
                    } else if (env.BRANCH_NAME == 'dev') {
                        // Notify the commit message
                        sh 'echo "Commit message: ${env.GIT_COMMIT_MESSAGE}"'
                    } else if (env.BRANCH_NAME == 'staging') {
                        // Ensure the destination directory exists
                        sh 'if [ ! -d /var/www/html/stage ]; then mkdir -p /var/www/html/stage; fi'
                        
                        // Use rsync to copy only changed files to the destination directory
                        sh 'rsync -av --delete new/ /var/www/html/stage/'
                    } else if (env.BRANCH_NAME == 'prod') {
                        // Ensure the destination directory exists
                        sh 'if [ ! -d /var/www/html/prod ]; then mkdir -p /var/www/html/prod; fi'
                        
                        // Use rsync to copy only changed files to the destination directory
                        sh 'rsync -av --delete new/ /var/www/html/prod/'
                    }
                }
            }
        }
    }
}
