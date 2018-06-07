package decoratorFilter;

import model.SFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateCondition extends Condiction {
    private  String dateCon = null;

    public DateCondition(CondictionFilter condictionFilter, String condition) {
        super(condictionFilter);
        this.dateCon = condition;
    }

    @Override
    public List<SFile> searchAndFind(List<SFile> files) {
        files = super.searchAndFind(files);
        List<SFile> tempfiles = new ArrayList<>();

        String[] parts = this.dateCon.split(" ");
        if (parts.length!=2){
            System.out.println("Condition type wrong!");
            return null;
        }
        String suffix = parts[0];
        String dateStr = parts[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long conditionTime = date.getTime();


        for (SFile file :files){
            if (suffix.equals("before")){
                if (file.getLastEditDate()<conditionTime){
                    tempfiles.add(file);
                }
            }else if(suffix.equals("after")){
                if (file.getLastEditDate()>conditionTime){
                    tempfiles.add(file);
                }
            }else if(suffix.equals("equals")){
                if (file.getLastEditDate()==conditionTime){
                    tempfiles.add(file);
                }
            }
            else{
                System.out.println("no such condition!pass...");
            }

        }
        files = tempfiles;

        return files;


    }
}
