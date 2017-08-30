package duplicate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class Finder {
	
	public static List<SearchFiles> listFiles = new ArrayList<SearchFiles>();
	public static int countListFiles = 0;
	public static int countDuplicateFiles = 0;
	public static int countMoveFiles = 0;
	private static String filter;
	
	public static void setListFiles(String source) throws IOException {
		
		Stream<Path> paths = Files.walk(Paths.get(source));
		paths.filter(Files::isRegularFile).forEach(file -> {
			try {
				filter = file.toString().toLowerCase();
				if(!filter.endsWith(".log") && !filter.endsWith(".log.lck") && !filter.endsWith(".csv")){
					countListFiles++;
					listFiles.add(new SearchFiles(file));
				}
			} catch (NoSuchAlgorithmException | IOException e) {
				e.printStackTrace();
			}
		});
		paths.close();	
		
		WriteLog.log("Total added files to List: " + countListFiles);
	}
	
	public static void findDuplicate() throws SecurityException, IOException{
		WriteLog.log(" *** Start searching the same cheksum *** ");
		//List<SearchFiles> listFiles = listFiles;
		for (int i = 0; i < listFiles.size(); i++) {
			for(int j = i + 1; j < listFiles.size(); j++){
				if(listFiles.get(j).getMarkDuplicate().contentEquals("0") && listFiles.get(i).getMd5sum().equals(listFiles.get(j).getMd5sum())){
					listFiles.get(j).setMarkDuplicate("1");
					countDuplicateFiles++;
					WriteLog.log("Finding -> source: " + listFiles.get(i).getFilePath() + 
							"\n	dest: " + listFiles.get(j).getFilePath() + "\n	checksum " + listFiles.get(j).getMd5sum());
				} 
			}
		}
		WriteLog.log("Total duplicate files: " + countDuplicateFiles);
		WriteLog.log("*** Completing the search for the same cheksum ***");
	}	
	
	public static void writeToFile(String filePath) throws IOException{
		
		FileWriter fileWriter = null;
		String HEADR = "file,path,dateAndTime,md5sum,duplicate";
		
		WriteLog.log("Create csv file ... ");
		fileWriter = new FileWriter(filePath);
		fileWriter.append(HEADR);
		fileWriter.append("\n");
		
		for (SearchFiles sf : listFiles) {
			fileWriter.append(sf.getFileName());
			fileWriter.append(",");
			fileWriter.append(sf.getFilePath());
			fileWriter.append(",");
			fileWriter.append(sf.getDateAndtime().toString());
			fileWriter.append(",");
			fileWriter.append(sf.getMd5sum());
			fileWriter.append(",");
			fileWriter.append(sf.getMarkDuplicate());
			fileWriter.append("\n");
		}
		
		fileWriter.flush();
		fileWriter.close();
		WriteLog.log("CSV file was created");
	}
	
	public static void moveFiles(String source, String target) throws SecurityException, IOException {
		WriteLog.log("Moving files ...");
		for(SearchFiles sf : listFiles){
			 if(sf.getMarkDuplicate().contentEquals("1")){	
				 	WriteLog.log("File name " + sf.getFile().getFileName());
					
					File destPath = new File(sf.getFile().toString().replace(source, target));	
		
					if(!destPath.exists()){
						destPath.getParentFile().mkdirs();
					}
				
					try {
						WriteLog.log("Start moving file" + sf.getFile() + " -> " + destPath);
						Files.move(sf.getFile(), destPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
						countMoveFiles++;
						WriteLog.log("Finished moving file");
					} catch (IOException e) {
						WriteLog.log("IOException while moving file " +  e);
						e.printStackTrace();
					}
			 }
		}
		WriteLog.log("Total moved files: " + countMoveFiles);
	}	
	
}
