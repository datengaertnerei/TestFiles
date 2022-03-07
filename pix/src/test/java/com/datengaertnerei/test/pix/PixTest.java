package com.datengaertnerei.test.pix;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockserver.model.BinaryBody.binary;
import static org.mockserver.model.Header.header;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.mock.Expectation;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.model.MediaType;

public class PixTest {

	private ClientAndServer mockServer;

	@Test
	public void testMe() {
		try {
			App.getImageList("http://localhost:1080/api/", "sonne", 640, 480, "horizontal", "dummy");
		} catch (IOException e) {
			fail(e);
		}

		try {
			Path path = Paths.get("sonne-1933340-640-480.jpg");
			assertTrue(Files.deleteIfExists(path));
			path = Paths.get("sonne-3797650-640-480.jpg");
			assertTrue(Files.deleteIfExists(path));
		} catch (IOException e) {
			fail(e);
		}

	}

	@BeforeEach
	public void startServer() {
		mockServer = ClientAndServer.startClientAndServer(1080);
		mockServer.upsert(initializeExpectations());
	}

	@AfterEach
	public void stopServer() {
		mockServer.stop();
	}

	public Expectation[] initializeExpectations() {
		byte[] responseRaw = new byte[0];
		byte[] pngBytes = new byte[0];
		try {
			responseRaw = IOUtils.toByteArray(
					getClass().getClassLoader().getResourceAsStream("com/datengaertnerei/test/pix/test.json"));
			pngBytes = IOUtils.toByteArray(
					getClass().getClassLoader().getResourceAsStream("com/datengaertnerei/test/pix/test.png"));
		} catch (IOException e) {
			fail(e);
		}

		String apiResponse = new String(responseRaw, Charset.forName("UTF-8"));

		return new Expectation[] {
				new Expectation(request().withPath("/api/").withQueryStringParameters(param("key", "dummy")))
						.thenRespond(response().withStatusCode(HttpStatusCode.OK_200.code())
								.withHeaders(header(CONTENT_TYPE.toString(), MediaType.JSON_UTF_8.toString()))
								.withBody(apiResponse)),
				new Expectation(request().withPath("/get/*.*"))
						.thenRespond(response().withStatusCode(HttpStatusCode.OK_200.code())
								.withHeaders(header(CONTENT_TYPE.toString(), MediaType.PNG.toString()))
								.withBody(binary(pngBytes))) };
	}

}
