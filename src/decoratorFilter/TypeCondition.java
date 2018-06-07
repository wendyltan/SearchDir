package decoratorFilter;

import builderModel.SFile;

import java.util.ArrayList;
import java.util.List;

public class TypeCondition extends Condition {
    private  String typeCon = null;
    public TypeCondition(ConditionFilter conditionFilter, String condiction) {
        super(conditionFilter);
        this.typeCon = condiction;
    }

    @Override
    public List<SFile> searchAndFind(List<SFile> files) {
        files = super.searchAndFind(files);
        List<SFile> tempfiles = new ArrayList<>();
        for (SFile file :files){
            if (file.getFileType().equals(this.typeCon)){
                tempfiles.add(file);
            }

        }

        files = tempfiles;

        return files;

    }
}
