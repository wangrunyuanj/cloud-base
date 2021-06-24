package com.runyuanj.authorization.filter.service;

public interface WhiteListFilterService {

    String getWhiteListPath();

    void addWhiteList(String path);

}
