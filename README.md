# CryptoMove Plugin

You can download this plugin from Jenkins.io

## Local Development

```
mvn verify
```

> This is ran in Bitbucket Pipelines

## Testing this out on your machine.

1. Download Jenkins, on a Mac you can do so via `brew install jenkins`.
2. Run the Jenkins server, on a Mac you can do so via
   `brew services start jenkins`. After installing Jenkins it should be
   running at `localhost:8080`.
3. Package the app with `mvn package`. This should create the
   `target/cryptomove.hpi` file. This file can be uploaded into Jenkins
   configurations.

   From there we will need to install the CryptoMove plugin into your local
   Jenkins instance. On the `<JENKINS_URL>/manage` page you can upload a
   plugin at `/pluginManager/advanced`.

4. From here, you can see the plugin by conifiguring a new `freestyle`
   project. In the build steps, you can see a new step for CryptoMove Tholos.

## Legal

Copyright 2019 CryptoMove Inc.
