# Unemployment Rate Writer

Unemployment Rate Writer is simple application for presenting extremes unemployment rates of countries or areas in different years written in Java. 

It is able to load data in json-stat format from url or local file and create order of unemployment rates. 
This application was developed for Tieto company as task for testing my Java skills.

Author: Lukáš Ondrák, 2020
## Usage
To run this maven project, use JDK11.

Attributes of program: 
```
    [url, count of printed areas]
```
If attributes are not provided, program will use these default values:
```
    url of file: https://json-stat.org/samples/oecd.json
    count of printed areas: 3
```
