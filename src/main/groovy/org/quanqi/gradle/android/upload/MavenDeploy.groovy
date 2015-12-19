package org.quanqi.gradle.android.upload

import com.squareup.okhttp.*

import java.util.concurrent.TimeUnit

/**
 * Post a single file to maven.
 */
class MavenDeploy {

    /**
     * Nexus api endpoint
     *
     * e.g. http://localhost:8081/nexus/service/local/artifact/maven/content
     */
    String url

    String user
    String password

    /**
     * g
     */
    String repository;

    /**
     * a
     */
    String group

    /**
     * v
     */
    String artifact

    /**
     * p
     */
    String version

    /**
     * c
     */
    String packaging

    /**
     * e
     */
    String extension

    /**
     * r
     */
    String classifier

    File file

    void deploy() {
        if (file == null ) return
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(10, TimeUnit.SECONDS)
        httpClient.setReadTimeout(60, TimeUnit.SECONDS)

        MultipartBuilder builder = new MultipartBuilder()
                .addFormDataPart('r', repository)
                .addFormDataPart('g', group)
                .addFormDataPart('a', artifact)
                .addFormDataPart('v', version)
                .addFormDataPart('p', packaging)
                .addFormDataPart('e', extension)
                .addFormDataPart('c', classifier)
                .addFormDataPart('file', file.name, RequestBody.create(MediaType.parse("maven/$packaging"), file))
                .addFormDataPart('hasPom', 'false')

        Request request = new Request.Builder().url(url)
                .addHeader("Authorization", Credentials.basic(user, password))
                .post(builder.build())
                .build()
        def response = httpClient.newCall(request).execute()
    }
}
