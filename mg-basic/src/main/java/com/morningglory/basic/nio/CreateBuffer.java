package com.morningglory.basic.nio;// $Id$


import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;

@Slf4j
public class CreateBuffer {

  public static void main( String args[] ) throws Exception {

    IntBuffer buffer = IntBuffer.allocate(10);
    log.info("after init = {}",buffer);
    buffer.put(1);
    buffer.put(2);
    buffer.put(3);
    buffer.put(4);
    buffer.put(5);
    log.info("after put = {}",buffer);

    IntBuffer slice = buffer.slice();
    slice.position(2);
    log.info("slice = {}",slice);
    slice.put(6);
    log.info("after slice put = {},buffer = {}",slice,buffer.get(7));

  }
}
