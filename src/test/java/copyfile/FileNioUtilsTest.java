package copyfile;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by muscaestar on 10/20/20
 *
 * @author muscaestar
 */
public class FileNioUtilsTest extends TestCase {

    public static final Logger logger = LogManager.getLogger(FileNioUtilsTest.class);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testCopyFile() {
        String srcFilePath = "Streaming_Linux_process.csv";
        String destFilePath = "Copy_Streaming_Linux_process.csv";
        logger.debug("Copy file from {} to {}", srcFilePath, destFilePath);
        boolean res = FileNioUtils.copyFile(srcFilePath, destFilePath);
        assertTrue(res);
    }

    public void testCopyFileFast() {
        String srcFilePath = "Streaming_Linux_memory.csv";
        String destFilePath = "Copy_Streaming_Linux_memory.csv";
        logger.debug("Copy file from {} to {}", srcFilePath, destFilePath);
        boolean res = FileNioUtils.copyFileFast(srcFilePath, destFilePath);
        assertTrue(res);
    }
}