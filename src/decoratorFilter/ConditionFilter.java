package decoratorFilter;

import builderModel.SFile;

import java.util.List;

public interface ConditionFilter {

    List<SFile> searchAndFind(List<SFile> files);
}
