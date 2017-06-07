package com.parishram.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

public class ThumbnailGenerator {
	public static String generateThumbnail(String image) throws IOException {
		if (StringUtils.isNotBlank(image)) {
			String imageInString = image.split(",")[1];
			byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageInString);
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// BufferedImage rotatedResizedImg = Scalr.rotate(img,
			// Scalr.Rotation.CW_90);
			BufferedImage resizedImg = null;
			if(img != null){
				 resizedImg = Scalr.resize(img, Method.AUTOMATIC, Mode.FIT_EXACT, 50, 60);
				 ImageIO.write(resizedImg, "JPEG", baos);
			}
			String data = DatatypeConverter.printBase64Binary(baos.toByteArray());
			String imageString = "data:image/png;base64," + data;
			return imageString;
		}
		return null;
	}
}
