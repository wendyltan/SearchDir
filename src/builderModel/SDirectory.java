package builderModel;

import sortStrategy.Sort;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SDirectory extends SFile {


    /**
     * 通过一个Manager管理
     */

    private Sort sortCriteria;

    public Builder mBuilder;

    public void setSortCriteria(Sort sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public Sort getSortCriteria() {
        return sortCriteria;
    }


    public SDirectory(Builder builder){
        super(builder);
        this.mBuilder = builder;
        this.fileType = builder.bfileType;
        this.fileSize = builder.bfileSize;
        this.fileSizeStr = builder.bfileSizeStr;
        this.lastEditDate = builder.blastEditDate;
    }

    public static class Builder extends SFile.Builder{

        public Builder(File file) throws IOException {
            super(file);
            if (file.isDirectory()){
                this.bfileName = this.bfilePath;
                this.bfileType = "directory";
            }

        }

        public Builder setEditDateStr(){
            SimpleDateFormat dateformat=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
            this.bfileLastEditStr = dateformat.format(new Date(blastEditDate));
            return this;
        }

        public Builder setFileType(String fileType){
            this.bfileType = fileType;
            return this;
        }

        public Builder setFileSize(long fileSize){
            this.bfileSize = fileSize;
            return this;
        }

        public Builder setFileSizeStr(String fileSizeStr){
            this.bfileSizeStr = fileSizeStr;
            return this;
        }

        public Builder setEditDateStr(String editDateStr){
            this.bfileLastEditStr = editDateStr;
            return this;
        }

        public SDirectory build(){
            return new SDirectory(this);
        }
    }





}
