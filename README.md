# Tholos Jenkins Plugin

## Verify

```
mvn verify
```

> This is ran in Bitbucket Pipelines

## Testing this out on your machine.

1. Download Jenkins, on a Mac you can do so via `brew install jenkins`
2. Run the Jenkins server, on a Mac you can do so via `brew services start jenkins`
3. Create a project with a repo that contains a `Jekninsfile` comes with the 
   [`Pipeline`](https://jenkins.io/doc/book/pipeline/) plugin.

## Using the CryptoMove Tholos Jenkins Plugin

1. Install the plugin via the Manage Plugin directory on `<JENKINS_URL>/manage`.
2. Configure the Cryptomove Tholos plugin on `<JENKINS_URL>/configureTools`
   by creating a configration called `tholos`. Upon creating this instance, add
   your `CRYPTOMOVE_EMAIL` and `CRYPTOMOVE_TOKEN`
3. In your `plugin`-based Jenkins project add the `cryptomove` the following
   to your `Jenkinsfile`.

```jenkinsfile
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
```

## Legal

Copyright 2019 CryptoMove Inc.
