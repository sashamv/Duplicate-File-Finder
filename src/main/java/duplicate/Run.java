package duplicate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Run {

	public static void main(String[] args) throws SecurityException, IOException {
		String SOURCE;
		String TARGET;
		
		if (args.length > 0 && args[0].contentEquals("-s") && args[2].contentEquals("-t")){
			SOURCE = args[1];
			TARGET = args[3];
		}else if (args.length > 0 && args[0].contentEquals("-h")) {
			System.out.println("Usage: duplicate [-s source] [-t target] ");
			System.exit(1);
			return;			
		}else {
			String mesage = "invalid option: duplicate ";
			for(String arg : args){
				mesage = mesage + arg + " ";
			}
			System.out.println(mesage);
			System.out.println("Usage: duplicate [-s source] [-t target] ");
			System.exit(1);
			return;
		}
		
		//SOURCE = "/run/media/ominin/Data/deleteme";
		//TARGET = "/run/media/ominin/Data/deleteme_dest";	
		
		SimpleDateFormat sf = new SimpleDateFormat ("yyyy-MM-dd-'t'HH-mm-ss");
		String timeStamp = sf.format(new Date());
		WriteLog.init(SOURCE + "/duplicate-" + timeStamp + ".log");
		
		WriteLog.log("*** Start ***");
		
		Finder.setListFiles(SOURCE);
		Finder.findDuplicate();
		Finder.moveFiles(SOURCE, TARGET);
		
		WriteLog.log("*** End ***");

	}

}
