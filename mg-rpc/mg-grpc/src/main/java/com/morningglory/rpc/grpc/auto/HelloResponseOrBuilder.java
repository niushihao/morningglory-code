// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hello.proto

package com.morningglory.rpc.grpc.auto;

public interface HelloResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:HelloResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string message = 1;</code>
   * @return The message.
   */
  String getMessage();
  /**
   * <code>string message = 1;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>bytes data = 2;</code>
   * @return The data.
   */
  com.google.protobuf.ByteString getData();

  /**
   * <code>map&lt;string, string&gt; extendInfo = 3;</code>
   */
  int getExtendInfoCount();
  /**
   * <code>map&lt;string, string&gt; extendInfo = 3;</code>
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
   * <code>map&lt;string, string&gt; extendInfo = 3;</code>
   */
  java.util.Map<String, String>
  getExtendInfoMap();
  /**
   * <code>map&lt;string, string&gt; extendInfo = 3;</code>
   */

  String getExtendInfoOrDefault(
      String key,
      String defaultValue);
  /**
   * <code>map&lt;string, string&gt; extendInfo = 3;</code>
   */

  String getExtendInfoOrThrow(
      String key);
}
