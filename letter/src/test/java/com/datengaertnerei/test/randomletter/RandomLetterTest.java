package com.datengaertnerei.test.randomletter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

public class RandomLetterTest {
	
	@Test
	public void testPageGeneration() {
		RandomLetter rl = new RandomLetter();
		try {
			ByteArrayOutputStream testStream = new ByteArrayOutputStream();
			rl.generate(testStream);
			testStream.close();
			PDDocument doc = PDDocument.load(testStream.toByteArray());
			assertTrue(doc.getNumberOfPages() > 0, "no pages in document"); 
		} catch (IOException e) {
			fail(e);
		}
	}

}
