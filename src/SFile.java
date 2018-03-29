import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Formatter;

public class SFile {

    /**
     * 文件的基本信息
     */
    private String fileName;
    private String fileSize;
    private String lastEdit;
    private String fileType;
    private String filePath;


    public SFile(File file) throws IOException {
        fileName = file.getName();
        fileSize = String.valueOf(file.length())+'B';
        lastEdit = new Date(file.lastModified()).toString();
        fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        filePath = file.getCanonicalPath();
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getLastEdit() {
        return lastEdit;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileInfo(){
        return String.format("%s,%s,%s,%s\n",fileName,fileSize,fileType,filePath);
    }



}
