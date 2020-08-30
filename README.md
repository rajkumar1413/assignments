# Welcome to Zip file Password crack (Brute Force)

## Installation

Add the zip4j dependency in the pom.xml file 

```bash
<!-- https://mvnrepository.com/artifact/net.lingala.zip4j/zip4j -->
 <dependency>
            <groupId>net.lingala.zip4j</groupId>
            <artifactId>zip4j</artifactId>
            <version>1.3.2</version>
        </dependency>
```

## Changes required
In BruteForce.java file have to change the fileName and destination path to extract the files.

```Java8

//  Change the fileName and destination path as per yours needs
    private final String fileName = "D:\\Meyvan\\sample\\assignment.zip";
    private final String destPath = "D:\\Meyvan\\sample\\assignment\\data_template";
```
## Output

```Java8
Password cracked in 59.59705 sec. Password is = Twg48wTMSu. Check the directory D:\Meyvan\sample\assignment\data_template zip files are extracted successfully... : )
```
