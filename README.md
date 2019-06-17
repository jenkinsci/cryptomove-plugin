# Tholos Jenkins Plugin

## Verify

```
mvn verify
```

> This is ran in Bitbucket Pipelines

## Testing this out on your machine.

1. Download Jenkins, on a Mac you can do so via `brew install jenkins`.
2. Run the Jenkins server, on a Mac you can do so via `brew services start jenkins`. After installing Jenkins it should be running at `localhost:8080`
3. Package the app with `mvn package`. This should create the
   `target/cryptomove.hpi` file. This file can be uploaded into Jenkins
   configurations.
4. This App contains a `Jenkinsfile` and can be used to set up the
   application. Add to Jenkins by creating a `newJob` at `/view/all/newJob` that
   is a Pipeline project.

5. Install the plugin via the Manage Plugin directory on `<JENKINS_URL>/manage`.
6. Configure the Cryptomove Tholos plugin on `<JENKINS_URL>/configureTools`
   by creating a configration called `tholos`. Upon creating this instance, add
   your `CRYPTOMOVE_EMAIL` and `CRYPTOMOVE_TOKEN`
7. In your `plugin`-based Jenkins project add the `cryptomove` the following
   to your `Jenkinsfile`.

## Legal

Copyright 2019 CryptoMove Inc.
