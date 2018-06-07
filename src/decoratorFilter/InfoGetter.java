package decoratorFilter;

import FileListManager;
import model.SDirectory;
import model.SFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InfoGetter implements CondictionFilter {

    public static void main(String[] args){

        /**
         * only to show decorator pattern in a hard-coding way...
         */
        List<SDirectory> directories = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // create a decoratee
        InfoGetter getter = new InfoGetter();
        System.out.println("Please enter file path:");
        String path = scanner.nextLine();
        try {
            getter.retrieveInfos(directories, path, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SFile> matches = null;

        //using decorator pattern
        System.out.println("Please enter fileType(for example,doc)");
        String fileCondition = scanner.nextLine();
        System.out.println("Please enter time constraint(for example,before/after/equals yyyy-mm-dd)");
        String dateCondition = scanner.nextLine();
        System.out.println("Please enter size constraint(for example, >/=/< 100B/KB/GB");
        String sizeCondition = scanner.nextLine();
        TypeCondition type = new TypeCondition(getter, fileCondition);
        DateCondition date = new DateCondition(type, dateCondition);
        SizeCondiction size = new SizeCondiction(date,sizeCondition);
        int counter = 0;
        System.out.println("Printing out search result!...");
        System.out.println("========================================");

        for (SDirectory directory : directories) {
            matches = size.searchAndFind(FileListManager.getInstance().getFileList(directory.getFilePath()));
            if (matches != null) {
                for (SFile file : matches) {
                    System.out.println(file.getFileInfo());
                }
                counter += matches.size();
            }


        }
        System.out.println("========================================");
        System.out.println("Search finish with " + counter + " results");


    }

    public void retrieveInfos(List<SDirectory> dirs , String dirName, int depth) throws IOException {

        /**
         * Search given directory path and store files and dirs' info iterately into a list
         */
        //获取pathName的File对象

        File dirFile = new File(dirName);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            System.out.println("Do not exists!");
            return;
        }
        //判断如果不是一个目录，就判断是不是一个文件，是文件则输出文件路径
        if (!dirFile.isDirectory()) {
            if (dirFile.isFile()) {
                SFile sfile = new SFile(dirFile);
                dirs.add((SDirectory) sfile);
            }
            return;
        }

        SDirectory directory = new SDirectory(dirFile);
        dirs.add(directory);

        List<SFile> files = new ArrayList<>();
        FileListManager.getInstance().addFileList(directory.getFilePath(),files);

        //获取此目录下的所有文件名与目录名
        String[] fileList = dirFile.list();
        int currentDepth=depth+1;
        if (fileList!=null){

            for (int i = 0; i < fileList.length; i++) {
                //遍历文件目录
                String item = fileList[i];
                File file = new File(dirFile.getPath(),item);
                String name = file.getName();
                //如果是一个目录，搜索深度depth++，输出目录名后，进行递归
                if (file.isDirectory()) {
                    //递归
                    retrieveInfos(dirs,file.getCanonicalPath(),currentDepth);
                }else{

                    //使用文件类保存信息
                    SFile sfile = new SFile(file);
                    FileListManager.getInstance().updateFileList(directory.getFilePath(),sfile);

                    //计算目录类的占空间大小
                    long size = 0;

                    for (SFile f: FileListManager.getInstance().getFileList(directory.getFilePath())){
                        size = size +f.getFileSize();
                    }
                    directory.setFileSize(size);
                    directory.setFileSizeStr(directory.getPrintSize(size));

                }

            }
        }

        //每一轮都重新计算最外面那个文件夹有多大。最后计算整个文件夹列表中最大那个文件夹（树顶）
        long i = 0;
        for(SDirectory dir : dirs){
            if (i>0){
                i+=dir.getFileSize();
            }
            i++;
        }
        SDirectory outer = dirs.get(0);
        outer.setFileSize(i);
        outer.setFileSizeStr(outer.getPrintSize(i));
        if (outer.getFileType().equals("directory")&&outer.getFileName().endsWith(":\\")){
            outer.setFileType("Disk root");
            outer.setLastEditStr("");
            System.out.println(outer.getFileName());
        }

    }


    @Override
    public List<SFile> searchAndFind(List<SFile> files) {
        return files;
    }
}
