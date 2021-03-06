package gui;

import modeFactory.*;
import decoratorFilter.InfoGetter;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import builderModel.FileListManager;
import builderModel.SDirectory;
import sortStrategy.AlphaSort;
import sortStrategy.DateSort;
import sortStrategy.Sort;
import sortStrategy.TypeSort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SearchGuiHandler {

    @FXML
    private Button searchBtn;
    @FXML
    private TextArea leftLogTextarea;
    @FXML
    private TextArea rightLogTextarea;
    @FXML
    private CheckMenuItem compare_menu;
    @FXML
    private CheckMenuItem log_menu;
    @FXML
    private MenuItem help_menu;

    @FXML
    private Button compareLogBtn;
    @FXML
    private Button readLogBtn;
    @FXML
    private ChoiceBox<String> chooseSort;
    @FXML
    private ChoiceBox<String> chooseShow;
    @FXML
    private TextField pathText;
    @FXML
    private Label hintLb;



    private List<SDirectory> dirs = new ArrayList<>();
    private InfoGetter info = new InfoGetter();
    private String path;
    private Mode mode;
    private TextArea current;
    private Sort sort=null;
    //显示在左窗口还是右窗口
    private static final int LEFT_WINDOW = 0 ;
    private static final int RIGHT_WINDOW = 1;

    private static boolean FIRST_SEARCH =true;
    private static boolean PATH_THE_SAME = true;


    @FXML
    public void initialize() {
        /**
         * doing some initialize job with gui controls
         */

        ObservableList<String> availableSorts = FXCollections.observableArrayList("AlphaSort", "TypeSort","DateSort");
        chooseSort.setItems(availableSorts);
        chooseSort.setValue(availableSorts.get(0));
        chooseSort.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                sort =null;
                String type = newValue;
                if (type == "AlphaSort") {
                    sort = new AlphaSort();
                    hintLb.setText("Using AlphaSort!");
                }else if(type.equals("TypeSort")){
                    sort = new TypeSort();
                    hintLb.setText("Using TypeSort!");
                }else if (type.equals("DateSort")){
                    sort = new DateSort();
                    hintLb.setText("Using DateSort!");
                }

            }
        });

        ObservableList<String> availableWindow = FXCollections.observableArrayList("left","right");
        chooseShow.setItems(availableWindow);
        chooseShow.setValue(availableWindow.get(0));
        chooseShow.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String type = newValue;
                if (type.equals("left") ) {
                    current = chooseLog(LEFT_WINDOW);
                    hintLb.setText("Using Left Window!");
                }else if(type.equals("right")){
                    current = chooseLog(RIGHT_WINDOW);
                    hintLb.setText("Using Right Window!");
                }
            }
        });

        leftLogTextarea.setEditable(false);
        rightLogTextarea.setEditable(false);


        //initialize as default
        sort = new AlphaSort();
        current = leftLogTextarea;
        compareLogBtn.setDisable(true);
        log_menu.setSelected(true);
    }


    /**
     * handle search event.Enter directory path and
     * save log file
     * then read from log file and show on left_window
     * @param event
     */

    @FXML
    protected void handleSearch(ActionEvent event) {

        current.clear();
        dirs.clear();
        boolean ori_or_new;
        if (FIRST_SEARCH){
            ori_or_new = false;
            FIRST_SEARCH = false;
        }else{
            ori_or_new = checkIfPathChange();
        }

        //假设现在输入的目录格式都是正确的
        path = pathText.getText();
        //以后加入path的有效性鉴别函数
        // retrive filelist
        if (!path.isEmpty()) {
            try {
                info.retrieveInfos(dirs, path, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!dirs.isEmpty()) {
            //using current sortStrategy method
            sortDir(dirs);
            long before = System.currentTimeMillis();

            //save log
            mode = ModeFactory.getMode("Log");
            LogMode log = (LogMode) mode;
            try {
                if (!ori_or_new){
                    log.saveLogFile(path,dirs,ModeFactory.ORI_LOG);
                }else{
                    log.saveLogFile(path,dirs,ModeFactory.NEW_LOG);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            //read log and show
            BufferedInputStream inputStream;
            if (!ori_or_new)
                inputStream = ModeFactory.getLogData(ModeFactory.ORI_LOG);
            else
                inputStream = ModeFactory.getLogData(ModeFactory.NEW_LOG);
            readFile(inputStream,current);

            long after = System.currentTimeMillis();
            hintLb.setText("Consume time: "+ String.valueOf((after-before)/ 1000D) + "s");

        }
    }

    private boolean checkIfPathChange() {
        if (!path.isEmpty()&&path.equals(pathText.getText())){
            //directory haven't change,write log to new
            PATH_THE_SAME = true;
            return true;
        }else if (!path.equals(pathText.getText())){
            //directory change,still write into ori
            PATH_THE_SAME = false;
            return false;
        }else{
            hintLb.setText("Something is wrong!");
        }
        return false;
    }

    /**
     * sortStrategy directory using selected sortStrategy method
     * @param dirs
     * @return
     */
    private boolean sortDir(List<SDirectory> dirs){
        if (!dirs.isEmpty()) {
            for (SDirectory dir : dirs) {
                dir.setSortCriteria(sort);
                dir.getSortCriteria().sort(FileListManager.getInstance().getFileList(dir.getFilePath()));
            }
            return true;
        }
        return false;

    }

    /**
     * choose textarea according to window modeFactory
     * @param windowId
     * @return
     */
    private TextArea chooseLog(int windowId){
        switch (windowId){
            case LEFT_WINDOW:
                chooseShow.setValue("left");
                return leftLogTextarea;
            case RIGHT_WINDOW:
                chooseShow.setValue("right");
                return rightLogTextarea;
        }
        return null;
    }

    /**
     * read log from file.
     * @param actionEvent
     */
    public void handleLogRead(ActionEvent actionEvent) {
        current.clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose log file");
        File logFile = fileChooser.showOpenDialog(SearchGui.getGuiStage());
        if (logFile!=null){
            BufferedInputStream inputStream = null;
            try {
                inputStream = new BufferedInputStream(new FileInputStream(logFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            long before = System.currentTimeMillis();
            readFile(inputStream,current);
            long after = System.currentTimeMillis();
            hintLb.setText("Log read success! Consume time: "+ String.valueOf((after-before)/ 1000D) + "s");

        }else{
            hintLb.setText("Didn't choose any file!");
        }


    }

    /**
     * this function handle file read logic
     * @param inputStream
     */
    private void readFile(BufferedInputStream inputStream,TextArea current){
        //新开线程防止主线程卡顿
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    int bytesRead = 0;
                    byte[] buff = new byte[4096];
                    while ((bytesRead = inputStream.read(buff)) != -1) {
                        // 显示行号
                        String chunk = new String(buff, 0, bytesRead);
                        //非ui进程更新界面
                        Platform.runLater(() -> current.appendText(chunk));
                    }

                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();

    }

    public void handleCompare(ActionEvent actionEvent) {
        //compare between ori and new log only if both ori and new is belongs to the same path.
        if (PATH_THE_SAME){
            //read ori log and set to left

            current = chooseLog(LEFT_WINDOW);
            if (current.getText() != null)
                current.clear();
            if (mode == null) {
                //compare file without doing any search
                mode = ModeFactory.getMode("Log");
            }
            BufferedInputStream inputStream = ModeFactory.getLogData(ModeFactory.ORI_LOG);
            System.out.println(current);

            readFile(inputStream,current);

            //do differ and read it then set to right
            current = chooseLog(RIGHT_WINDOW);
            if (current.getText()!= null) {
                current.clear();
            }
            mode = ModeFactory.getMode("Compare");
            CompareMode compare = (CompareMode) mode;
            BufferedInputStream diff = null;

            try {
                diff = compare != null ? compare.compareLogFile() : null;
                if (diff==null){
                    hintLb.setText("File size the same,I don't think we have to compare!");
                }else{
                    readFile(diff,current);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{
            hintLb.setText("Path not the same between two log files!Try again");
        }

    }

    public void onModeCompare(ActionEvent actionEvent) {
        compareLogBtn.setDisable(false);
        compare_menu.setSelected(true);
        log_menu.setSelected(false);
        searchBtn.setDisable(true);
        hintLb.setText("You are in compare mode!");
    }

    public void onModeLog(ActionEvent actionEvent) {
        compareLogBtn.setDisable(true);
        log_menu.setSelected(true);
        compare_menu.setSelected(false);
        searchBtn.setDisable(false);
        hintLb.setText("You are in log mode!");
    }

    /**
     * help menu and about dialog handler
     * @param actionEvent
     */

    public void onHelp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("关于");
        alert.setHeaderText("目录搜索差异比较程序v1.0");
        alert.setContentText("当前版本处于测试状态，请阅读readme");
        Label label = new Label("readme.md :");

        File file = new File("README.md");
        TextArea textArea = new TextArea();


        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            int bytesRead = 0;
            byte[] buff = new byte[4096];
            while ((bytesRead = inputStream.read(buff)) != -1 ) {
                // 显示行号
                String chunk = new String(buff, 0, bytesRead);
                textArea.appendText(chunk);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.setEditable(false);
        textArea.setWrapText(false);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }
}
