package com.yoshino.service;

import com.yoshino.util.ThreeDESUtil;

public class ServiceWork {

    public static void main(String[] args) {
        String filePath = "/Users/xiaoyi/IdeaProjects/comyoshinoutil/src/file/母婴提取数据3-7.xlsx";
        ThreeDESUtil.getDecryptUserIdFile(filePath, 1, 1, 2);
    }

}
