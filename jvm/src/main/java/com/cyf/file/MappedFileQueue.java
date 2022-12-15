package com.cyf.file;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 陈一锋
 * @date 2022/12/15 10:18 下午
 */
public class MappedFileQueue {

    private List<MappedFile> mappedFiles = new CopyOnWriteArrayList<>();
    private String storePath;
    public MappedFileQueue(String storePath) {

    }

}
