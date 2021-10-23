package com.datengaertnerei.test.pix;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;

public class App {

	private static final String PIXABAY_API_KEY = "PixabayApiKey";

	public static void main(String[] args) throws IOException {

		String srch = "inspiration";
		int targetWidth = 1600;
		int targetHeight = 900;
		String orientation = "horizontal";

		Preferences prefs = Preferences.userRoot().node("pix");
		String apiKey = prefs.get(PIXABAY_API_KEY, "");

		if (apiKey.length() == 0) {
			System.out.print("API Key: ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			apiKey = reader.readLine();
			prefs.put(PIXABAY_API_KEY, apiKey);
		}

		StringBuilder pixTarget = new StringBuilder("https://pixabay.com/api/?key=").append(apiKey)
				.append("&image_type=photo&per_page=10&min_width=1000&min_height=1000&q=")
				.append(srch)
				.append("&orientation=")
				.append(orientation);

		Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFeature.class));
		WebTarget webTarget = client.target(pixTarget.toString());

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		PBResponse result = response.readEntity(PBResponse.class);

		for (Hit hit : result.getHits()) {
			getImage(srch, hit, targetHeight, targetWidth);
		}

	}

	private static void getImage(String srch, Hit hit, int targetHeight, int targetWidth)
			throws MalformedURLException, IOException {
		URL url = new URL(hit.getLargeImageURL());
		BufferedImage originalImage = ImageIO.read(url);

		double hfactor = (double) originalImage.getHeight() / (double) targetHeight;
		double wfactor = (double) originalImage.getWidth() / (double) targetWidth;
		double factor = hfactor > wfactor ? wfactor : hfactor;
		int interimHeight = (int) (originalImage.getHeight() / factor);
		int interimWidth = (int) (originalImage.getWidth() / factor);

		Image resultingImage = originalImage.getScaledInstance(interimWidth, interimHeight, Image.SCALE_SMOOTH);
		BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		outputImage.getGraphics().drawImage(resultingImage, (interimWidth - targetWidth) / -2,
				(interimHeight - targetHeight) / -2, null);
		int fontHeight = outputImage.getGraphics().getFontMetrics().getHeight();
		outputImage.getGraphics().drawString("Image by " + hit.getUser(), 5, 5 + fontHeight);

		String filename = new StringBuilder().append(srch).append("-").append(hit.getId()).append("-")
				.append(targetWidth).append("-").append(targetHeight).append(".jpg").toString();

		ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
		ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
		jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		jpgWriteParam.setCompressionQuality(0.7f);

		ImageOutputStream outputStream = new FileImageOutputStream(new File(filename));

		jpgWriter.setOutput(outputStream);
		jpgWriter.write(outputImage);
		jpgWriter.dispose();
		outputStream.close();
	}

}
