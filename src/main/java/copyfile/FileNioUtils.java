package copyfile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by muscaestar on 10/20/20
 *
 * @author muscaestar
 */
public class FileNioUtils {
    public static final Logger logger = LogManager.getLogger(FileNioUtils.class);

    public static boolean copyFile(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            long startTime = System.currentTimeMillis();
            try (FileInputStream fis = new FileInputStream(srcFile);
                 FileOutputStream fos = new FileOutputStream(destFile);
                 FileChannel inChannel = fis.getChannel();
                 FileChannel outChannel = fos.getChannel()) {

                int len = -1;
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while ((len = inChannel.read(buffer)) > 0) {
                    logger.debug("inChannel -> buffer: {}", len);
                    buffer.flip();
                    int outLen = -1;
                    while ((outLen = outChannel.write(buffer)) > 0) {
                        logger.debug("buffer -> outChannel: {}", outLen);
                    }
                    buffer.clear();
                }
                outChannel.force(true);
            }
            long endTime = System.currentTimeMillis();
            logger.debug("copyFile elapse time: {}", endTime - startTime);
        } catch (IOException ex) {
            logger.error("copyFile: ", ex);
        }
        if (CksumUtils.hasSameCksum(srcPath, destPath)) {
            logger.debug("After copy check: Same Cksum");
            return true;
        } else {
            logger.warn("After copy check: Copy file is not the same with source file!!!");
            return false;
        }
    }

    public static boolean copyFileFast(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            long startTime = System.currentTimeMillis();
            try (FileInputStream fis = new FileInputStream(srcFile);
                 FileOutputStream fos = new FileOutputStream(destFile);
                 FileChannel inChannel = fis.getChannel();
                 FileChannel outChannel = fos.getChannel()) {

                long size = inChannel.size();
                long pos = 0;
                long count = 0;
                while (pos < size) {
                    //每次复制最多1024个字节，没有就复制剩余的
                    count = size - pos > 1024 ? 1024 : size - pos;
                    //复制内存,偏移量pos + count长度
                    pos += outChannel.transferFrom(inChannel, pos, count);
                }

                outChannel.force(true);
            }
            long endTime = System.currentTimeMillis();
            logger.debug("copyFile elapse time: {}", endTime - startTime);
        } catch (IOException ex) {
            logger.error("copyFile: ", ex);
        }
        if (CksumUtils.hasSameCksum(srcPath, destPath)) {
            logger.debug("After copy check: Same Cksum");
            return true;
        } else {
            logger.warn("After copy check: Copy file is not the same with source file!!!");
            return false;
        }
    }

}
