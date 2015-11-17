package com.toshko.photoalbum.data;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

public class UserUtils {
	
	private static final String CURRENT_USER = "CURRENT_USER";

	public static String getCurrentUser(HttpSession aSession) {
		String currentUser = (String)aSession.getAttribute(CURRENT_USER);
		return currentUser;
	}
	
	public static void setCurrentUser(HttpSession aSession, String aUserName) {
		aSession.setAttribute(CURRENT_USER, aUserName);
	}
	
	public static Date getCurrentTime(){
		java.util.Date now = new java.util.Date();
		Date date = new Date(now.getTime());
		return date;
	}
	
	private static File getPicturesRoot(){
		File result = new File("C:\\webhome_eclipse_workspace\\PhotoAlbum\\dbPictures");
		result.mkdirs();
		return result;
	}
	public static File getPicturePath(int userId, int categoryId, int imageId){
		File root = getPicturesRoot();
		File imageFile = new File(String.format("%s/%s/%s/%s", root, userId, categoryId, imageId) + ".jpg");
		return imageFile;
	}
	
	public static File[] getPicturesPathByCategory(int userId, int categoryId, int imageId){
		File root = getPicturesRoot();
		File categoryPath = new File(String.format("%s/%s/%s/", root, userId, categoryId));
		File dir = new File(categoryPath.getAbsolutePath());
		File[] directoryImagesListing = dir.listFiles();
		return directoryImagesListing;
	}
	public static byte[] loadPicture(int userId, int categoryId, int imageId){
		File imageFile = getPicturePath(userId, categoryId, imageId);
		
		byte[] fileContent = new byte[(int)imageFile.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(imageFile);
			fis.read(fileContent);
		} catch (IOException ex) {
			System.out.println("IOException: " + ex);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return fileContent;
	}

	public static int savePicture(int userId, int categoryId, int imageId,
			byte[] content) {
		File imageFile = getPicturePath(userId, categoryId, imageId);
		imageFile.mkdirs();
		imageFile.delete();
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(imageFile + ".jpg");
			fos.write(content);
		} catch (IOException ex) {
			System.out.println("IOException: " + ex);
		} finally {
			if (fos != null) {
				try {
					fos .close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (imageFile.exists()) {
			compressImageFile(imageFile);
			return (int) imageFile.length();
		}
		
		return -1;
	}
	
	private static void compressImageFile(File imageFile) {
		try{
			File origFile = new File(imageFile.getParent(), imageFile.getName() + "-orig");
			FileInputStream fis = new FileInputStream(imageFile);
			FileOutputStream fos = new FileOutputStream(origFile);
			byte cnt[] = new byte[(int)imageFile.length()];
			fis.read(cnt);
			fos.write(cnt);
			fis.close();
			fos.close();	
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		try {
			  //read image file
			  BufferedImage bufferedImage = ImageIO.read(imageFile);
			  if (bufferedImage == null) {
				  System.out.println("Wrong image");
				  return;
			  }
		 
			  // create a blank, RGB, same width and height, and a white background
			  BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
					bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			  newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
		 
			  // write to jpeg file
			  ImageIO.write(newBufferedImage, "jpg", imageFile);
		 
			} catch (IOException e) {
				System.err.println("Cannot compress given image. Error: " + e);
		 
			}
	}
	
}