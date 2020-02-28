package com.morningglory.basic.nio;// $Id$

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class FastCopyFile
{
  static public void main( String args[] ) throws Exception {
    if (args.length<2) {
      System.err.println( "Usage: java FastCopyFile infile outfile" );
      System.exit( 1 );
    }

    String infile = args[0];
    String outfile = args[1];

    FileInputStream fin = new FileInputStream( infile );
    FileOutputStream fout = new FileOutputStream( outfile );

    FileChannel fcin = fin.getChannel();
    FileChannel fcout = fout.getChannel();

    // 这种方式最快
    fcout.transferFrom(fcin,0,fcin.size());

    // 这种方式并不是最快的
    /*ByteBuffer buffer = ByteBuffer.allocateDirect( 1024 );

    while (true) {
      buffer.clear();

      int r = fcin.read( buffer );

      if (r==-1) {
        break;
      }

      buffer.flip();

      fcout.write( buffer );
    }*/
  }
}
