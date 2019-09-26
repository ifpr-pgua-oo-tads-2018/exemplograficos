package ifpr.pgua.tads.exemplograficos;

import ifpr.pgua.tads.exemplograficos.model.FabricaConexao;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main2 {

    private static void geraDadosSeno() throws SQLException{
        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con.prepareStatement("INSERT INTO seno(x,y) VALUES (?,?)");


        for(int i=0;i<360;i+=10){

            double x = i;
            double y = Math.sin(Math.toRadians(i));

            stm.setDouble(1,x);
            stm.setDouble(2,y);

            stm.executeUpdate();
        }

        stm.close();
        con.close();

    }


    private static void carregaDadosPopulacao() throws SQLException{
        Connection con = FabricaConexao.getConnection();

        PreparedStatement stm = con.prepareStatement("INSERT INTO populacao(estado,populacao) VALUES (?,?)");

        try{

            BufferedReader br = new BufferedReader(new FileReader(new File("estados.txt")));

            String linha;



            while((linha=br.readLine())!=null ){
                String[] tks = linha.split(";");

                String estado = tks[1];
                int populacao = Integer.valueOf(tks[2].replace(" ",""));

                stm.setString(1,estado);
                stm.setInt(2,populacao);

                stm.executeUpdate();
            }

            br.close();

        }catch (IOException e){
            e.printStackTrace();
        }

        stm.close();
        con.close();
    }


    public static void main(String[] args) throws SQLException {



        //geraDadosSeno();
        //carregaDadosPopulacao();



    }


}
