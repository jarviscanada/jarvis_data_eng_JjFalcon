package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {

  /**
   * Override process with Parallel Stream
   * @throws IOException
   */
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<String>();
    String rootDir = this.getRootPath();
    List<File> files = this.listFiles(rootDir);

    for (File file : files) {
      // Make this a stream function call()
      matchedLines = this.processFiles(file);
    }
    this.writeToFile(matchedLines);
  }

  /**
   *
   * @param file
   * @return
   * @throws IOException
   */
  public List<String> processFiles(File file) throws IOException {
    return Files.lines(Paths.get(file.getPath()))
        .filter(line -> line.matches(this.getRegex()))
        .collect(Collectors.toList());
  }

  /**
   * Override listFiles with Streams
   * @param rootDir
   * @return
   * @throws IOException
   */
  public List<File> listFiles(String rootDir) throws IOException {
    // https://www.baeldung.com/java-list-directory-files
    // walk returns a Stream<Path>
    try (Stream<Path> stream = Files.walk(Paths.get(rootDir), 3)) {
      return stream
          .filter(file -> !Files.isDirectory(file))
          // returns a Path object
          .map(Path::getFileName)
          // converts it to File
          .map(Path::toFile)
          .collect(Collectors.toList());
    }
  }

  /**
   * Override readLines with streams
   * @param inputFile
   * @return
   * @throws IOException
   */
  public List<String> readLines(File inputFile) throws IOException {
    // https://www.baeldung.com/java-8-streams
    // https://www.baeldung.com/java-8-collectors
    return Files.lines(Paths.get(inputFile.getPath())).collect(Collectors.toList());
  }

  /**
   * Override containsPattern with lambda
   * uses String built-in method .matches
   * @param line
   * @return if line matches regex pattern
   */
  public boolean containsPattern(String line) {
    return line.matches(this.getRegex());
  }

  /**
   * Override writeTofile with streams
   * @param lines
   * @throws IOException
   */
  public void writeToFile(List<String> lines) throws IOException {
    // https://www.baeldung.com/java-append-to-file
    // ***********************************************************
    Files.write(Paths.get(this.getOutFile()), lines, StandardOpenOption.APPEND);
  }

  public static void main(String[] args) {
    JavaGrepImp javaGrep = new JavaGrepImp();
    if (args.length != 3) {
      logger.error("Insufficient Arguments");
    } else {
      javaGrep.setRegex(args[0]);
      javaGrep.setRootPath(args[1]);
      javaGrep.setOutFile(args[2]);
    }

    try {
      javaGrep.process();
    } catch (IOException e) {
      logger.error("Unable to read", e);
    }
  }
}
