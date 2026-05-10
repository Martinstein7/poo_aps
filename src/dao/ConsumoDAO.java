package dao;

import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Consumo;
import java.sql.*;

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
}
