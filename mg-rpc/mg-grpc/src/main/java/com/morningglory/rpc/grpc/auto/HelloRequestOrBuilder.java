// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hello.proto

package com.morningglory.rpc.grpc.auto;

public interface HelloRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:HelloRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string name = 1;</code>
   * @return The name.
   */
  String getName();
  /**
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>map&lt;string, string&gt; extendInfo = 2;</code>
   */
  int getExtendInfoCount();
  /**
   * <code>map&lt;string, string&gt; extendInfo = 2;</code>
   */
  boolean containsExtendInfo(
      String key);
  /**
   * Use {@link #getExtendInfoMap()} instead.
   */
  @Deprecated
  java.util.Map<String, String>
  getExtendInfo();
  /**
   * <code>map&lt;string, string&gt; extendInfo = 2;</code>
   */
  java.util.Map<String, String>
  getExtendInfoMap();
  /**
   * <code>map&lt;string, string&gt; extendInfo = 2;</code>
   */

  String getExtendInfoOrDefault(
      String key,
      String defaultValue);
  /**
   * <code>map&lt;string, string&gt; extendInfo = 2;</code>
   */

  String getExtendInfoOrThrow(
      String key);
}