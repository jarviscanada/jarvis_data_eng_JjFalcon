package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

  private String regex;
  private String rootPath;
  private String outFile;
  private final Logger logger = LoggerFactory.getLogger(Wombat.class);

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<String>();
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
  public List<File> listFiles(String rootDir) {
    List<File> files = new ArrayList<File>();
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
  public List<String> readLines(File inputFile) {
    List<String> lines = new ArrayList<String>();

    try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
      for (String line; (line = br.readLine()) != null; ) {
        lines.add(line);
      }
    } catch (IOException e) {
      logger.error("Unable to read", e);
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

  public void main(String[] args) {
    
    if (args.length != 3) {
      logger.error("Insufficient Arguments");
    } else {
      JavaGrepImp javaGrep = new JavaGrepImp();
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
