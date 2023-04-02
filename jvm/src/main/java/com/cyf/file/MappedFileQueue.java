package com.cyf.file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 陈一锋
 * @date 2022/12/15 10:18 下午
 */
@Slf4j
public class MappedFileQueue {

    private List<MappedFile> mappedFiles = new CopyOnWriteArrayList<>();
    private String storePath;
    private Integer mappedFileSize;

    public MappedFileQueue(String storePath, Integer fileSize) {
        this.storePath = storePath;
        this.mappedFileSize = fileSize;
    }

    public boolean load() {
        File file = new File(storePath);
        File[] files = file.listFiles();
        if (files != null) {
            Arrays.sort(files);
            for (File f : files) {
                if (f.length() != mappedFileSize) {
                    log.warn(f + "\t" + f.length() + " length not matched message store config value, please check it manually");
                    return false;
                }
                //重新生成mappedFile
                MappedFile mappedFile;
                try {
                    mappedFile = new MappedFile(f.getPath(), mappedFileSize);
                    mappedFile.setFlushedPosition(mappedFileSize);
                    mappedFile.setWritePoint(mappedFileSize);
                } catch (IOException e) {
                    log.error("load MappedFile fail:", e);
                    return false;
                }
                mappedFiles.add(mappedFile);
            }
        }
        return true;
    }

    public MappedFile getLastMappedFile() {
        return null;
    }
}
