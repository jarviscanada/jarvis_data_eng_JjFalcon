package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

  private String regex;
  private String rootPath;
  private String outFile;
  private final static Logger LOGGER = Logger.getLogger(JavaGrepViewer.class.getName());

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();

    String rootDir = this.getRootPath();
    List<File> files = this.listFiles(rootDir);

    for (File file : files) {
      List<String> lines = this.readLines(file);
      for (String line : lines) {
        if (this.containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }
    this.writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) throws IOException {
    List<File> files = new ArrayList<>();
    File directory = new File(rootDir);

    // Get all files from a directory.
    File[] currentFiles = directory.listFiles();

    // Only adds valid files
    if (currentFiles != null) {
      for (File file : currentFiles) {
        if (file.isFile()) {
          files.add(file);
        } else if (file.isDirectory()) {
          listFiles(file.getAbsolutePath());
        }
      }
    }
    return files;
  }

  @Override
  public List<String> readLines(File inputFile) throws IOException {
    List<String> lines = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
      for (String line; (line = br.readLine()) != null; ) {
        lines.add(line);
      }
    } catch (IOException e) {
      LOGGER.info("Unable to read");
    }

    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    String pattern = this.getRegex();
    Matcher m = Pattern.compile(pattern).matcher(line);

    return (m.find());
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    String outFile = this.getOutFile();
    FileWriter fw = new FileWriter(outFile);

    for (String line : lines) {
      fw.write(line);
    }
    fw.close();
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
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
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

}
