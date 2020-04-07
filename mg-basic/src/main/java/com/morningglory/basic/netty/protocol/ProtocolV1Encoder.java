package com.morningglory.basic.netty.protocol;

import com.morningglory.basic.netty.codec.Codec;
import com.morningglory.basic.netty.codec.HessianCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2020-03-23 15:51
 * @Desc:
 */
@Slf4j
public class ProtocolV1Encoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {

        try {
            if(msg instanceof Message){
                Message message = (Message) msg;
                int fullLength = Constans.V1_HEAD_LENGTH;
                int headLength = Constans.V1_HEAD_LENGTH;
                byte messageType = message.getMessageType();

                byteBuf.writeBytes(Constans.MAGIC_CODE_BYTES);
                byteBuf.writeByte(Constans.VERSION);
                log.info("byteBuf.writerIndex() = {}",byteBuf.writerIndex());
                // full Length(4B) and head length(2B) will fix in the end.
                byteBuf.writerIndex(byteBuf.writerIndex()+6);
                log.info("byteBuf.writerIndex() = {}",byteBuf.writerIndex());
                // 数据部分
                byteBuf.writeByte(messageType);
                byteBuf.writeByte(message.getCodec());
                byteBuf.writeByte(message.getCompressor());
                byteBuf.writeInt(message.getId());

                // headMap先不处理
                Map<String, String> headMap = message.getHeadMap();

                byte[] byteBodys = null;
                if(Constans.MSGTYPE_HEARTBEAT_REQUEST != messageType
                        && Constans.MSGTYPE_HEARTBEAT_RESPONSE != messageType){
                    /**
                     * TODO 应该使用工厂 根据 codec动态取
                     * Codec codec = CodecFactory.getCodec(rpcMessage.getCodec());
                     * bodyBytes = codec.encode(rpcMessage.getBody());
                     */
                    Codec codec = new HessianCodec();
                    byteBodys = codec.encode(message.getBody());

                    /**
                     * TODO 应该使用工厂 增加压缩配置
                     * Compressor compressor = CompressorFactory.getCompressor(rpcMessage.getCompressor());
                     * bodyBytes = compressor.compress(bodyBytes);
                     */

                    fullLength+=byteBodys.length;
                }

                // fix fullLength and headLength
                if(byteBodys != null){
                    byteBuf.writeBytes(byteBodys);
                }
                // skip magic code(2B) + version(1B)
                int writerIndex = byteBuf.writerIndex();
                byteBuf.writerIndex(writerIndex - fullLength + 3);
                byteBuf.writeInt(fullLength);
                byteBuf.writeShort(headLength);
                byteBuf.writerIndex(writerIndex);

            }else {
                throw new UnsupportedOperationException("Not support this class:" + msg.getClass());
            }
        }catch (Exception e){
            log.error("Encode request error!", e);
        }
    }
}
