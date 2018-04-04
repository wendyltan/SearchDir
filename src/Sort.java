import java.util.List;

public class Sort implements sortMethod {
    @Override
    public void sort(List<SFile> fileList,String type) {
        switch (type){
            case "date":
                DateSort(fileList);
                break;
            case "alpha":
                AlphaSort(fileList);
                break;
            case "type":
                TypeSort(fileList);
                break;
            default:
                System.out.println("Please enter valid sort type!");

        }
    }

    private void printAfterSort(){

    }

    /**
     * 通过首字母顺序排序
     * @param fileList
     */
    private void AlphaSort(List<SFile> fileList){

    }

    private void TypeSort(List<SFile> fileList){

    }

    private void DateSort(List<SFile> fileList){

    }
}
