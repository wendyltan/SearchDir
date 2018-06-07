package sortStrategy;

import builderModel.SFile;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;


public class AlphaSort implements Sort {


    @Override
    public void sort(List<SFile> fileList) {


        fileList.sort(new Comparator<SFile>() {
            @Override
            public int compare(SFile o1, SFile o2) {
                Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
                return ((Collator) cmp).compare(o1.getFileName(),o2.getFileName());
            }
        });

    }

}
