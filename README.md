# Barren Land Analysis

Author: Todd Becicka

## Getting Started

### Prerequisites

* Install [Java JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)


### Installing/Building

Unzip the BarrenLandAnalysis.zip file.  
Open a console and navigate to the unzipped directory.  
(Note all commands were run in Windows consoles.  Syntax may vary slightly on Linux, specifically the backslash).  
Execute the following command (The first time it is built Gradle will be downloaded):

```
.\gradlew build
```

## Running the application

To run the application, run the following Gradle command.

```
.\gradlew run
```

You will be prompted in the console to provide input (You may also see output from Gradle.)

```
Enter the set of rectangles:
```

Enter the rectangles in the format specified in the case study:

```
{"48 192 351 207", "48 392 351 407", "120 52 135 547", "260 52 275 547"} 
```

The output will be the fertile areas sorted in ascending order.

```
22816 192608
```

## Running the tests

To execute the unit tests enter the following command.

```
.\gradlew test
```

The result of the tests will be printed to the console

```
com.becicka.barrenlandanalysis.FarmTest > testNoBarrenRectangles PASSED
```

Because Gradle only executes tasks when a change is detected, the following command is needed between successive calls to execute the tests. 

```
.\gradlew clean
```








