package decoratorFilter;

import builderModel.SFile;

import java.util.List;

public abstract class Condiction implements CondictionFilter {

    CondictionFilter filter;


    public Condiction(CondictionFilter condictionFilter){
        this.filter = condictionFilter;
    }


    @Override
    public List<SFile> searchAndFind(List<SFile> files) {
        files = filter.searchAndFind(files);
        return files;
    }
}
