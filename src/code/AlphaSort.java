package code;

import java.text.Collator;
import java.util.Comparator;
import java.util.List;


public class AlphaSort implements Sort {

    private StringBuilder builder;

    public void setBuilder(StringBuilder builder) {
        this.builder = builder;
    }


    @Override
    public StringBuilder sort(List<SFile> fileList) {


        fileList.sort(new Comparator<SFile>() {
            @Override
            public int compare(SFile o1, SFile o2) {
                Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
                return ((Collator) cmp).compare(o1.getFileName(),o2.getFileName());
            }
        });

        for (SFile file:fileList){
//            System.out.println("\t"+file.getFileInfo());
           builder.append("\t").append(file.getFileInfo());
        }

        return builder;
    }
}