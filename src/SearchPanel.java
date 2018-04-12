

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

        // retrive filelist
        if (!path.isEmpty()){
            try {
                panel.retrieveInfos(dirs,path,0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //opearate only if dirs is not empty
        if (!dirs.isEmpty()){
            System.out.println("Please choose sort method:(1/2/3 for alpha,type,date sort");
            Scanner choice = new Scanner(System.in);
            Sort sort=null;
            switch (choice.nextInt()){
                case 1:
                    sort = new AlphaSort();
                    break;
                case 2:
                    sort = new TypeSort();
                    break;
                case 3:
                    sort = new DateSort();
                    break;
                default:
                    sort = new AlphaSort();
                    break;
            }
            //sort and show
            for (SDirectory dir : dirs){
                System.out.print("\\---"+dir.getFileName()+'\n');
                dir.setSortCriteria(sort);
                dir.getSortCriteria().sort(dir.getManager().getFileList());
            }

            //log saving
            try {
                mode.saveLogFile(path,dirs);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //log reading
            mode.getLogData();


        }



    }


    private void retrieveInfos(List<SDirectory> dirs , String dirName, int depth) throws IOException {


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
//                System.out.println(sfile.getFilePath());
            }
            return;
        }


        for (int j = 0; j < depth; j++) {
//            System.out.print("  ");
        }
//        System.out.print("|--");
//        System.out.println(dirFile.getName());

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
                    //如果是文件，则直接输出文件名
                    for (int j = 0; j < currentDepth; j++) {
//                        System.out.print("   ");
                    }
//                    System.out.print("|--");
//                    System.out.println(name);

                    //使用文件类保存信息
                    SFile sfile = new SFile(file);
                    directory.getManager().getFileList().add(sfile);
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
