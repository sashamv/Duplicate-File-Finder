package duplicate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.NoSuchAlgorithmException;

public class SearchFiles {
	
	Path file;
	String fileName;
	String filePath;
	FileTime dateAndtime;
	String md5sum;
	String markDuplicate;
	
	public SearchFiles(Path file) throws IOException, NoSuchAlgorithmException {
		this.file = file;
		
		this.fileName = file.getFileName().toString();
		
		this.filePath = file.toString();
		
		BasicFileAttributes fileAttributes = Files.readAttributes(file, BasicFileAttributes.class);
		this.dateAndtime = fileAttributes.creationTime();
				
		this.md5sum = Finder.checkSum(file);

		
		this.markDuplicate = "0";
		
		WriteLog.log(Finder.countListFiles + " - " + this.filePath + " - " + this.md5sum);
	}
	
	public Path getFile() {
		return file;
	}
	public void setFile(Path file) {
		this.file = file;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFilePath() {
		
		return filePath;
	}
	
	public FileTime getDateAndtime() {
		return dateAndtime;
	}
	
	public String getMd5sum() {
		return md5sum;
	}
	
	public void setMarkDuplicate(String b){
		this.markDuplicate = b;
	}
	
	public String getMarkDuplicate(){
		return this.markDuplicate;
	}

	@Override
	public String toString() {
		return "SearchFiles [file=" + file + ", fileName=" + fileName + ", filePath=" + filePath + ", dateAndtime="
				+ dateAndtime + ", md5sum=" + md5sum + "]";
	}

}
