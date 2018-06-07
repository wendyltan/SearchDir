package builderModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SFile {

    /**
     * 文件的基本信息
     */
    protected String fileName;
    protected long fileSize;
    protected final String filePath;
    protected long lastEditDate;
    protected String fileType;

    protected String lastEditStr;
    protected String fileSizeStr;
    protected final String fileHash;


    public SFile(Builder builder){
        //must require
        this.fileName = builder.bfileName;
        this.fileSize = builder.bfileSize;
        this.filePath = builder.bfilePath;
        this.fileType = builder.bfileType;
        this.lastEditDate = builder.blastEditDate;

        //choosen options
        this.fileSizeStr = builder.bfileSizeStr;
        this.lastEditStr = builder.bfileLastEditStr;
        this.fileHash = builder.bfileHash;
    }

    public static class Builder{
        protected File file;
        protected String bfileName;
        protected String bfilePath;
        protected long bfileSize;
        protected String bfileType;
        protected long blastEditDate;
        protected String bfileLastEditStr;
        protected String bfileHash;
        protected String bfileSizeStr;

        public Builder(File file) throws IOException {
            this.file = file;
            this.bfilePath = file.getCanonicalPath();
            this.bfileName = file.getName();
            this.bfileSize = file.length();
            this.bfileType = bfileName.substring(file.getName().lastIndexOf(".") + 1);
            this.blastEditDate = file.lastModified();

        }

        public Builder setEditDateStr(){
            SimpleDateFormat dateformat=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
            this.bfileLastEditStr = dateformat.format(new Date(blastEditDate));
            return this;
        }

        public Builder setFileSizeStr(){
            this.bfileSizeStr = getPrintSize(bfileSize);
            return this;
        }

        public Builder setFileHash(){
            this.bfileHash = String.valueOf(file.hashCode());
            return this;
        }

        public SFile build(){
            return new SFile(this);
        }


    }

    /**
     * All kinds of setters and getters
     *
     */


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

    public long getFileSize() {
        return fileSize;
    }


    public String getFilePath() {
        return filePath;
    }

    public String getFileInfo(){
        return String.format("%s,%s,%s,%s\n",fileName, fileSizeStr,fileType,lastEditStr);
    }

    public static String getPrintSize(long size) {
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
