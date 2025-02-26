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
                    // Get the commit message
                    def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()

                    if (env.BRANCH_NAME == 'master') {
                        // Ensure the destination directory exists
                        sh 'if [ ! -d /var/www/html/prod ]; then mkdir -p /var/www/html/prod; fi'
                        
                        // Use rsync to copy only changed files to the destination directory
                        sh 'rsync -av --delete new/ /var/www/html/prod/'
                    } else if (env.BRANCH_NAME == 'dev') {
                        // Notify the commit message
                        sh "echo 'Commit message: ${commitMessage}'"
                    } else if (env.BRANCH_NAME == 'staging') {
                        // Ensure the destination directory exists
                        sh 'if [ ! -d /var/www/html/stage ]; then mkdir -p /var/www/html/stage; fi'
                        
                        // Use rsync to copy only changed files to the destination directory
                        sh 'rsync -av --delete new/ /var/www/html/stage/'
                    } else if (env.BRANCH_NAME == 'prod') {
                        // Change the deployment process for the prod branch
                        // For example, you might want to run a specific deployment script
                        sh 'deploy.sh prod'  // Replace with your actual deployment command

                        // Create a pull request to merge prod into master
                        sh '''
                        git checkout master
                        git pull origin master
                        git checkout prod
                        git checkout -b merge-prod-to-master
                        git merge master
                        git push origin merge-prod-to-master
                        gh pr create --base master --head merge-prod-to-master --title "Merge prod into master" --body "${commitMessage}"
                        '''
                    }
                }
            }
        }
    }
}
