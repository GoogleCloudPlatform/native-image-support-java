# Google Cloud GraalVM Support

[![experimental](http://badges.github.io/stability-badges/dist/experimental.svg)](http://github.com/badges/stability-badges)

This repository provides support for applications using the [Google Java Client Libraries](https://github.com/googleapis/google-cloud-java) to be built as [GraalVM Native Images](https://www.graalvm.org/docs/reference-manual/native-image).

## Quick Setup

First, add the Sonatype Snapshots repository to your project build file.

```
<repositories>
    <repository>
        <id>snapshots-repo</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        <releases><enabled>false</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>
```

After adding the snapshots repository, you can add the `google-cloud-graalvm-support` dependency to your project.

```
<dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>google-cloud-graalvm-support</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

This dependency contains the GraalVM configurations (reflection config, native call config) to provide out-of-the-box support for native-image compilation of applications depending on the Google Java Client Libraries.

### Note on grpc-netty-shaded version

As an additional step, you will also need to override the version of `grpc-netty-shaded` to version `1.32.1` (or later):

```
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>io.grpc</groupId>
        <artifactId>grpc-netty-shaded</artifactId>
        <version>1.32.1</version>
      </dependency>
    </dependencies>
  </dependencyManagement>
```

The Java client libraries import this dependency to your project automatically as a transitive dependency.
The latest version of `grpc-netty-shaded` contains a patch to enable native image compilation, so this version must be (temporarily) manually upgraded for native image compilation to work.

## Samples

Sample projects can be found in the [google-cloud-graalvm-samples](https://github.com/GoogleCloudPlatform/google-cloud-graalvm-support/tree/master/google-cloud-graalvm-samples) directory.

## Supported Libraries

The project currently supports the following Google Client Libraries:

* [Cloud Pub/Sub](https://github.com/googleapis/java-pubsub)
