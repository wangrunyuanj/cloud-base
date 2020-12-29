package com.runyuanj.authorization.utils;

import cn.hutool.core.lang.Assert;

import java.util.Collection;

public class MyCollectionUtils {

    public static String toStringParams(Collection collection, String character, String isNullDefault) {
        if (collection == null || collection.isEmpty()) {
            return isNullDefault;
        }
        Assert.notBlank(character, "character could not be empty");
        StringBuilder sb = new StringBuilder();
        collection.stream().forEach(o -> sb.append(o.toString()).append(character));
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
