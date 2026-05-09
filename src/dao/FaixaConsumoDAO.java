package dao;

import java.sql.*;
import model.FaixaConsumo;

public class FaixaConsumoDAO {

    public FaixaConsumo buscarPorEstado(String sigla) {
        String sql = "SELECT * FROM faixa_consumo WHERE estado_idestado = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sigla);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new FaixaConsumo(
                        rs.getString("estado_idestado"),
                        rs.getDouble("fixo"),
                        rs.getInt("vol_inc"),
                        rs.getDouble("step1"),
                        rs.getInt("faixa1"),
                        rs.getDouble("step2"),
                        rs.getInt("faixa2"),
                        rs.getDouble("step3"),
                        rs.getInt("faixa3"),
                        rs.getDouble("step4"),
                        rs.getInt("faixa4"),
                        rs.getDouble("step5"),
                        rs.getInt("faixa5"),
                        rs.getString("emp")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro na FaixaConsumoDAO: " + e.getMessage());
        }
        return null;
    }
}
