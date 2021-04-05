package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  private String regex;
  private String rootDir;
  private String outputFile;
  private Logger logger;

  public JavaGrepImp() {
    logger = LoggerFactory.getLogger(JavaGrepImp.class);
  }

  /**
   * runs the program
   *
   * @throws IOException when file operations fail
   */
  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();
    List<File> files = this.listFiles(rootDir);

    for (File file : files) {
      List<String> lines = this.readLines(file);
      for (String line : lines) {
        if (this.containsPattern(line)) {
          matchedLines.add(file.getName() + ": " + line);
        }
      }
    }
    writeToFile(matchedLines);
  }

  /**
   * Traverse a given directory and return all files.
   *
   * @param rootDir input directory
   * @return all valid files
   */
  @Override
  public List<File> listFiles(String rootDir) throws IOException {
    List<File> files = new ArrayList<>();
    File directory = new File(rootDir);

    if (directory.isDirectory()) {
      // Get all files from a directory.
      File[] currentFiles = directory.listFiles();
      if (currentFiles != null) {
        for (File file : currentFiles) {
          if (file.isFile()) {
            files.add(file);
          } else if (file.isDirectory()) {
            files.addAll(listFiles(file.getAbsolutePath()));
          }
        }
      }
    }
    return files;
  }

  /**
   * Read a file and return all the lines.
   *
   * @param inputFile file to be read
   * @return all the lines
   * @throws IllegalArgumentException if a given inputFile is not a file
   */
  @Override
  public List<String> readLines(File inputFile) throws IOException {
    List<String> lines = new ArrayList<>();
    // Try to read one line then, while the current line isn't null,
    // add it to the list and get the next, otherwise return
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      String line = reader.readLine();
      while (line != null) {
        lines.add(line);
        line = reader.readLine();
      }
    } catch (IOException e) {
      logger.info("ERROR: " + e.getMessage());
    }
    return lines;
  }

  /**
   * check if there is a match to the regex pattern inputted by user
   *
   * @param line input string
   * @return true if there is a match
   */
  @Override
  public boolean containsPattern(String line) {
    return Pattern.compile(regex).matcher(line).find();
  }

  /**
   * Write lines to a file.
   * <p>
   * Explore: FileOutputStream, OutputStreamWriter, and BufferedWriter
   *
   * @param lines matched line
   * @throws IOException if write failed
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

    for (String line : lines) {
      bw.write(line + "\n");
    }
    bw.close();
  }

  @Override
  public String getRootPath() {
    return rootDir;
  }

  @Override
  public void setRootPath(String rootPath) {
    rootDir = rootPath;
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return outputFile;
  }

  @Override
  public void setOutFile(String outFile) {
    outputFile = outFile;
  }

  // move main method to a viewer
}
