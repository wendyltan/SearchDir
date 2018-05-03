package code;

import java.util.Comparator;
import java.util.List;

public class DateSort implements Sort {


    @Override
    public void sort(List<SFile> fileList) {
        fileList.sort(new Comparator<SFile>() {
            @Override
            public int compare(SFile o1, SFile o2) {
                long diff = o1.getLastEditDate()-o2.getLastEditDate();
                if (diff> 0) {
                    return 1;
                } else if (diff == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }
}
