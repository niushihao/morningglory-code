package com.morningglory.h2;

import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;

public class MvStoreTest {

    public static void main(String[] args) {

        MVStore mvStore = MVStore.open("file:/Users/nsh/data/demo1");

        MVMap<Integer, String> map = mvStore.openMap("testData");
        map.put(1, "test");

        long oldVersion = mvStore.getCurrentVersion();
        System.out.println(map.get(1));
        System.out.println(oldVersion);
        mvStore.commit();
        System.out.println(mvStore.getCurrentVersion());


        map.put(1, "test2");
        MVMap<Integer, String> integerStringMVMap = map.openVersion(oldVersion);
        System.out.println(integerStringMVMap.get(1));


    }
}
