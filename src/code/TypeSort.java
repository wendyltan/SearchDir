package code;

import java.util.Comparator;
import java.util.List;

public class TypeSort implements Sort {

    private StringBuilder builder;
    public void setBuilder(StringBuilder builder) {
        this.builder = builder;
    }


    @Override
    public StringBuilder sort(List<SFile> fileList) {
        fileList.sort(new Comparator<SFile>() {
            @Override
            public int compare(SFile o1, SFile o2) {
                return o1.getFileType().compareTo(o2.getFileType());
            }
        });

        for (SFile file:fileList){
            System.out.println("\t"+file.getFileInfo());
            builder.append("\t"+file.getFileInfo());
        }

        return builder;
    }
}
