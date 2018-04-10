import java.io.File;
import java.io.IOException;

public class SDirectory extends SFile {


    /**
     * 通过一个Manager管理
     */

    private FileListManager manager = new FileListManager();
    private Sort sortCriteria;

    public void setSortCriteria(Sort sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public Sort getSortCriteria() {
        return sortCriteria;
    }

    public SDirectory(File file) throws IOException {
        super(file);
        if (file.isDirectory()){
            setFileName(file.getCanonicalPath());
            setFileType("directory");
            setFileSize("None");
        }
    }

    public FileListManager getManager() {
        return manager;
    }


}
