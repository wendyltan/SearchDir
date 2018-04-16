package code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchPanel {
    /**
     * 没有界面之前暂时作为主程序
     * @param args
     */

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a file path below");
        String path = scanner.next();

        SearchPanel panel = new SearchPanel();

        List<SDirectory> dirs = new ArrayList<>();

        LogMode mode = new LogMode();
        CompareMode compareMode = new CompareMode();

        // retrive filelist
        if (!path.isEmpty()){
            try {
                panel.retrieveInfos(dirs,path,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //if dirs not empty
        if(panel.sortAndShow(dirs)){
            //log saving
            try {
                mode.saveLogFile(path,dirs);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            //log reading
//            mode.printLog(mode.getLogData(code.Mode.ORI_LOG));
        }


        //read path and write current status and do compare
        System.out.println("Please enter new directory to compare");
        String newPath = scanner.next();
        dirs.clear();
        if (newPath.equals(path)){
            try {
                panel.retrieveInfos(dirs,path,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (panel.sortAndShow(dirs)){
                try {
                    compareMode.compareLogFile(dirs);
//                    mode.printLog(mode.getLogData(code.Mode.NEW_LOG));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("path is not equal,can't compare!the original search path is: "+path);
        }

    }

    public boolean sortAndShow(List<SDirectory> dirs){
        //opearate only if dirs is not empty
        if (!dirs.isEmpty()) {
//            System.out.println("Please choose sort method:(1/2/3 for alpha,type,date sort");
//            Scanner choice = new Scanner(System.in);
            Sort sort ;
//            switch (choice.nextInt()) {
//                case 1:
//                    sort = new code.AlphaSort();
//                    break;
//                case 2:
//                    sort = new code.TypeSort();
//                    break;
//                case 3:
//                    sort = new code.DateSort();
//                    break;
//                default:
//                    sort = new code.AlphaSort();
//                    break;
//            }
            sort = new AlphaSort();
            //sort and show
            for (SDirectory dir : dirs) {
                System.out.print("\\---" + dir.getFileName() + '\n');
                dir.setSortCriteria(sort);
                dir.getSortCriteria().sort(dir.getManager().getFileList());
            }
            return true;
        }
        return false;
    }


    public void retrieveInfos(List<SDirectory> dirs , String dirName, int depth) throws IOException {


        /**
         * 使用此函数获得指定目录下的信息并显示s。
         */
        //获取pathName的File对象
        File dirFile = new File(dirName);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            System.out.println("Do not exit");
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
                    directory.getManager().getFileList().add(sfile);
                    //计算目录类的占空间大小
                    long size = 0;
                    for (SFile f: directory.getManager().getFileList()){
                        size = size+f.getFileSize();
                    }
                    directory.setFileSize(size);
                    directory.setFileSizeStr(directory.getPrintSize(size));

                }
            }
        }

    }

}
