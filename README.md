# video-playback

***
Simple os-independent framework for working with video feeds. Includes support for both file and webcam stream,
implements multi-threaded processing.

<!-- ![demo](./vp-demo.gif) -->

## Project Structure

This project specifies a framework by which single frames of video are read from a Video feed, sent through a video
processor, and queued for rendering.

Currently, processing consists of applying a filter to a single frame of video. Processors handle every video frame in
its entirety and do not implement any form of delta-series compression, so multi-threaded support has been added to
accelerate the video altering process (each processor runs on its independent thread, all processors feed output to the
same renderer).

An api has been implemented for creating filters, but a lower level development is necessary should inter-frame data be
useful. This project deals with frames as 2D integer arrays or as OpenCV Mat objects, thus opencv features are available
for our use in integration with this project.

## Installation

This project requires Java Corretto-15.

You can check your java version by running

```
java --version
```

If you do not have Java 15 installed, you can find
it [here](https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html). Be sure to download the
appropriate version for your computer architecture. Once downloaded, you can switch your Java version to 15 using the
following command

```
export JAVA_HOME="/usr/libexec/java_home -v 15.x.x"
```

Replace ```15.x.x``` with your downloaded java version.

Once completed, verify you are using java 15 by running ```java --version```

Next make sure you have Gradle 6.x installed on your computer (test environment: Gradle 6.7)

```
gradle --version
```

If gradle isn't installed on your computer, use homebrew for installation on your mac

```
brew install gradle
```

If homebrew isn't installed on your computer (mac or linux), you can install it from [here](https://brew.sh/). If your
os does not support homebrew, Gradle installation instructions can be found [here](https://gradle.org/install/).

This project requires opencv for java, refer
to [this page](https://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html) for
installation instructions. Once the installation is complete, make note of the directory containing dependency
files ```.dll, .dylib, .jar```. The contained jar file will be needed later.

After installation on my mac, I can find these files here:

```
/usr/local/Cellar/opencv/4.5.2_4/share/java/opencv4/
```

From that directory we need this jar file: ```opencv-452.jar```.

Next navigate to the location where you would like to keep this project and clone the repository

```
git clone https://github.com/roshanprabhakar/video-playback.git
cd video-playback
```

Open the build.gradle file. Change the opencv dependency location from

```
/usr/local/Cellar/opencv/4.5.2_4/share/java/opencv4/opencv-452.jar
``` 

to the absolute path of the jar we downloaded during opencv installation (```opencv-xxx.jar```)

Now we are ready to build the project :)\
Navigate into video-playback and run a gradle build

```
./gradlew build
```

In the case of a permission denied, give executable permissions to the ```gradlew``` file

```
chmod +x ./gradlew
```

and run the build sequence again. the chmod command is only available for mac and linux, contact either me or Ganesh if
you have issues running the gradlew executable on a non mac or linux machine.

To run the program, execute the created jar program while adding the opencv jar home directory to the java library path.
Because my jar was located at ```/usr/local/Cellar/opencv/4.5.2_4/share/java/opencv4/```, this is the directory I will
specify in my execution. Change this value in the below run command if your opencv build contained the necessary jars in
a different directory.

```
java -jar -Djava.library.path="/usr/local/Cellar/opencv/4.5.2_4/share/java/opencv4/" ./build/libs/video-playback-1.0-SNAPSHOT.jar
```

The program should initialize webcam feed, load a pre-implemented edge detector filter, and display the processed feed
to a render window on your screen.
