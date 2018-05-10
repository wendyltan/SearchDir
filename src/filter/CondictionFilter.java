package filter;

import code.SFile;

import java.util.List;

public interface CondictionFilter {

    List<SFile> searchAndFind(List<SFile> files);
}
