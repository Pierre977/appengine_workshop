package com.example.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		FileInputStream fileInputStream = new FileInputStream(new File("WEB-INF/android.jpg"));
	    FileChannel fileChannel = fileInputStream.getChannel();
	    ByteBuffer byteBuffer = ByteBuffer.allocate((int)fileChannel.size());
	    fileChannel.read(byteBuffer);
	    
	    byte[] imageBytes = byteBuffer.array();
	    
	    // Get an instance of the imagesService we can use to transform images.
	    ImagesService imagesService = ImagesServiceFactory.getImagesService();
	    Image image = ImagesServiceFactory.makeImage(imageBytes);
	    Transform resize = ImagesServiceFactory.makeResize(100, 50);
	    Image resizedImage = imagesService.applyTransform(resize, image);
		
	    resp.setContentType("image/jpeg");
	    resp.getOutputStream().write(resizedImage.getImageData());
	}
	
}
