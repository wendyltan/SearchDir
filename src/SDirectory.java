import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SDirectory extends SFile {


    /**
     * 通过一个Manager管理
     */

    private FileListManager manager = new FileListManager();


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
