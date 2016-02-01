This App is extended from [2048](https://github.com/gabrielecirulli/2048), thanks to gabrielecirulli !

the Project has been migrated from maven to gradle... and up to now (version 0.0.2), it still has some bugs..

changed: removed large amount of redundant code, replaced Spring integration (Spring MVC, Spring, Tomcat) with **SpringBoot**

## install

### Preparation

if you have not gradle, don't worry, you can execute the build using one of the following commands from the root of the project:

```bash
# on Unix-like platforms such as Linux and Mac OS X
./gradlew bootRun

# on Windows using the gradlew.bat batch file
gradlew bootRun
```

### Build an executable JAR

You can run the application without building

```bash
$ gradle bootRun
```

You might want to use this useful operating system environment variable:

```bash
$ export JAVA_OPTS=-Xmx1024m -XX:MaxPermSize=128M -Djava.security.egd=file:/dev/./urandom
```

or...

You can build a single executable JAR file that contains all the necessary dependencies, classes, and resources.

```bash
./gradlew clean build
```

Then you can run the JAR file:

```bash
java -jar build/libs/LoftPage-0.0.2.jar
```

## Animation (gif file，please wait!)：

![](https://github.com/decaywood/LoftPage/blob/master/Info/2048.gif)

default port is 4000, when App is running, input {your server IP}:4000 in your browser.

enjoy!
