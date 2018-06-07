package decoratorFilter;

import builderModel.SFile;

import java.util.List;

public abstract class Condition implements ConditionFilter {

    ConditionFilter filter;


    public Condition(ConditionFilter conditionFilter){
        this.filter = conditionFilter;
    }


    @Override
    public List<SFile> searchAndFind(List<SFile> files) {
        files = filter.searchAndFind(files);
        return files;
    }
}
