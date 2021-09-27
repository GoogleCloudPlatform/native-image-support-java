# Releasing Guide

All scripts can be found in the .kokoro directory.

# Snapshots

A commit to the master branch will automatically trigger the `prod:cloud-java-frameworks/native-image-support/continuous` job that will publish snapshots to Sonatype Snapshots repository.

# Releases

1. Run the prod:cloud-java-frameworks/native-image-support/stage Kokoro job.

    In the build logs, find the ID of the staging repository. You should see a log line that looks something like this:

    ```
    [INFO]  * Created staging repository with ID "comgooglecloud-1416".
    The {STAGING_ID} in this case is comgooglecloud-1416.
    ```

2. Verify staged artifacts by going to `https://google.oss.sonatype.org/content/repositories/{STAGING_ID}/com/google/cloud/`

    **Note:** You can also view all staged artifacts at http://oss.sonatype.org.
    
    To get access to com.google.cloud group ID, please make a request similar to [this one](https://issues.sonatype.org/browse/OSSRH-52371) and ask someone who already has access to this group ID to vouch for you in the issue comments.)

3. If you want to drop the staged artifacts, `run the prod:cloud-java-frameworks/native-image-support/drop` Kokoro job, while providing the staging repository ID as an environment variable like `STAGING_REPOSITORY_ID=comgooglecloud-1416`.

4. If you want to release the staged artifacts, run the `prod:cloud-java-frameworks/native-image-support/promote` Kokoro job, while providing the staging repository ID as an environment variable like `STAGING_REPOSITORY_ID=comgooglecloud-1416`.

5. Verify that the new version has been published to Maven Central by checking [here](https://repo.maven.apache.org/maven2/com/google/cloud/native-image-support/). This might take a while.

6. Create a [new release](https://github.com/GoogleCloudPlatform/native-image-support/releases) on GitHub.

7. Increment the project base version. For example, from 1.0.0-SNAPSHOT to 1.1.0-SNAPSHOT.
