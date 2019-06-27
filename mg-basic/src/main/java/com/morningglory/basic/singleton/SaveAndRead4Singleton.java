package com.morningglory.basic.singleton;

import java.io.*;

/**
 * @Author: nsh
 * @Date: 2018/4/3
 * @Description:验证序列化和反序列化对单例的影响
 */
public class SaveAndRead4Singleton {

    public static void main(String[] args) {
        StaticSingleton singleton = StaticSingleton.getInstance();
        File file = new File("singleton.txt");
        System.out.println(file.getAbsolutePath());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(singleton);
            fos.close();
            oos.close();
            System.out.println(singleton.hashCode());

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            StaticSingleton rsingleton = (StaticSingleton) ois.readObject();
            fis.close();
            ois.close();
            System.out.println(rsingleton.hashCode());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}