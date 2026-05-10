package dao;

import java.sql.*;
import model.FaixaConsumo;

public class FaixaConsumoDAO {

    public FaixaConsumo buscarPorEstado(String sigla) {
        String sql = "SELECT * FROM faixa_consumo WHERE estado_idestado = ?";

        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, sigla);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new FaixaConsumo(
                        result.getString("estado_idestado"),
                        result.getDouble("fixo"),
                        result.getInt("vol_inc"),
                        result.getDouble("step_1"),
                        result.getInt("faixa_1"),
                        result.getDouble("step_2"),
                        result.getInt("faixa_2"),
                        result.getDouble("step_3"),
                        result.getInt("faixa_3"),
                        result.getDouble("step_4"),
                        result.getInt("faixa_4"),
                        result.getDouble("step_5"),
                        result.getInt("faixa_5"),
                        result.getString("concessionaria")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro na FaixaConsumoDAO: " + e.getMessage());
        }
        return null;
    }
}
