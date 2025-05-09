# GcpUtils Plugin Example

This is an example project demonstrating how to use the GcpUtils Gradle plugin.

## Features Demonstrated

1. Applying the GcpUtils plugin to a Gradle project
2. Using the `doomhowlMaven()` repository extension method
3. Accessing the GCP token functionality through a custom task

## Setup

Before running this example:

1. Make sure you have the main GcpUtils plugin built:

   ```
   cd ..
   ./gradlew build publishToMavenLocal
   ```

2. Ensure you are authenticated with Google Cloud:
   ```
   gcloud auth login
   ```

## Running the Example

To test the plugin functionality:

```
./gradlew printGcpToken
```

This will attempt to retrieve a GCP access token using the plugin's functionality and print the first few characters.

## Using in Your Own Project

To use this plugin in your own project:

1. Apply the plugin in your build.gradle:

   ```groovy
   plugins {
       id 'com.doomhowl.gcputils' version '1.0-SNAPSHOT'
   }
   ```

2. Use the doomhowlMaven repository:
   ```groovy
   repositories {
       mavenCentral()
       doomhowlMaven()
   }
   ```
