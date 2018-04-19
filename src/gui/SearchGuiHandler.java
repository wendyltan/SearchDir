package gui;

import code.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchGuiHandler {

    @FXML
    private Button searchBtn;
    @FXML
    private TextArea oldLogTextarea;
    @FXML
    private TextArea newLogTextarea;
    @FXML
    private MenuItem compare_menu;
    @FXML
    private MenuItem log_menu;
    @FXML
    private MenuItem help_menu;
    @FXML
    private Button saveLogBtn;
    @FXML
    private Button readLogBtn;
    @FXML
    private ChoiceBox chooseSort;
    @FXML
    private TextField pathText;
    @FXML
    private Label hintLb;


    private List<SDirectory> dirs = new ArrayList<>();
    private SearchPanel panel = new SearchPanel();
    //下次改进使其可以选择
    private Sort sort = new AlphaSort();

    private String path;
    private ModeFactory mode;

    

    @FXML
    protected void handleSearch(ActionEvent event) {
        //清空上一次的路径
        if (!oldLogTextarea.getText().isEmpty()){
            oldLogTextarea.clear();
        }

        //假设现在输入的目录格式都是正确的
        path = pathText.getText();
        //以后加入path的有效性鉴别函数

        // retrive filelist
        if (!path.isEmpty()) {
            try {
                panel.retrieveInfos(dirs, path, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!dirs.isEmpty()) {
            sortDir(dirs);
            long before = System.currentTimeMillis();
            //save log
            mode = ModeFactory.getMode("Log");
            LogMode log = (LogMode) mode;
            try {
                log.saveLogFile(path,dirs);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //read log and show
            BufferedInputStream inputStream = mode.getLogData(ModeFactory.ORI_LOG);
            try{

                int bytesRead = 0;
                byte[] buff = new byte[4096];
                while ((bytesRead = inputStream.read(buff)) != -1 ) {
                    // 显示行号
                    String chunk = new String(buff, 0, bytesRead);
                    oldLogTextarea.appendText(chunk);
                }

                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            long after = System.currentTimeMillis();
            hintLb.setText("Consume time: "+ String.valueOf((after-before)/ 1000D) + "s");
            saveLogBtn.setDisable(false);

        }
    }

    public boolean sortDir(List<SDirectory> dirs){
        if (!dirs.isEmpty()) {
            //sort and show
            Sort sort = new AlphaSort();
            for (SDirectory dir : dirs) {
                dir.setSortCriteria(sort);
                dir.getSortCriteria().sort(FileListManager.getFileList(dir.getFilePath()));
            }
            return true;
        }
        return false;

    }

    public void handleLogSave(ActionEvent actionEvent) {

        if (sortDir(dirs)){
            mode = ModeFactory.getMode("Log");
            LogMode log = (LogMode) mode;
            try {
                log.saveLogFile(path,dirs);
            } catch (IOException e) {
                e.printStackTrace();
            }
            hintLb.setText("Log save success!");
            readLogBtn.setDisable(false);
        }else{
            hintLb.setText("You can't save log before u search!");
            saveLogBtn.setDisable(true);
            readLogBtn.setDisable(true);
        }

    }

    public void handleLogRead(ActionEvent actionEvent) {
        oldLogTextarea.clear();
        if (mode==null){
            hintLb.setText("You can't read before u save!");
            readLogBtn.setDisable(true);
        }else{
            BufferedInputStream inputStream = mode.getLogData(ModeFactory.ORI_LOG);
            try{

                int bytesRead = 0;
                byte[] buff = new byte[4096];
                long before = System.currentTimeMillis();
                while ((bytesRead = inputStream.read(buff)) != -1 ) {
                    // 显示行号
                    String chunk = new String(buff, 0, bytesRead);
                    oldLogTextarea.appendText(chunk);
                }
                long after = System.currentTimeMillis();

                hintLb.setText("Log read success! Consume time: "+ String.valueOf((after-before)/ 1000D) + "s");
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
