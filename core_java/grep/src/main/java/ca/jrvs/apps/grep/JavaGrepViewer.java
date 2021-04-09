package ca.jrvs.apps.grep;

import java.io.IOException;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepViewer {

  private final static Logger LOGGER = Logger.getLogger(JavaGrepViewer.class.getName());

  public static void main(String[] args) {
    org.slf4j.Logger logger = LoggerFactory.getLogger("Main");
    if (args.length != 3) {
      throw new IllegalArgumentException("Insufficient Arguments");
    }
    JavaGrepImp javaGrep = new JavaGrepImp();
    javaGrep.setRegex(args[0]);
    javaGrep.setRootPath(args[1]);
    javaGrep.setOutFile(args[2]);

    try {
      javaGrep.process();
    } catch (IOException e) {
      logger.info("ERROR: " + e.getMessage());
    }
  }

}
