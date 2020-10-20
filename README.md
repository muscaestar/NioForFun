# NioForFun
记录学习Java NIO期间的代码

## CksumUtils
- 得到文件的CRC32值，与unix下的`cksum -o 3 <file>`返回值相同
- 比较两个文件，确认它们的CRC32值相同

## FileNioUtils
- 基于`FileChannel`和`ByteBuffer`实现文件复制，并校验CRC32
- 基于`FileChannel`和`transferFrom`实现文件复制，并校验CRC32
- 扩展知识：零拷贝技术(Zero-copy/DMA)
- [StackOverflow - Reading: why is java.nio.FileChannel transferTo() and transferFrom() faster??? Does it use DMA?](https://stackoverflow.com/questions/16451642/why-is-java-nio-filechannel-transferto-and-transferfrom-faster-does-it-us)
