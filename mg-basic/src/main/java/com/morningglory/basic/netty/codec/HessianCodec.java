package com.morningglory.basic.netty.codec;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.Serializer;
import com.caucho.hessian.io.SerializerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Author: qianniu
 * @Date: 2020-03-23 16:19
 * @Desc:
 */
@Slf4j
public class HessianCodec implements Codec{
    @Override
    public <T> byte[] encode(T t) {
        byte[] stream = null;
        SerializerFactory hessian = new SerializerFactory();
        try {
            Serializer serializer = hessian.getSerializer(t.getClass());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Hessian2Output output = new Hessian2Output(baos);
            serializer.writeObject(t, output);
            output.close();
            stream = baos.toByteArray();
        } catch (IOException e) {
            log.error("Hessian encode error:{}", e.getMessage(), e);
        }
        return stream;
    }

    @Override
    public <T> T decode(byte[] bytes) {
        T obj = null;
        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes);) {
            Hessian2Input input = new Hessian2Input(is);
            obj = (T) input.readObject();
            input.close();
        } catch (IOException e) {
            log.error("Hessian decode error:{}", e.getMessage(), e);
        }
        return obj;
    }
}
