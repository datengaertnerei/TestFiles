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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class App {

	private static final String API_BASE = "https://pixabay.com/api/";
	private static final String HEIGHT_SHORT = "h";
	private static final String WIDTH_SHORT = "w";
	private static final String KEYWORD_SHORT = "s";
	private static final String IMAGE_BY = "Image by ";
	private static final String PIXABAY_API_KEY = "PixabayApiKey";

	public static void main(String[] args) throws IOException, ParseException {

		// create Options object
		Options options = new Options();
		options.addOption(KEYWORD_SHORT, true, "keyword to search");
		options.addOption(WIDTH_SHORT, true, "width");
		options.addOption(HEIGHT_SHORT, true, "height");
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		String srch = cmd.getOptionValue(KEYWORD_SHORT);
		int targetWidth = Integer.parseInt(cmd.getOptionValue(WIDTH_SHORT));
		int targetHeight = Integer.parseInt(cmd.getOptionValue(HEIGHT_SHORT));
		String orientation = targetWidth > targetHeight ? "horizontal" : "vertical";

		Preferences prefs = Preferences.userRoot().node("pix");
		String apiKey = prefs.get(PIXABAY_API_KEY, "");

		if (apiKey.length() == 0) {
			System.out.print("API Key: ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			apiKey = reader.readLine();
			prefs.put(PIXABAY_API_KEY, apiKey);
		}

		getImageList(API_BASE, srch, targetWidth, targetHeight, orientation, apiKey);

	}

	private static void getImageList(String apiBase, String srch, int targetWidth, int targetHeight, String orientation,
			String apiKey) throws MalformedURLException, IOException {
		StringBuilder pixTarget = new StringBuilder(apiBase + "?key=").append(apiKey)
				.append("&image_type=photo&per_page=10&min_width=1000&min_height=1000&q=").append(srch)
				.append("&orientation=").append(orientation);

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
		outputImage.getGraphics().drawString(IMAGE_BY + hit.getUser(), 5, 5 + fontHeight);

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
