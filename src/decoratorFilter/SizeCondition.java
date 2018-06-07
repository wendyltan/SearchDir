package decoratorFilter;

import builderModel.SFile;

import java.util.ArrayList;
import java.util.List;

public class SizeCondition extends Condition {
    private String sizeCon=null;
    public SizeCondition(ConditionFilter conditionFilter, String condiction) {
        super(conditionFilter);
        this.sizeCon = condiction;
    }

    @Override
    public List<SFile> searchAndFind(List<SFile> files) {
        files = super.searchAndFind(files);
        List<SFile> tempfiles = new ArrayList<>();
        String[] parts = this.sizeCon.split(" ");
        if (parts.length!=2){
            System.out.println("Condition type wrong!");
            return null;
        }
        // for example, > 2MB
        String operand = parts[0];
        String sizeStr = parts[1];
        String number = sizeStr.replaceAll("[a-zA-Z]", "");
        String type = sizeStr.replaceAll("[0-9]", "");
        long trueSize = Long.valueOf(number) * getTypeSize(type);
        for(SFile file:files){
            switch (operand){
                case ">":
                    if(file.getFileSize()>trueSize){
                        tempfiles.add(file);
                    }
                    break;
                case "<":
                    if(file.getFileSize()<trueSize){
                        tempfiles.add(file);
                    }
                    break;
                case "=":
                    if(file.getFileSize()==trueSize){
                        tempfiles.add(file);
                    }
                    break;
            }
        }


        files = tempfiles;
        return files;
    }

    private long getTypeSize(String type) {
        switch (type){
            case "MB":
                return 1024*1024;
            case "B":
                return 1;
            case "KB":
                return 1024;
            case "GB":
                return 1024*1024*1024;

        }
        return 0;
    }
}
