package com.karl.pre.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class EurekaInstanceContainer
{
    private static volatile List<EurekaInstanceKeyInfo> keyInfos = new ArrayList<>();

    public static void add(EurekaInstanceKeyInfo keyInfo)
    {
        keyInfos.add(keyInfo);
    }

    public static void del(EurekaInstanceKeyInfo keyInfo)
    {
        keyInfos.remove(keyInfo);
    }

    public static List<EurekaInstanceKeyInfo> keyInfos()
    {
        return keyInfos;
    }

}
