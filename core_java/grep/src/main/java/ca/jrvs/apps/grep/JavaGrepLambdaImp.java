package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {

  /**
   * Override process with a nested call to stream function
   *
   * @throws IOException for invalid files
   */
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    String rootDir = this.getRootPath();
    List<File> files = this.listFiles(rootDir);

    for (File file : files) {
      // Make this a stream function call()
      matchedLines = this.processFiles(file);
    }
    this.writeToFile(matchedLines);
  }

  /**
   * New method to processFiles for matching patterns
   *
   * @param file to find matching pattern
   * @return list of matched files
   * @throws IOException for invalid files
   */
  public List<String> processFiles(File file) throws IOException {
    return Files.lines(Paths.get(file.getPath()))
        .filter(line -> line.matches(this.getRegex()))
        .collect(Collectors.toList());
  }

  /**
   * Override listFiles with Streams
   *
   * @param rootDir to retrieve valid files
   * @return list of valid files
   * @throws IOException for invalid file directory
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
   *
   * @param inputFile to retrieve lines
   * @return list of lines
   * @throws IOException invalid file
   */
  public List<String> readLines(File inputFile) throws IOException {
    // https://www.baeldung.com/java-8-streams
    // https://www.baeldung.com/java-8-collectors
    return Files.lines(Paths.get(inputFile.getPath())).collect(Collectors.toList());
  }

  /**
   * Override containsPattern with lambda uses String built-in method .matches
   *
   * @param line to assess if it contains pattern
   * @return if ture if line matches regex pattern
   */
  public boolean containsPattern(String line) {
    return line.matches(this.getRegex());
  }

  /**
   * Override writeToFile with streams
   *
   * @param lines to write into a file
   * @throws IOException if invalid filename
   */
  public void writeToFile(List<String> lines) throws IOException {
    // https://www.baeldung.com/java-append-to-file
    Files.write(Paths.get(this.getOutFile()), lines, StandardOpenOption.APPEND);
  }

}
