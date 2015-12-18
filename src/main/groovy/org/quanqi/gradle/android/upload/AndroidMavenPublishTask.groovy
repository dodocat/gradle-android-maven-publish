package org.quanqi.gradle.android.upload

import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.DefaultTask
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.tasks.TaskAction

/**
 * Created by cindy on 12/18/15.
 */
class AndroidMavenPublishTask extends DefaultTask {

    ApplicationVariant variant
    AndroidMavenPublishExtension publishExtension

    public AndroidMavenPublishTask() {
        super()
        this.description = "Upload android apks and mapping file to maven"
    }

    @TaskAction
    def upload() {
        publishExtension = project."${AndroidMavenPublishExtension.NAME}"
        uploadApk()
        uploadMapping()
    }

    def uploadApk() {
        MavenDeploy deploy = create()
        deploy.classifier = variant.name
        deploy.extension = 'apk'
        deploy.packaging = 'apk'
        variant.outputs.each {
            if (it.outputFile.path.endsWith('apk')) {
                deploy.file = it.outputFile
                return true
            }
        }
        deploy.deploy()
    }

    def uploadMapping() {
        MavenDeploy deploy = create()
        deploy.classifier = "${variant.name}-mapping"
        deploy.extension = 'txt'
        deploy.packaging = 'txt'
        deploy.file = variant.mappingFile
        deploy.deploy()
    }

    MavenDeploy create() {
        MavenDeploy mavenDeploy = new MavenDeploy()
        if (publishExtension.groupId != null) {
            mavenDeploy.group = publishExtension.groupId
        } else if (project.group != null) {
            mavenDeploy.group = project.group
        } else {
            throw new ProjectConfigurationException("missing group", null);
        }
        if (publishExtension.artifactId != null) {
            mavenDeploy.artifact = publishExtension.artifactId
        } else {
            mavenDeploy.artifact = project.name
        }

        mavenDeploy.url = publishExtension.url
        mavenDeploy.repository = publishExtension.repository
        mavenDeploy.user = publishExtension.user
        mavenDeploy.password = publishExtension.password
        mavenDeploy.version = variant.versionName
        mavenDeploy.packaging = 'apk'
        mavenDeploy.extension = 'apk'
        return mavenDeploy
    }
}
