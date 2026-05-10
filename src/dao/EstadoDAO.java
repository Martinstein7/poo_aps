package dao;

import model.Estado;
import java.sql.*;

public class EstadoDAO {

    public Estado buscarPorSigla(String sigla) {
    String sql = "SELECT * FROM estado WHERE idestado = ?";

    //try garantindo que a conexão ira fechar sozinha após a consulta
    try (Connection connect = Conexao.getConexao();
        PreparedStatement statement = connect.prepareStatement(sql)) {

        statement.setString(1, sigla);
        ResultSet result = statement.executeQuery();

        if(result.next()){
            //Utilizando os metodos contrutores criados
            return new Estado(
                    result.getString("idestado"),
                    result.getString("nome"),
                    result.getDouble("coef_Esg"),
                    result.getDouble("consumo_medio")
            );
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar estado:" + e.getMessage());
    }
    return null;
    }
}


