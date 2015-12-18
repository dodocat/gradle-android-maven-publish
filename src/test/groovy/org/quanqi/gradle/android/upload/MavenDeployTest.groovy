package org.quanqi.gradle.android.upload

/**
 * Created by cindy on 12/18/15.
 */
class MavenDeployTest extends GroovyTestCase {
    void testDeploy() {
        MavenDeploy mavenDeploy = new MavenDeploy()
        mavenDeploy.repository = 'ci'
        mavenDeploy.group = 'org.quanqi'
        mavenDeploy.artifact = 'helios'
        mavenDeploy.version = '0.0.1'
        mavenDeploy.classifier = 'testc'
        mavenDeploy.extension = 'apk'
        mavenDeploy.packaging = 'apk'
        mavenDeploy.user = 'admin'
        mavenDeploy.password = 'admin123'
        File file = new File('settings.gradle')
        mavenDeploy.file = file;
        mavenDeploy.url = 'http://localhost:8081/nexus/service/local/artifact/maven/content'
        mavenDeploy.deploy()
    }
}
