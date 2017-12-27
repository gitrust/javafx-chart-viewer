

import java.io.File;
import java.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.BarChart;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.event.EventHandler;

// http://www.java2s.com/Code/Java/JavaFX/DraganddropfiletoScene.htm
public class FXController extends Application {
    //@FXML
    private BarChart barchart;
	
    public static void main(String[] args) {
        Application.launch(FXController.class, args);
    }
	
	@FXML
    private void initialize() {
		
	}
	
	private BarChart getChart() {
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barchart = 
            new BarChart<String,Number>(xAxis,yAxis);
		barchart.getXAxis().setAutoRanging(true);
        barchart.getYAxis().setAutoRanging(true);
        
		return barchart;
    }
	
	private void loadData(File file) {
		if (file == null ||  !file.exists()) {
			return;
		}
		ReadCsv csv = new ReadCsv(";");
		List<String[]> data = csv.load(file);
		
		barchart.setData(getChartData(data));
        barchart.setTitle(file.getAbsolutePath());
	}
	
    @Override
    public void start(Stage stage) throws Exception {
		barchart = getChart();
		loadData(new File("data.csv"));
		
		stage.setTitle("Chart data.csv");
		Scene scene = new Scene(barchart, 300, 275);
		scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });
        
        // Dropping over surface
        scene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        loadData(file);
						break;
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
		
		
        stage.setScene(scene);				
        stage.show();
    }
	
	
	
	 private ObservableList<XYChart.Series<String, Number>> getChartData(List<String[]> data) {
        ObservableList<XYChart.Series<String, Number>> answer = FXCollections.observableArrayList();
		
        Series<String, Number> aSeries = new Series<String, Number>();
        aSeries.setName("Series 1");
        
		// sort
		Collections.sort(data, new Comparator<String[]>() {
			public int compare(String[] s1, String[] s2) {
				return Double.valueOf(s1[1]).compareTo(Double.valueOf(s2[1]));
			}
			}
		);
        for (String[] item: data) {
            aSeries.getData().add(new XYChart.Data(item[0], Double.valueOf(item[1])));
        }
        answer.addAll(aSeries);
        return answer;
    }
	
	
}