# Google Cloud GraalVM Support

[![experimental](http://badges.github.io/stability-badges/dist/experimental.svg)](http://github.com/badges/stability-badges)

This repository provides support for applications using the [Google Java Client Libraries](https://github.com/googleapis/google-cloud-java) to be built as [GraalVM Native Images](https://www.graalvm.org/reference-manual/native-image/).

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

### Client Library Versions

To get your application working with GraalVM compilation, you will also have to ensure that the client library versions in your project are supported by your chosen version of `google-cloud-graalvm-support`.

| GraalVM Support version | *`libraries-bom` version | `grpc-netty-shaded` version |
|-------------------------|:-------------------------|-----------------------------|
| `1.0.0-SNAPSHOT`        | `>= 11.0.0`              | `>= 1.32.1`                 |

**NOTE:** Most users typically manage their client library versions using the [Cloud Libraries Bill of Materials](https://github.com/GoogleCloudPlatform/cloud-opensource-java/wiki/The-Google-Cloud-Platform-Libraries-BOM) (`libraries-bom`).
The `libraries-bom` manages the version of `grpc-netty-shaded` for you as well so you don't have to manage it yourself.

## Supported Libraries

This project should support many Java Google Client Libraries out-of-the-box.

Our project has [code samples](https://github.com/GoogleCloudPlatform/google-cloud-graalvm-support/tree/master/google-cloud-graalvm-samples) demonstrating compatibility with the following APIs:

| Google Cloud Service    | Sample Link              | 
|-------------------------|--------------------------|
| [Cloud Pub/Sub](https://github.com/googleapis/java-pubsub) | [google-cloud-graalvm-pubsub-sample](https://github.com/GoogleCloudPlatform/google-cloud-graalvm-support/tree/master/google-cloud-graalvm-samples/google-cloud-graalvm-pubsub-sample) |

## Questions

Please report any issues and questions to our [Github Issue Tracker](https://github.com/GoogleCloudPlatform/google-cloud-graalvm-support/issues).
