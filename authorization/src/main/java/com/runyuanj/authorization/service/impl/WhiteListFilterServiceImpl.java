package com.runyuanj.authorization.service.impl;

import com.runyuanj.authorization.service.WhiteListFilterService;
import com.runyuanj.authorization.utils.MyCollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class WhiteListFilterServiceImpl implements WhiteListFilterService {

    private Set<String> paths = new HashSet<>(8);

    private static final String DEFAULT_PATHS = "/login, /jwt";

    @Override
    public String getWhiteListPath() {
        if (paths.isEmpty()) {
            return DEFAULT_PATHS;
        }
        return MyCollectionUtils.toStringParams(paths, ",", "");
    }

    @Override
    public void addWhiteList(String path) {
        paths.add(path);
    }
}
