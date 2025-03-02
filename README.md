# CronParser

## Overview

main.CronParser is a Java-based utility for parsing and interpreting cron expressions. It takes a cron schedule string as input and parses its various time fields.

## Prerequisites

- JDK 8 must be installed on your system. If not, download and install it.
- Ensure your environment variables are correctly set for `JAVA_HOME` and `PATH`.

## Setup Instructions

1. Clone or download the repository.
2. Ensure that the necessary JUnit dependencies are inside the `lib/` directory.

## Compilation

To compile the project, run:

```bash
javac --release 8 -cp "lib/*;src" -d out src/main/*.java src/test/*.java
```

## Running the Parser

To execute the main.CronParser with a sample cron expression, use:

```bash
java -cp "lib/*;out" main.CronParser "*/15 0 1,15 * 1-5 abcd"
```

## Expected Output Format

When the parser runs successfully, the output will be formatted as follows:

```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       abcd
```

## Running Tests

To execute JUnit tests, use the following command:

```bash
java -cp "lib/*;out" org.junit.runner.JUnitCore test.CronParserTest
```
