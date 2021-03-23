package com.morningglory.dubbo.wrapper;

import com.morningglory.dubbo.service.DemoService;
import org.apache.dubbo.common.bytecode.Wrapper;

/**
 * @Author: qianniu
 * @Date: 2019-05-20 14:09
 * @Desc:
 */
public class WrapperTest {

    private String c1 = "public void setPropertyValue(Object o, String n, Object v){ " +
            "com.morningglory.dubbo.service.DemoService w; " +
            "try{ " +
            "   w = ((com.morningglory.dubbo.service.DemoService)$1); " +
            "}catch(Throwable e){" +
            "    throw new IllegalArgumentException(e); " +
            "}";

    private String c2 = "public Object getPropertyValue(Object o, String n){ " +
            "com.morningglory.dubbo.service.DemoService w; " +
            "try{ " +
            "   w = ((com.morningglory.dubbo.service.DemoService)$1); " +
            "}catch(Throwable e){ " +
            "   throw new IllegalArgumentException(e); " +
            "}";

    private String c3 = "public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws java.lang.reflect.InvocationTargetException{ " +
            "com.morningglory.dubbo.service.DemoService w; " +
            "try{ " +
            "   w = ((com.morningglory.dubbo.service.DemoService)$1); " +
            "}catch(Throwable e){ " +
            "   throw new IllegalArgumentException(e); " +
            "} " +
            "" +
            "try{ " +
            "   if( \"get\".equals( $2 )  &&  $3.length == 1 &&  $3[0].getName().equals(\"int\") ) {  " +
            "       return ($w)w.get(((Number)$4[0]).intValue()); } " +
            "   if( \"get\".equals( $2 )  &&  $3.length == 1 &&  $3[0].getName().equals(\"java.lang.Integer\") ) {  " +
            "       return ($w)w.get((java.lang.Integer)$4[0]); } " +
            "   if( \"sayHi\".equals( $2 )  &&  $3.length == 1 &&  $3[0].getName().equals(\"java.lang.Object\") ) {  " +
            "       return ($w)w.sayHi((java.lang.Object)$4[0]); } " +
            "   if( \"sayHi\".equals( $2 )  &&  $3.length == 2 &&  $3[0].getName().equals(\"com.morningglory.dubbo.module.DubboRequest\") " +
            "           &&  $3[1].getName().equals(\"long\") ) {  " +
            "       return ($w)w.sayHi((com.morningglory.dubbo.module.DubboRequest)$4[0],((Number)$4[1]).longValue()); } " +
            "   if( \"sayHi\".equals( $2 )  &&  $3.length == 2 &&  $3[0].getName().equals(\"com.morningglory.dubbo.module.DubboRequest\") " +
            "           &&  $3[1].getName().equals(\"java.lang.Long\") ) {  " +
            "       return ($w)w.sayHi((com.morningglory.dubbo.module.DubboRequest)$4[0],(java.lang.Long)$4[1]); } " +
            "   if( \"sayHi\".equals( $2 )  &&  $3.length == 3 &&  $3[0].getName().equals(\"com.morningglory.dubbo.module.DubboRequest\") " +
            "           &&  $3[1].getName().equals(\"java.lang.String\") &&  $3[2].getName().equals(\"java.lang.Integer\") ) {  " +
            "       return ($w)w.sayHi((com.morningglory.dubbo.module.DubboRequest)$4[0],(java.lang.String)$4[1],(java.lang.Integer)$4[2]); } " +
            "   if( \"sayHiWithSleep\".equals( $2 )  &&  $3.length == 1 ) {  " +
            "       return ($w)w.sayHiWithSleep((com.morningglory.dubbo.module.DubboRequest)$4[0]); } " +
            "} catch(Throwable e) {      " +
            "   throw new java.lang.reflect.InvocationTargetException(e);  " +
            "} " +
            "throw new org.apache.dubbo.common.bytecode.NoSuchMethodException(\"Not found method \\\"\"+$2+\"\\\" in class com.morningglory.dubbo.service.DemoService.\"); }";



    // 生成类的完整信息
    private String complete = "public class Wrapper0\n" +
            "extends Wrapper\n" +
            "implements ClassGenerator.DC {\n" +
            "    public static String[] pns;\n" +
            "    public static Map pts;\n" +
            "    public static String[] mns;\n" +
            "    public static String[] dmns;\n" +
            "    public static Class[] mts0;\n" +
            "    public static Class[] mts1;\n" +
            "    public static Class[] mts2;\n" +
            "    public static Class[] mts3;\n" +
            "    public static Class[] mts4;\n" +
            "\n" +
            "    public String[] getPropertyNames() {\n" +
            "        return pns;\n" +
            "    }\n" +
            "\n" +
            "    public boolean hasProperty(String string) {\n" +
            "        return pts.containsKey(string);\n" +
            "    }\n" +
            "\n" +
            "    public Class getPropertyType(String string) {\n" +
            "        return (Class)pts.get(string);\n" +
            "    }\n" +
            "\n" +
            "    public String[] getMethodNames() {\n" +
            "        return mns;\n" +
            "    }\n" +
            "\n" +
            "    public String[] getDeclaredMethodNames() {\n" +
            "        return dmns;\n" +
            "    }\n" +
            "\n" +
            "    public void setPropertyValue(Object object, String string, Object object2) {\n" +
            "        try {\n" +
            "            ChannelWarehouseMappingReadFacade channelWarehouseMappingReadFacade = (ChannelWarehouseMappingReadFacade)object;\n" +
            "        }\n" +
            "        catch (Throwable throwable) {\n" +
            "            throw new IllegalArgumentException(throwable);\n" +
            "        }\n" +
            "        throw new NoSuchPropertyException(new StringBuffer().append(\"Not found property \\\"\").append(string).append(\"\\\" field or setter method in class io.terminus.parana.channel.api.facade.warehouse.ChannelWarehouseMappingReadFacade.\").toString());\n" +
            "    }\n" +
            "\n" +
            "    public Object getPropertyValue(Object object, String string) {\n" +
            "        try {\n" +
            "            ChannelWarehouseMappingReadFacade channelWarehouseMappingReadFacade = (ChannelWarehouseMappingReadFacade)object;\n" +
            "        }\n" +
            "        catch (Throwable throwable) {\n" +
            "            throw new IllegalArgumentException(throwable);\n" +
            "        }\n" +
            "        throw new NoSuchPropertyException(new StringBuffer().append(\"Not found property \\\"\").append(string).append(\"\\\" field or setter method in class io.terminus.parana.channel.api.facade.warehouse.ChannelWarehouseMappingReadFacade.\").toString());\n" +
            "    }\n" +
            "\n" +
            "    public Object invokeMethod(Object object, String string, Class[] arrclass, Object[] arrobject) throws InvocationTargetException {\n" +
            "        ChannelWarehouseMappingReadFacade channelWarehouseMappingReadFacade;\n" +
            "        try {\n" +
            "            channelWarehouseMappingReadFacade = (ChannelWarehouseMappingReadFacade)object;\n" +
            "        }\n" +
            "        catch (Throwable throwable) {\n" +
            "            throw new IllegalArgumentException(throwable);\n" +
            "        }\n" +
            "        try {\n" +
            "            if (\"findById\".equals(string) && arrclass.length == 1) {\n" +
            "                return channelWarehouseMappingReadFacade.findById((ChannelWarehouseMappingFindByIdRequest)arrobject[0]);\n" +
            "            }\n" +
            "            if (\"listByOuterWarehouseCodeAndShopId\".equals(string) && arrclass.length == 1) {\n" +
            "                return channelWarehouseMappingReadFacade.listByOuterWarehouseCodeAndShopId((ChannelWarehouseMappingListByOuterWarehouseCodeRequest)arrobject[0]);\n" +
            "            }\n" +
            "            if (\"paging\".equals(string) && arrclass.length == 1) {\n" +
            "                return channelWarehouseMappingReadFacade.paging((ChannelWarehouseMappingPagingRequest)arrobject[0]);\n" +
            "            }\n" +
            "            if (\"findByOuterWarehouseCode\".equals(string) && arrclass.length == 1) {\n" +
            "                return channelWarehouseMappingReadFacade.findByOuterWarehouseCode((ChannelWarehouseMappingFindByOuterWarehouseCodeRequest)arrobject[0]);\n" +
            "            }\n" +
            "            if (\"listByOuterWarehouseId\".equals(string) && arrclass.length == 1) {\n" +
            "                return channelWarehouseMappingReadFacade.listByOuterWarehouseId((ChannelWarehouseMappingListByOuterWarehouseIdsRequest)arrobject[0]);\n" +
            "            }\n" +
            "        }\n" +
            "        catch (Throwable throwable) {\n" +
            "            throw new InvocationTargetException(throwable);\n" +
            "        }\n" +
            "        throw new NoSuchMethodException(new StringBuffer().append(\"Not found method \\\"\").append(string).append(\"\\\" in class io.terminus.parana.channel.api.facade.warehouse.ChannelWarehouseMappingReadFacade.\").toString());\n" +
            "    }\n" +
            "}";

    public static void main(String[] args) {

        Wrapper wrapper = Wrapper.getWrapper(DemoService.class);

        String[] ns = wrapper.getDeclaredMethodNames();

        System.out.println(ns.length);

    }

}
