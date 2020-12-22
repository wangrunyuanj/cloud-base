package com.runyuanj.authorization.service;

public interface WhiteListFilterService {

    String getWhiteListPath();

    void addWhiteList(String path);

}
