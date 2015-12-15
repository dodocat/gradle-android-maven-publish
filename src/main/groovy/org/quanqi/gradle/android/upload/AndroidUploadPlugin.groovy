package org.quanqi.gradle.android.upload

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.internal.artifacts.publish.DefaultPublishArtifact
import org.gradle.api.plugins.MavenPlugin
import org.gradle.api.plugins.PluginContainer

/**
 * Created by cindy on 12/14/15.
 */
class AndroidUploadPlugin implements Plugin<Project> {

    static boolean checkPlugins(Project project) {
        PluginContainer plugins = project.getPlugins()
        return (plugins.hasPlugin(AppPlugin)
                || plugins.hasPlugin(LibraryPlugin)) && plugins.hasPlugin(MavenPlugin)
    }

    @Override
    void apply(Project project) {
        if (!checkPlugins(project)) {
            throw new ProjectConfigurationException("The android plugin must be applied to the project", null)
        }

        AppExtension android = project.android
        println("product flavors:")
        android.productFlavors.each {
            println "flavor:" + it.name + ";"
        }
        println("product buildTypes")
        android.buildTypes.each {
            println "buildType:" + it.name + ';'
        }
        project.artifacts.collect {clear()}

        android.applicationVariants.all { ApplicationVariant variant ->
            println("variant: " + variant.name)
            variant.outputs.each {
                File file = it.outputFile.absoluteFile
                println("output:" + file)
                if (file.name.endsWith('apk')) {
                    project.artifacts {
                        variant.flavorName
                        archives new DefaultPublishArtifact(project.name, "apk", "apk", variant.name, new Date(), file)
                    }
                }
            }
        }
    }
}
