package dao;

import model.ReferenciaSustentavelOnu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReferenciaSustentavelOnuDAO {

    public List<ReferenciaSustentavelOnu> listarTodas() {
        String sql = "SELECT * FROM referencia_sustentavel_onu";
        List<ReferenciaSustentavelOnu> lista = new ArrayList<>();

        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                lista.add(new ReferenciaSustentavelOnu(
                        result.getInt("idreferencia_sustentavel_onu"),
                        result.getString("nivel_acesso"),
                        result.getInt("litro_pessoa")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar referencia da onu: " + e.getMessage());
        }
        return lista;
    }
}