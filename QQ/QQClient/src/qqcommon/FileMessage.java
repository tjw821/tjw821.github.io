package qqcommon;

import java.io.Serializable;

public class FileMessage implements Serializable {
    private char[] fileChars;
    private int fileLen;
    private String dest;
    private String src;


    public char[] getFileChars() {
        return fileChars;
    }

    public void setFileChars(char[] fileChars) {
        this.fileChars = fileChars;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
