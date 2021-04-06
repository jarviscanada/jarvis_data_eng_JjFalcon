# Java Implementation of the GNU grep

The JavaGrep is a tool that simulates the GNU grep command that searches all
files recursively from a given directory for lines that match the given regex pattern
and outputs them to a file.  This is achieved using Java's Regex classes (Pattern and Matcher),
and the java.nio library.

It utilizes the stream libraries to process the collection of objects in the program and
demonstrates the use of intermediate and terminal operations.

The program also takes advantage of lambda expressions to simplify the code
and make it more readable.

## Quick Start
The application can be run via Docker
```
   $ docker pull jjfalcon/grep
   $ docker run --rm -v {rootPath} -v {outFile} jjfalcon/grep {regex} {rootPath} {outFile}
```
# Implementation
## JavaGrep Pseudocode
```
Recursively go through the root directory, get a list of files in it.
For each valid file found,
    For each line in the file,
        Check if the line contains the Regex pattern,
        If the pattern is found, write the line to the output file.
```

## Performance Issues
The program stores all the lines from all the files into a single collection.
If there are a huge amount of files in the directory each containing huge content that single collection can get very big.  
Consider a more efficient way to store those lines.

This program assumes that each file contains simple text lines.  
This program will run slowly if a file is large and contains structured data.  
Consider advanced search algorithms to handle complex files.

# Test
Tested from the local machine using 2 files grepTestCase01.txt and grepTestCase02.txt.
Both files contain lines with the target text "dog" both as a standalone and part of a word ie; hotdog.
Ran the program via the Docker image and confirmed expected test results.

[/data:](https://github.com/jarviscanada/jarvis_data_eng_JjFalcon/tree/develop/core_java/grep/data)
contains the test data  
[/log:](https://github.com/jarviscanada/jarvis_data_eng_JjFalcon/tree/develop/core_java/grep/log)
contains the output

# Deployment
The application was bundled using Docker and pushed to Docker hub.
The image can be downloaded to a local machine and ran in a container.

# Improvement
- Inclusion of filename + line number on matched lines
- Option to specify file types and matching search algorithms
- To address possible memory usage issues, files containing a huge amount of lines can be batched for processing

