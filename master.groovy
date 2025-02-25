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
                sh 'npm run deploy --env=prod'
                // Ensure the destination directory exists
                    sh 'if [ ! -d /var/www/html/prod ]; then mkdir -p /var/www/html/prod; fi'
                    
                    // Use rsync to copy only changed files to the destination directory
                    sh 'rsync -av --delete new/ /var/www/html/prod/
            }
        }
    }
}
