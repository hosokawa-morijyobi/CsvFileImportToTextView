package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class SampleController implements Initializable {

	@FXML
	private ListView<String> listView;
	@FXML
	private TextArea textArea;

	private  ObservableList<String> items ;

	private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	stage = Main.getInstance().getStage();
    	listView.getItems().add("One");
    	listView.getItems().add("Two");
    	listView.getItems().add("Three");

    	this.textArea.setText("初期化");
		textArea.setFont( Font.font( "MS Mincho" , 12 ) );

		items = FXCollections.observableArrayList();
		listView.setItems(items);
		// 編集可能にする。
		listView.setEditable(true);
		//TextFieldTableCellクラスによって表セルをテキスト・フィールドとして実装できる
		listView.setCellFactory(TextFieldListCell.forListView());
    }

//    //呼び出されて、listに対して追加を行うメソッド
//    public void addListView(String Message) {
//    	listView.getItems().add(Message);
//    }

    @FXML
	protected void handleButtonAction(ActionEvent e) {
    	FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		fileChooser.getExtensionFilters().addAll(
				   new FileChooser.ExtensionFilter("CSV", "*.csv"),
				   new FileChooser.ExtensionFilter("All", "*.*")
		            );
		//初期ディレクトリの設定
		fileChooser.setInitialDirectory(
				   new File(System.getProperty("user.home"))
				        );

		Stage stage = Main.getInstance().getStage();

		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			ArrayList<String> array = readCSV(file);

				items.clear();//ListViewのObservableListアイテムを消去しておく。
			  String display= new String();
			  for (String item : array){
			        System.out.println(item);
			        display = display + item+ "\r\n";
			        items.add(item);//ListViewのObservableListアイテムとしてadd
			   }
		        textArea.setText(display);


		}
    }

    boolean checkBeforeReadfile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.canRead()) {
                return true;
            }
        }
        return false;
    }

    ArrayList<String> readCSV(File file ) {

    	ArrayList<String> array=new ArrayList();//テキストエリアに表示させるテキスト
    	try {
            if (checkBeforeReadfile(file)) {
                FileInputStream in = new FileInputStream(file);
                InputStreamReader sr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(sr);

                String line;
                while ((line = br.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(line, ",");

                    	String item;
                        while (token.hasMoreTokens()) {
                        	item = (String)token.nextToken();
                        	array.add(item);
                        }

                }
                return array;

            } else {
                System.out.println("No file exists or can't open.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException : " + e.getMessage());
        }
		return array;

    }

}




