package duplicate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class WriteLog {
	static Logger logger;
	
	public WriteLog(String logFile) throws SecurityException, IOException{
		 logger = Logger.getLogger("duplicate");
		 logger.setUseParentHandlers(false);
		 
		 DuplicateFormater formatter = new DuplicateFormater();
		 ConsoleHandler consoleHandler = new ConsoleHandler();
		 FileHandler fileHandler = new FileHandler(logFile, true);
		 
		 logger.addHandler(fileHandler);
		 logger.addHandler(consoleHandler);
		 fileHandler.setFormatter(formatter);
		 consoleHandler.setFormatter(formatter); 
	}
	
	public static void init(String logFile) throws SecurityException, IOException{
		new WriteLog(logFile);
	}
	public static void log(String s) throws SecurityException, IOException{
    	logger.log(Level.INFO, s);
	
	}
	
 public class DuplicateFormater extends Formatter{
	 private final DateFormat df = new SimpleDateFormat("yyyy MM dd HH:mm:ss.SSS");
		@Override
		public String format(LogRecord record) {
			StringBuffer sb = new StringBuffer();
			sb.append("[").append(df.format(new Date())).append("]");
			//sb.append("[").append(record.getSourceClassName()).append("]");
			//sb.append("[").append(record.getSourceMethodName()).append("]");
			sb.append("[").append(record.getMessage()).append("]");
			sb.append("\n");
			return sb.toString();
		}
		
	}
}
