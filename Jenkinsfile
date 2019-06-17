pipeline {
    agent any
    tools {
        nodejs "node"
        cryptomove "tholos"
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'yarn'
                sh 'yarn build'
            }
        }
        stage('Test'){
            steps {
                sh 'yarn lint'
                sh 'yarn test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'echo \'publish\''
            }
        }
    }
}