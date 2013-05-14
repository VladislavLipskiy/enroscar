package com.stanfy.enroscar.net.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.GZIPOutputStream;

import org.junit.Test;

import com.google.mockwebserver.MockResponse;
import com.stanfy.enroscar.io.IoUtils;
import com.stanfy.enroscar.net.test.cache.AbstractOneCacheTest;

/**
 * Test for gzip response.
 * @author Roman Mazur (Stanfy - http://stanfy.com)
 *
 */
public class CompressedResponseTest extends AbstractOneCacheTest {

  public static byte[] getZippedText(final String text) throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final GZIPOutputStream gzip = new GZIPOutputStream(out);
    gzip.write(text.getBytes(IoUtils.UTF_8_NAME));
    gzip.close();
    return out.toByteArray();
  }

  @Test
  public void testCompressedResponse() throws IOException {
    final String text = "Some response text; Some response text; Some response text.";

    final byte[] zipped = getZippedText(text);
    // it's really zipped
    assertThat(zipped, is(not(equalTo(text.getBytes(IoUtils.UTF_8_NAME)))));

    getWebServer().enqueue(
        new MockResponse().setBody(zipped).setHeader("Content-Encoding", "gzip")
    );

    final URL url = getWebServer().getUrl("/");
    // response is unzipped
    assertResponse(url.openConnection(), text, false);
    // response is correctly read from the cache
    assertResponse(url.openConnection(), text, true);
  }

}
