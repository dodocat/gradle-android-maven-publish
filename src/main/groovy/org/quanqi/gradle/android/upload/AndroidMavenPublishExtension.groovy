package org.quanqi.gradle.android.upload

/**
 * Created by cindy on 12/18/15.
 */
class AndroidMavenPublishExtension {

    public static final String NAME = 'androidMavenPublish'
    String url = "http://localhost:8081/nexus/service/local/artifact/maven/content"
    String user = 'admin'
    String password = 'admin123'
    String repository = 'ci'
    String groupId
    String artifactId
    String version
}
