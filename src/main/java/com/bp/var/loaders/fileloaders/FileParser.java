package com.bp.var.loaders.fileloaders;

/**
 * Created by rothb1 on 08/06/2016.
 */
public abstract class FileParser {
    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
