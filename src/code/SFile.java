package code;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SFile {

    /**
     * 文件的基本信息
     */
    private String fileName;
    private String fileSizeStr;
    private long fileSize;
    private String lastEditStr;
    private String fileType;
    private String filePath;
    private long lastEditDate;


    public SFile(File file) throws IOException {
        fileName = file.getName();

        fileSize = file.length();
        fileSizeStr = getPrintSize(file.length());

        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
        lastEditStr = dateformat.format(new Date(file.lastModified()));
        lastEditDate = file.lastModified();

        fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        filePath = file.getCanonicalPath();
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }


    public String getLastEdit() {
        return lastEditStr;
    }

    public long getLastEditDate() {
        return lastEditDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileInfo(){
//        if (fileType=="directory")
//            return String.format("%s,%s,%s\n",fileName,fileType,lastEditStr);
        return String.format("%s,%s,%s,%s\n",fileName, fileSizeStr,fileType,lastEditStr);
    }

    public String getPrintSize(long size) {
        // 如果字节数少于 1024，则直接以 B 为单位，否则先除于 1024，后 3 位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        // 如果原字节数除于 1024 之后，少于 1024，则可以直接以 KB 作为单位
        // 因为还没有到达要使用另一个单位的时候
        // 接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            // 因为如果以 MB 为单位的话，要保留最后 1 位小数，
            // 因此，把此数乘以 100 之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            // 否则如果要以 GB 为单位的，先除于 1024 再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

}
