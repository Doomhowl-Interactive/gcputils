package com.doomhowl.gcputils

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository

class GcpUtilsPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // Register extension methods
        RepositoryHandler.metaClass.doomhowlMaven = { ->
            return owner.maven {
                url = project.uri("https://europe-west4-maven.pkg.dev/doomhowl-interactive/ndkports")
                credentials {
                    username = "oauth2accesstoken"
                    password = getAccessToken()
                }
            }
        }
    }
    
    /**
     * Gets a GCP access token using the gcloud CLI without requiring a Gradle Project
     * @return The access token as a string
     * @throws RuntimeException if the gcloud command fails
     */
    protected static String getAccessToken() {
        def isWindows = System.getProperty("os.name").toLowerCase().contains("windows")
        def gcloudCommand = isWindows ? "gcloud.cmd" : "gcloud"
        def command = []

        if (isWindows) {
            command = ["cmd", "/c", gcloudCommand, "auth", "print-access-token"]
        } else {
            command = [gcloudCommand, "auth", "print-access-token"]
        }

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(false)
                .start()

        def stdout = new StringBuilder()
        def stderr = new StringBuilder()

        process.inputStream.eachLine { stdout.append(it).append('\n') }
        process.errorStream.eachLine { stderr.append(it).append('\n') }

        def exitCode = process.waitFor()

        if (exitCode != 0) {
            throw new RuntimeException("Failed to get GCP access token: ${stderr.toString().trim()}")
        }

        def token = stdout.toString().trim()
        if (token.isEmpty()) {
            throw new RuntimeException("GCP token is empty. Are you authenticated with gcloud?")
        }

        return token
    }
}