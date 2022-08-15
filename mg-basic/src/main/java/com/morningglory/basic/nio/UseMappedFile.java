package com.morningglory.basic.nio;// $Id$

import org.springframework.util.ReflectionUtils;
import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.*;
import java.nio.channels.*;

public class UseMappedFile
{
  static private final int start = 0;
  static private final int size = 1024;

  static public void main( String args[] ) throws Exception {
    RandomAccessFile raf = new RandomAccessFile( "usemappedfile.txt", "rw" );
    FileChannel fc = raf.getChannel();

    MappedByteBuffer mbb = fc.map( FileChannel.MapMode.READ_WRITE,
            start, size );


    mbb.put((byte) 97);
    mbb.put((byte) 124);

    //fc.write(mbb);
    //fc.force(false);
    Field address = ReflectionUtils.findField(MappedByteBuffer.class, "address");
    address.setAccessible(true);
    long addressLong = address.getLong(mbb);

    System.out.println(address.getLong(mbb));
    //mbb.arrayOffset()
    Unsafe unsafe = createUnsafe();
    unsafe.freeMemory(addressLong);
    raf.close();
  }


  public static Unsafe createUnsafe() {
    try {
      Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
      Field field = unsafeClass.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      Unsafe unsafe = (Unsafe) field.get(null);
      return unsafe;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
