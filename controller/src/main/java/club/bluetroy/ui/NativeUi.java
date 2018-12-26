package club.bluetroy.ui;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-12-03
 * Time: 09:56
 */
public class NativeUi extends Application {
    private static String path;

    public static String getPath() {
        Application.launch(NativeUi.class);
        return path;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setAlwaysOnTop(true);
        stage.setWidth(1);
        stage.setHeight(1);
        stage.initStyle(StageStyle.UNDECORATED);
        //stage的标题将会是hello
        stage.show();
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择文件夹");
        File file = chooser.showDialog(stage);
        path = file.getPath();
        stage.close();
    }
}
