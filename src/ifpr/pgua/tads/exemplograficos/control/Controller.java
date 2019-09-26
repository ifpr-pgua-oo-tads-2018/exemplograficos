package ifpr.pgua.tads.exemplograficos.control;

import ifpr.pgua.tads.exemplograficos.model.FakeDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.FormatStringConverter;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;

public class Controller {


    @FXML
    private LineChart<Double,Double> grafico;

    @FXML
    private NumberAxis eixoX;

    @FXML
    private NumberAxis eixoY;


    @FXML
    private PieChart pizza;


    public void initialize(){

        graficoSeno();

        graficoPizza();

    }

    public void graficoSeno(){
        FakeDAO fakeDAO = new FakeDAO();

        try{

            XYChart.Series seno = fakeDAO.getDadosSeno();

            grafico.getData().addAll(seno);


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void graficoPizza(){
        FakeDAO fakeDAO = new FakeDAO();

        try{

            ObservableList serie = fakeDAO.getDadosPopulacao();

            pizza.setData(serie);

        }catch (SQLException e){
            e.printStackTrace();
        }


    }


}
