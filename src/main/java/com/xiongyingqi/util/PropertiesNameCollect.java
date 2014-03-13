package com.xiongyingqi.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiongyingqi on 14-3-13.
 */
public class PropertiesNameCollect {
    private static Set<String> names = new HashSet<String>();

    public static void someName(String name) {
        names.add(name);
        StringBuilder builder = new StringBuilder();
        for (String _name : names) {
            builder.append(_name);
        }
        FileHelper.writeStringToFile(new File("properties.txt"), builder.toString());
    }
}
