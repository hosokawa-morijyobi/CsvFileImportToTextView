package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class SampleController {

	@FXML
	private TextArea textarea;
	private ListView<String> listview;
	private String display="";//テキストエリアに表示させるテキスト

	@FXML
	protected void onButtonClick(ActionEvent e) {

		textarea.setText("初期化");
		textarea.setFont( Font.font( "MS Mincho" , 12 ) );
		//ListView<String> listview = new ListView<>();

		System.out.println("クリックされました！");
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
//		File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String words = readCSV(file);
            if (words != null) {
            	System.out.println(words);
            	textarea.setText(words);
            }

            ObservableList<String> array= readCSVToArray(file);
            if (array != null) {
            	listview.setItems( array );
            }
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

    String readCSV(File file) {

    	String display="";//テキストエリアに表示させるテキスト

        try {
            if (checkBeforeReadfile(file)) {
                FileInputStream in = new FileInputStream(file);
                InputStreamReader sr = new InputStreamReader(in, "UTF-8");
//                InputStreamReader sr = new InputStreamReader(in, "Shift_JIS");
                BufferedReader br = new BufferedReader(sr);

                String line;
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(line, ",");

                    if (isHeader) {
                        while (token.hasMoreTokens()) {

                            display=display+" " + token.nextToken();
                        }
                        isHeader = false;
                        display=display+"\r\n"+"----------------";
                    } else {
                        while (token.hasMoreTokens()) {
                        	display=display+"\r\n" + token.nextToken();
                        }
                    }



                }
                return display;

            } else {
                System.out.println("No file exists or can't open.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException : " + e.getMessage());
        }
		return display;



    }


    ObservableList<String> readCSVToArray(File file) {

    	//ArrayList<String> array=new ArrayList();//テキストエリアに表示させるテキスト
    	ObservableList<String>array = FXCollections.observableArrayList(  );
        try {
            if (checkBeforeReadfile(file)) {
                FileInputStream in = new FileInputStream(file);
                InputStreamReader sr = new InputStreamReader(in, "UTF-8");
//                InputStreamReader sr = new InputStreamReader(in, "Shift_JIS");
                BufferedReader br = new BufferedReader(sr);

                String line;
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(line, ",");


                   if (isHeader) {
                        while (token.hasMoreTokens()) {
                        	array.add(token.nextToken());
                        }
                        isHeader = false;
                        array.add("--------------");

                    } else {
                        while (token.hasMoreTokens()) {
                        	array.add(token.nextToken());
                        }
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
