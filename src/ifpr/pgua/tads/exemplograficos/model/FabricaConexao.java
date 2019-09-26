package ifpr.pgua.tads.exemplograficos.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FabricaConexao {

    private enum Sgbds{
        sqlite,mysql
    }


    private static int MAX_CONNECTIONS=5;

    private Sgbds SGBD=Sgbds.sqlite;
    private static String IP_SERVIDOR;
    private static String NOME_BANCO;
    private static String USUARIO;
    private static String SENHA;

    private static String CON_STRING;

    private static Connection[] pool;

    private static FabricaConexao instance = new FabricaConexao();

    //carrega os dados de configuração da conexão com o banco de dados
    //de um arquivo de propriedades. Isso é bastante útil para desvincular
    //do código da aplicação esses parâmetros que podem variar de um lugar
    //para outro.
    //Cada linha do arquivo contém uma tupla no seguinte formato
    // nome_da_propriedade = valor

    private void carregaProperties(){
        //abrimos o arquivo config.properties que deverá estar na raiz do projeto.
        //Essa localização facilita o uso com jar
        try(InputStream input =new FileInputStream("config.properties")){

            //o objeto da classe Properties facilita a leitura dos dados
            Properties properties = new Properties();
            if(input != null){

                //vinculamos com o arquivo aberto
                properties.load(input);

                //as propriedades são lidas com o método getProperty, e são lidas como strings.
                IP_SERVIDOR = properties.getProperty("db.ip_servidor");
                NOME_BANCO = properties.getProperty("db.nome");
                USUARIO = properties.getProperty("db.usuario");
                SENHA = properties.getProperty("db.senha");
                SGBD = Sgbds.valueOf(properties.getProperty("db.tipo"));
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }


     private FabricaConexao(){

        carregaProperties();

        if(SGBD == Sgbds.mysql){
            CON_STRING="jdbc:"+SGBD.name()+"://"+IP_SERVIDOR+"/"+NOME_BANCO;
        }else{
            CON_STRING="jdbc:"+SGBD.name()+":"+NOME_BANCO+".sqlite";
        }

        pool = new Connection[MAX_CONNECTIONS];
    }

    public static Connection getConnection() throws SQLException{

        for(int i=0;i<MAX_CONNECTIONS;i++){
            if(instance.pool[i]==null || instance.pool[i].isClosed()){
                instance.pool[i] = DriverManager.getConnection(CON_STRING,USUARIO,SENHA);
                return instance.pool[i];
            }
        }

        throw new SQLException("Máximo de conexões");
    }


}
