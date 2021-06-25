# video-playback

***
Simple os-independent framework for working with video feeds. Includes support for both file and webcam stream, implements multi-threaded processing.

## Installation

Make sure you have Gradle 6.x installed on your computer (test environment: Gradle 6.7)

```
gradle --version
```

If gradle isn't installed on your computer, use homebrew for installation on your mac

```
brew install gradle
```

If homebrew isn't installed on your computer (mac or linux), you can install it from [here](https://brew.sh/). If your os does not
support homebrew, Gradle installation instructions can be found [here](https://gradle.org/install/).

This project requires opencv for java, refer
to [this page](https://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html) for
installation instructions. Once the installation is complete, make note of the directory containing dependency files ```.dll, .dylib, .jar```.

After installation on my mac, I can find these files here:
```
/usr/local/Cellar/opencv/4.5.2_4/share/java/opencv4/
```
We need the jar file located in this directory: ```opencv-452.jar```.

Clone this repository and navigate to the created directory
```
git clone https://github.com/roshanprabhakar/video-playback.git
cd video-playback
```

open the build.gradle file
