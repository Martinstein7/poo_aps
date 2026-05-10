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
                        result.getDouble("step1"),
                        result.getInt("faixa1"),
                        result.getDouble("step2"),
                        result.getInt("faixa2"),
                        result.getDouble("step3"),
                        result.getInt("faixa3"),
                        result.getDouble("step4"),
                        result.getInt("faixa4"),
                        result.getDouble("step5"),
                        result.getInt("faixa5"),
                        result.getString("emp")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro na FaixaConsumoDAO: " + e.getMessage());
        }
        return null;
    }
}
