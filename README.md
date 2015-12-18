# Gradle Android Maven Publish Plugin

> This is a part of my CI system.

A plugin for publish your android variants apk and proguard mapping files to your maven repository.


## USAGE

In your `build.gradle` file
``` groovy

buildscript {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.0.0'
        classpath 'com.github.dodocat:gradle-android-maven-publish:0.1.0'
    }
}

apply plugin: 'org.quanqi.gradle.android-maven-publish'

```

View tasks
```
./gradlew task
```

run task

```
./gradle publishToMaven
```