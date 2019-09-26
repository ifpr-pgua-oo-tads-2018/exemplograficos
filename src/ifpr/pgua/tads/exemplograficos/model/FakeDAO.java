package ifpr.pgua.tads.exemplograficos.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FakeDAO {


    private static String LISTA_SENO = "SELECT * FROM seno";
    private static String LISTA_POPULACAO = "SELECT * FROM populacao";


    public XYChart.Series getDadosSeno() throws SQLException {

        XYChart.Series<Double,Double> seno = new XYChart.Series();

        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con.prepareStatement(LISTA_SENO);

        ResultSet rs = stm.executeQuery();

        while(rs.next()){
            double x = rs.getDouble("x");
            double y = rs.getDouble("y");

            seno.getData().add(new XYChart.Data<>(x,y));
        }

        return seno;

    }


    public ObservableList<PieChart.Data> getDadosPopulacao() throws SQLException{

        ObservableList<PieChart.Data> serie = FXCollections.observableArrayList();

        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con.prepareStatement(LISTA_POPULACAO);

        ResultSet rs = stm.executeQuery();

        while(rs.next()){

            String estado = rs.getString("estado");
            int populacao = rs.getInt("populacao");

            serie.add(new PieChart.Data(estado,populacao));

        }
        rs.close();
        stm.close();
        con.close();

        return serie;




    }



}
