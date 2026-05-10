package dao;

import model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public void registrarUsuario(Usuario usuario) {
        //não é necessario utilizarmos o IDusuario que estava na outra classe pois ele gera sozinho
        String sql = "INSERT INTO usuario (nome, num_residentes, estado_idestado) VALUES (?,?,?)";


        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql)) {


            statement.setString(1, usuario.getNome());
            statement.setInt(2, usuario.getNumResidentes());
            statement.setString(3, usuario.getIdEstado());

            statement.executeUpdate();//aqui atualizamos nosso BD
            System.out.println("Usuario" + usuario.getNome() + "salvo!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar o usuário:" + e.getMessage());
        }
    }
}
