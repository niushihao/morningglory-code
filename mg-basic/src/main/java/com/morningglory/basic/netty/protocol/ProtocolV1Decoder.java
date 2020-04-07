package com.morningglory.basic.netty.protocol;

import com.morningglory.basic.netty.codec.HessianCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-03-23 15:47
 * @Desc:
 */
@Slf4j
public class ProtocolV1Decoder extends LengthFieldBasedFrameDecoder {

    public ProtocolV1Decoder() {
        this(Constans.MAX_FRAME_LENGTH);
    }

    public ProtocolV1Decoder(int maxFrameLength) {
        super(maxFrameLength,3,4,-7,0);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decoded = super.decode(ctx, in);
        if(decoded instanceof ByteBuf){
            ByteBuf frame = (ByteBuf) decoded;
            try {
                return decodeFrame(frame);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
        return decoded;
    }

    private Object decodeFrame(ByteBuf frame) {
        // 取魔数
        byte b0 = frame.readByte();
        byte b1 = frame.readByte();
        if(Constans.MAGIC_CODE_BYTES[0] != b0 || Constans.MAGIC_CODE_BYTES[1] != b1){
            throw new IllegalArgumentException("Unknown magic code: " + b0 + ", " + b1);
        }
        byte version = frame.readByte();
        int fullLength = frame.readInt();
        int headLength = frame.readShort();
        byte messageType = frame.readByte();
        byte codecType = frame.readByte();
        byte compressorType = frame.readByte();
        int requestId = frame.readInt();

        Message message = new Message();
        message.setId(requestId);
        message.setCodec(codecType);
        message.setCompressor(compressorType);
        message.setMessageType(messageType);

        // read map
        int headMapLength = headLength - Constans.V1_HEAD_LENGTH;
        if(headMapLength > 0){

        }

        // read body
        if(Constans.MSGTYPE_HEARTBEAT_REQUEST == messageType){
            message.setBody(HeartbeatMessage.PING);
        }else if(Constans.MSGTYPE_HEARTBEAT_RESPONSE == messageType){
            message.setBody(HeartbeatMessage.PONG);
        }else {
            int bodyLength = fullLength - headLength;
            if(bodyLength > 0){
                byte[] bytes = new byte[bodyLength];
                frame.readBytes(bytes);
                // TODO 解压,反序列化
                HessianCodec codec = new HessianCodec();
                message.setBody(codec.decode(bytes));
            }
        }
        return message;
    }
}
