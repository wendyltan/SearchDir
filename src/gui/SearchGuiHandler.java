package gui;

import code.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchGuiHandler {

    @FXML
    private Button searchBtn;
    @FXML
    private TextArea logTextarea;
    @FXML
    private TextField pathText;
    @FXML
    private Label hintLb;

    @FXML
    protected void handleSearch(ActionEvent event) {
        if (!logTextarea.getText().isEmpty()){
            logTextarea.clear();
        }
        //假设现在输入的目录格式都是正确的
        String path = pathText.getText();
        //加入path的有效性鉴别函数
        List<SDirectory> dirs = new ArrayList<>();
        SearchPanel panel = new SearchPanel();

        // retrive filelist
        if (!path.isEmpty()) {
            try {
                panel.retrieveInfos(dirs, path, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!dirs.isEmpty()) {

            Sort sort;
            sort = new AlphaSort();
            long before = System.currentTimeMillis();
            for (SDirectory dir : dirs) {
                logTextarea.appendText("\\---" + dir.getFileName() + '\n');
                dir.setSortCriteria(sort);
                ((AlphaSort) sort).setBuilder(new StringBuilder());
                logTextarea.appendText(dir.getSortCriteria().sort(dir.getManager().getFileList()).toString());
                //set used builder to null to auto gc
                ((AlphaSort)dir.getSortCriteria()).setBuilder(null);
                System.out.println("Finish dir: "+dir.getFileName()+" processing...");
            }
            long after = System.currentTimeMillis();
            hintLb.setText("Consume time: "+ String.valueOf((after-before)/ 1000D) + "s");

        }
    }
}
