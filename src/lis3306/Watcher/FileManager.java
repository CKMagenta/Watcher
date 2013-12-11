package lis3306.Watcher;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.InputStream;


public class FileManager {
	/*
	public static List<FileMeta> uploadByApacheFileUpload(HttpServletRequest request) throws IOException, ServletException{
		 
		List<FileMeta> files = new LinkedList<FileMeta>();
 
		// 1. Check request has multipart content
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		FileMeta temp = null;
 
		// 2. If yes (it has multipart "files")
		if(isMultipart){
 
			// 2.1 instantiate Apache FileUpload classes
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
 
			// 2.2 Parse the request
			try {
 
				// 2.3 Get all uploaded FileItem
				List<FileItem> items = upload.parseRequest(request);
				//String twitter = "";
 
				// 2.4 Go over each FileItem
				for(FileItem item:items){
 
					// 2.5 if FileItem is not of type "file"
					if (item.isFormField()) {
 
						// 2.6 Search for "twitter" parameter
						//if(item.getFieldName().equals("twitter"))
						//	twitter = item.getString();
 
					} else {
 
						// 2.7 Create FileMeta object
						temp = new FileMeta();
						temp.setFileName(item.getName());
						temp.setContent(item.getInputStream());
						temp.setFileType(item.getContentType());
						temp.setFileSize(item.getSize()/1024+ "Kb");
 
						// 2.7 Add created FileMeta object to List<FileMeta> files
						files.add(temp);
 
					}
				}
 
				// 2.8 Set "twitter" parameter 
				//for(FileMeta fm:files){
				//	fm.setTwitter(twitter);
				//}
 
			} catch (FileUploadException e) {
				//e.printStackTrace();
			}
		}
		return files;
	}
 
	// this method is used to get file name out of request headers
	// 
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}
	
	public static class FileMeta {
		private String fileName;
		private String fileSize;
		private String fileType;
		private String twitter;
 
		private InputStream content;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileSize() {
			return fileSize;
		}

		public void setFileSize(String fileSize) {
			this.fileSize = fileSize;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}

		public InputStream getContent() {
			return content;
		}

		public void setContent(InputStream content) {
			this.content = content;
		}
		 
		

	}
	*/
}

