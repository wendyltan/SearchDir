import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SDirectory extends SFile {

    private ArrayList<SFile> mFileList = new ArrayList<>();

    public SDirectory(File file) throws IOException {
        super(file);
        if (file.isDirectory()){
            setFileName(file.getCanonicalPath());
            setFileType("directory");
            setFileSize("None");
        }
    }

    public ArrayList<SFile> getFileList(){
        return mFileList;
    }

    public void setFileList(ArrayList<SFile> list){
        mFileList = list;
    }




}
