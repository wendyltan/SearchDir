package code;

import java.util.ArrayList;
import java.util.List;

public class TypeCondition extends Condiction {
    private  String typeCon = null;
    public TypeCondition(CondictionFilter condictionFilter,String condiction) {
        super(condictionFilter);
        this.typeCon = condiction;
    }


    @Override
    public List<SFile> searchAndFind(String path) {
        super.searchAndFind(path);
        List<SFile> files = FileListManager.getFileList(path);
        List<SFile> tempfiles = new ArrayList<>();
        for (SFile file :files){
            if (file.getFileType().equals(this.typeCon)){
                tempfiles.add(file);
            }

        }
        return tempfiles;

    }
}
