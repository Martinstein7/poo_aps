package dao;

import model.Usuario;
import model.Consumo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsumoDAO {

    public void registrarConsumo(Consumo consumo) {

        String sql = "INSERT INTO consumo (m3_gastos, data_leitura, usuario_idusuario) VALUES (?,?,?)";


        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql)) {


            statement.setDouble(1, consumo.getM3Gastos());
            statement.setString(2, consumo.getDataLeitura());
            statement.setInt(3, consumo.getUsuarioIdUsuario());

            statement.executeUpdate();
            System.out.println("Consumo registrado!");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar consumo:" + e.getMessage());
        }
    }

    public List<Consumo> listarPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM consumo WHERE usuario_idusuario = ? ORDER BY data_leitura DESC";
        List<Consumo> lista = new ArrayList<>();

        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, idUsuario);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                lista.add(new Consumo(
                        result.getInt("idconsumo"),
                        result.getDouble("m3_gastos"),
                        result.getString("data_leitura"),
                        result.getInt("usuario_idusuario")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar consumos: " + e.getMessage());
        }
        return lista;
    }

    public void deletarConsumo(int idConsumo) {
        String sql = "DELETE FROM consumo WHERE idconsumo = ?";

        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, idConsumo);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar consumo: " + e.getMessage());
        }
    }

    public void atualizarConsumo(Consumo consumo) {
        String sql = "UPDATE consumo SET m3_gastos = ?, data_leitura = ? WHERE idconsumo = ?";

        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setDouble(1, consumo.getM3Gastos());
            statement.setString(2, consumo.getDataLeitura());
            statement.setInt(3, consumo.getIdConsumo());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar consumo: " + e.getMessage());
        }
    }
}