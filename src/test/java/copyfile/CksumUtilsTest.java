package copyfile;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by muscaestar on 10/20/20
 *
 * @author muscaestar
 */
public class CksumUtilsTest extends TestCase {
    private static final Logger logger = LogManager.getLogger(CksumUtilsTest.class);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGetCrc32() {
        String path = "Streaming_Linux_memory.csv";
        // $ cksum -o 3 Streaming_Linux_memory.csv
        long resultFromUnixCksum = 1503972688;
        long crc32Value = CksumUtils.getCrc32(path);
        logger.debug("CRC32 value: {}", crc32Value);
        assertEquals(resultFromUnixCksum, crc32Value);
    }

    public void testHasSameCksum() {
        String path = "Streaming_Linux_memory.csv";
        assertTrue(CksumUtils.hasSameCksum(path, path));
    }
}