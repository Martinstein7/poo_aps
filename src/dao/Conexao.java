package dao;
//importar funcionalidades de sql dentro do Java
import java.sql.*;

public class Conexao {
    //puxa o arquivo do banco de dados que se encontra dentro da pasta
    private static final String URL = "jdbc:sqlite:agua.db";

    public static Connection getConexao(){
        try {
            Class.forName("org.sqlite.JDBC"); //tenta carregar o driver de conexão de bd
            return DriverManager.getConnection(URL); //inicia a conexão
        } catch (ClassNotFoundException e){
            System.out.println("Driver do SQLite não encontrado: "+ e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }//os catchs foram feitos para caso de erro para realizar a conexão, seja ela por não encontrar ou não conectar
        return null;
    }

}
