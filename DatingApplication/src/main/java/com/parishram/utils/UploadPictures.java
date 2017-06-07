package com.parishram.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.parishram.common.Constant;
public class UploadPictures {

	public static  List<String> uploadPictures(HttpServletRequest request) throws FileUploadException, IOException {
		 List<String> uploadedImageList = null;
		if (ServletFileUpload.isMultipartContent(request)) {
		/*	MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			final Map<String, MultipartFile> files = (Map<String, MultipartFile>) multiRequest.getFileMap();
			for (MultipartFile file : files.values()) {	        	
				InputStream is = file.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		        StringBuilder out = new StringBuilder();
		        String line;
		        while ((line = reader.readLine()) != null) {
		            out.append(line);
		        }
		        String image = out.toString();
		        String updatedImage = buildImage(image.getBytes());
		        System.out.println(updatedImage);*/
			if (ServletFileUpload.isMultipartContent(request)) {
				uploadedImageList = new ArrayList<String>();
			    ServletFileUpload fileUpload = new ServletFileUpload();
			    FileItemIterator items = fileUpload.getItemIterator(request);
			    // iterate items
			    while (items.hasNext()) {
			        FileItemStream item = items.next();
			        if (!item.isFormField()) {
			            InputStream is = item.openStream();
				        String uploadedImage = buildImage(IOUtils.toByteArray(is));
				        uploadedImageList.add(uploadedImage);
			        }
			    }
			}
			
		}
		return uploadedImageList;
	}
	private static String buildImage(byte[] imageUploaded) {
		String image = new String(Base64.getEncoder().encode(imageUploaded));
		StringBuilder imageBuilder = new StringBuilder();
		imageBuilder.append(Constant.IMAGE_PREFIX);
		imageBuilder.append(image);
		return imageBuilder.toString();
	}

}
