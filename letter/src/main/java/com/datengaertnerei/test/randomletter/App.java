package com.datengaertnerei.test.randomletter;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {

	public static void main(String[] args) throws IOException {

		for (int i = 0; i < 10; i++) {
			RandomLetter l = new RandomLetter();
			String fileName = "TestDoc-"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd-HH-mm-ss-SSS")) + ".pdf";
			OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));
			l.generate(out);
			out.close();
		}
	}

}
