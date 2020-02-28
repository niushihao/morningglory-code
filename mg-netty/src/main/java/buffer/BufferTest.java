package buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-11 17:06
 * @Desc: 通过索引get/set Buffer的指针不会变化,read/write时指针会变化
 */
@Slf4j
public class BufferTest {

    public static void main(String[] args) {

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        log.info("动作:分配ByteBuf(9,100):[{}]",buffer);

        buffer.writeBytes(new byte[]{1,2,3,4});
        log.info("动作:写入4个字节(1,2,3,4):[{}]",buffer);

        buffer.setByte(3,5);
        log.info("动作:setByte(3,5):[{}]",buffer);

        log.info("start============:get=============");
        getByteBuf(buffer);
        log.info("动作:读取数据:[{}]",buffer);

        log.info("start============:read=============");
        readBuf(buffer);
        log.info("动作:读取数据:[{}]",buffer);
    }

    // 取字节
    private static void readBuf(ByteBuf buffer) {
        while (buffer.isReadable()){
            log.info("read():取一个字节:{}",buffer.readByte());
        }
    }

    // 读字节,不改变指针
    private static void getByteBuf(ByteBuf buffer) {
        for(int i = 0; i < buffer.readableBytes(); i++){
            log.info("get():读一个字节:{}",buffer.getByte(i));
        }
    }
}
