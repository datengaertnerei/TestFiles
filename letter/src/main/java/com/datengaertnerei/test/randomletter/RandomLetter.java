package com.datengaertnerei.test.randomletter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.TransformerException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.type.BadFieldValueException;
import org.apache.xmpbox.xml.XmpSerializer;

import com.github.javafaker.Faker;

public class RandomLetter {

	private static final String ISO_COATED_V2_300_BAS_IC_COLOR = "ISO Coated v2 300% (basICColor)";
	private Faker faker;
	private PDDocument document;
	private PDPage page;
	private PDFont font;
	private float fontSize;
	private float leading;
	private float leftmargin;
	private float rightmargin;
	private float topmargin;
	private float pageWidth;
	private float leftBound;
	private float topBound;
	private PDRectangle mediabox;

	public RandomLetter() {
		faker = new Faker(Locale.GERMANY);
		document = new PDDocument();
		page = new PDPage(PDRectangle.A4);

		try {
			InputStream fontStream = getClass()
					.getResourceAsStream("/org/apache/pdfbox/resources/ttf/LiberationSans-Regular.ttf");
			font = PDType0Font.load(document, fontStream);
		} catch (IOException e) {
			//fallback
			font = PDType1Font.HELVETICA;
		}

		fontSize = 11;
		leading = 1.5f * fontSize;

		mediabox = page.getMediaBox();
		leftmargin = millimeterToPoints(25f);
		rightmargin = millimeterToPoints(20f);
		topmargin = millimeterToPoints(32f);
		pageWidth = mediabox.getWidth() - leftmargin - rightmargin;
		leftBound = mediabox.getLowerLeftX() + leftmargin;
		topBound = mediabox.getUpperRightY() - topmargin;

	}

	public void generate(OutputStream out) throws IOException {

		addPDFAData();

		// Start a new content stream for the page
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		contentStream.setFont(font, fontSize);

		addAddressBlock(contentStream);

		addInfoBlock(contentStream);

		addTextBlock(contentStream);

		addFooter(contentStream);

		// Make sure that the content stream is closed:
		contentStream.close();
		document.addPage(page);

		// Save the newly created document
		document.save(out);

		// finally make sure that the document is properly closed.
		document.close();
	}

	private void addPDFAData() {

		PDDocumentCatalog cat = document.getDocumentCatalog();
		PDMetadata metadata = new PDMetadata(document);
		cat.setMetadata(metadata);

		PDMarkInfo markInfo = new PDMarkInfo();
		markInfo.setMarked(true);
		cat.setMarkInfo(markInfo);

		try {
			XMPMetadata xmp = XMPMetadata.createXMPMetadata();
			PDFAIdentificationSchema id = xmp.createAndAddPFAIdentificationSchema();
			id.setPart(3);
			id.setConformance("B");
			XmpSerializer serializer = new XmpSerializer();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			serializer.serialize(xmp, baos, true);
			metadata.importXMPMetadata(baos.toByteArray());

			InputStream colorProfile = getClass()
					.getResourceAsStream("/org/apache/pdfbox/resources/icc/ISOcoated_v2_300_bas.icc");
			PDOutputIntent oi = new PDOutputIntent(document, colorProfile);
			oi.setInfo(ISO_COATED_V2_300_BAS_IC_COLOR);
			oi.setOutputCondition(ISO_COATED_V2_300_BAS_IC_COLOR);
			oi.setOutputConditionIdentifier(ISO_COATED_V2_300_BAS_IC_COLOR);
			oi.setRegistryName("https://www.colormanagement.org/");
			cat.addOutputIntent(oi);			
		} catch (BadFieldValueException | TransformerException | IOException e) {
			// test data generator -> just dump the stacktrace
			e.printStackTrace();
		}
	    
		
		PDStructureTreeRoot newTreeRoot = new PDStructureTreeRoot();			
		cat.setStructureTreeRoot(newTreeRoot);
		
	}

	private void addFooter(PDPageContentStream contentStream) throws IOException {
		contentStream.beginText();
		contentStream.newLineAtOffset(leftBound, millimeterToPoints(10.0f) + leading);
		contentStream.showText(faker.company().name() + " - " + faker.address().fullAddress());
		contentStream.endText();
	}

	private void addTextBlock(PDPageContentStream contentStream) throws IOException {
		contentStream.beginText();
		String text = faker.lorem().paragraph(15);
		List<String> lines = buildLines(text);
		contentStream.newLineAtOffset(leftBound, mediabox.getUpperRightY() - millimeterToPoints(100.0f));
		for (int i = 0; i < 2; i++) {
			for (String line : lines) {
				contentStream.showText(line);
				contentStream.newLineAtOffset(0, -leading);
			}
			contentStream.newLineAtOffset(0, -leading);
		}
		contentStream.showText("Mit freundlichen Grüßen");
		contentStream.endText();
	}

	private void addInfoBlock(PDPageContentStream contentStream) throws IOException {
		contentStream.beginText();
		contentStream.newLineAtOffset(mediabox.getLowerLeftX() + millimeterToPoints(125.0f), topBound);
		contentStream.showText("Unser Zeichen: " + faker.commerce().promotionCode(3));
		contentStream.newLineAtOffset(0, -leading * 2);
		contentStream.showText("Ansprechpartner:");
		contentStream.newLineAtOffset(0, -leading);
		contentStream.showText(faker.name().fullName());
		contentStream.newLineAtOffset(0, -leading);
		contentStream.showText(faker.phoneNumber().phoneNumber());
		contentStream.newLineAtOffset(0, -leading);
		contentStream.showText(faker.internet().emailAddress());
		contentStream.newLineAtOffset(0, -leading * 2);
		contentStream.showText(DateFormat.getDateInstance().format(faker.date().past(25, TimeUnit.DAYS)));
		contentStream.endText();
	}

	private void addAddressBlock(PDPageContentStream contentStream) throws IOException {
		contentStream.beginText();
		contentStream.newLineAtOffset(leftBound, topBound);
		contentStream.showText(faker.name().fullName());
		contentStream.newLineAtOffset(0, -leading);
		contentStream.showText(faker.address().streetAddress());
		contentStream.newLineAtOffset(0, -leading * 2);
		contentStream.showText(faker.address().zipCode() + " " + faker.address().city());
		contentStream.endText();
	}

	private List<String> buildLines(String text) throws IOException {
		List<String> lines = new ArrayList<String>();
		int lastSpace = -1;
		while (text.length() > 0) {
			int spaceIndex = text.indexOf(' ', lastSpace + 1);
			if (spaceIndex < 0)
				spaceIndex = text.length();
			String subString = text.substring(0, spaceIndex);
			float size = fontSize * font.getStringWidth(subString) / 1000;
			if (size > pageWidth) {
				if (lastSpace < 0)
					lastSpace = spaceIndex;
				subString = text.substring(0, lastSpace);
				lines.add(subString);
				text = text.substring(lastSpace).trim();
				lastSpace = -1;
			} else if (spaceIndex == text.length()) {
				lines.add(text);
				text = "";
			} else {
				lastSpace = spaceIndex;
			}
		}
		return lines;
	}

	public static float millimeterToPoints(float mm) {
		return (millimeterToInch(mm) * 72.0f);
	}

	public static float millimeterToInch(float mm) {
		return (mm / 25.4f);
	}
}
