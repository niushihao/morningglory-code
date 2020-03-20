package com.morningglory.dubbo.dubbospi;


import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @Author: qianniu
 * @Date: 2019-05-13 19:32
 * @Desc:
 */
public class DubboSPITest {

    private String adaptiveCode = "package com.alibaba.dubbo.rpc;\n" +
            "import com.alibaba.dubbo.common.extension.ExtensionLoader;\n" +
            "\n" +
            "public class Protocol$Adaptive implements com.alibaba.dubbo.rpc.Protocol {\n" +
            "\n" +
            "    public void destroy() {\n" +
            "        throw new UnsupportedOperationException(\"method public abstract void com.alibaba.dubbo.rpc.Protocol.destroy() of interface com.alibaba.dubbo.rpc.Protocol is not adaptive method!\");\n" +
            "    }\n" +
            "\n" +
            "    public int getDefaultPort() {\n" +
            "        throw new UnsupportedOperationException(\"method public abstract int com.alibaba.dubbo.rpc.Protocol.getDefaultPort() of interface com.alibaba.dubbo.rpc.Protocol is not adaptive method!\");\n" +
            "    }\n" +
            "\n" +
            "    public com.alibaba.dubbo.rpc.Exporter export(com.alibaba.dubbo.rpc.Invoker arg0) throws com.alibaba.dubbo.rpc.RpcException {\n" +
            "\n" +
            "        if (arg0 == null)\n" +
            "            throw new IllegalArgumentException(\"com.alibaba.dubbo.rpc.Invoker argument == null\");\n" +
            "\n" +
            "        if (arg0.getUrl() == null)\n" +
            "            throw new IllegalArgumentException(\"com.alibaba.dubbo.rpc.Invoker argument getUrl() == null\");com.alibaba.dubbo.common.URL url = arg0.getUrl();\n" +
            "\n" +
            "        String extName = ( url.getProtocol() == null ? \"dubbo\" : url.getProtocol() );\n" +
            "\n" +
            "        if(extName == null)\n" +
            "            throw new IllegalStateException(\"Fail to get extension(com.alibaba.dubbo.rpc.Protocol) name from url(\" + url.toString() + \") use keys([protocol])\");\n" +
            "\n" +
            "        com.alibaba.dubbo.rpc.Protocol extension = (com.alibaba.dubbo.rpc.Protocol)ExtensionLoader.getExtensionLoader(com.alibaba.dubbo.rpc.Protocol.class).getExtension(extName);\n" +
            "\n" +
            "        return extension.export(arg0);\n" +
            "    }\n" +
            "    public com.alibaba.dubbo.rpc.Invoker refer(java.lang.Class arg0, com.alibaba.dubbo.common.URL arg1) throws com.alibaba.dubbo.rpc.RpcException {\n" +
            "\n" +
            "        if (arg1 == null)\n" +
            "            throw new IllegalArgumentException(\"url == null\");\n" +
            "\n" +
            "        com.alibaba.dubbo.common.URL url = arg1;\n" +
            "\n" +
            "        String extName = ( url.getProtocol() == null ? \"dubbo\" : url.getProtocol() );\n" +
            "\n" +
            "        if(extName == null)\n" +
            "            throw new IllegalStateException(\"Fail to get extension(com.alibaba.dubbo.rpc.Protocol) name from url(\" + url.toString() + \") use keys([protocol])\");\n" +
            "\n" +
            "        com.alibaba.dubbo.rpc.Protocol extension = (com.alibaba.dubbo.rpc.Protocol)ExtensionLoader.getExtensionLoader(com.alibaba.dubbo.rpc.Protocol.class).getExtension(extName);\n" +
            "\n" +
            "        return extension.refer(arg0, arg1);\n" +
            "    }\n" +
            "}\n" +
            "\n";

    public static void main(String[] args) {

        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);

        /*Robot adaptiveExtension = extensionLoader.getAdaptiveExtension();
        adaptiveExtension.sayHello();*/

        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();

        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();

    }
}
