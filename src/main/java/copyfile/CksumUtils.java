package copyfile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.CRC32;

/**
 * Created by muscaestar on 10/20/20
 *
 * @author muscaestar
 */
public class CksumUtils {
    private static final Logger logger = LogManager.getLogger(CksumUtils.class);

    public static long getCrc32(String filePath) {
        CRC32 crc32 = new CRC32();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        int len = -1;
        try (SeekableByteChannel input = Files.newByteChannel(Paths.get(filePath), StandardOpenOption.READ)) {
            logger.debug("Read file from path {}", filePath);
            while ((len = input.read(byteBuffer)) > 0) {
                byteBuffer.flip();
                crc32.update(byteBuffer.array(), 0, len);
            }
            return crc32.getValue();
        } catch (IOException e) {
            logger.error("getCrc32", e);
        }
        return crc32.getValue();
    }

    public static boolean hasSameCksum(String filePathA, String filePathB) {
        return getCrc32(filePathA) == getCrc32(filePathB);
    }
}
