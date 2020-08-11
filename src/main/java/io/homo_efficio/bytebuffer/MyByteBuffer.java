package io.homo_efficio.bytebuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author homo.efficio@gmail.com
 * created on 2020-08-09
 */
public class MyByteBuffer {

    public ByteBuffer newByteBuffer(int capacity) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(capacity);
        return byteBuffer;
    }

    public void writeToFile(String filePathStr, ByteBuffer byteBuffer) throws IOException {

        Path resultFilePath = Paths.get(filePathStr);
        try (FileChannel resultFileChannel = FileChannel.open(resultFilePath,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            byteBuffer.flip();
            byteBuffer.limit(byteBuffer.capacity() / 2);
            resultFileChannel.write(byteBuffer);
        }
    }
}
