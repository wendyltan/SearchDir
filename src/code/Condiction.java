package code;

import java.util.List;

public abstract class Condiction implements CondictionFilter{

    CondictionFilter filter;

    public Condiction(CondictionFilter condictionFilter){
        this.filter = condictionFilter;

    }

    @Override
    public List<SFile> searchAndFind(String path) {
        filter.searchAndFind(path);
        return null;
    }
}
