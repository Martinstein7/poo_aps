package dao;

import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public Usuario buscarPorNome(String nome) {
        String sql = "SELECT * FROM usuario WHERE nome = ?";

        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, nome);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Usuario(
                        result.getInt("idusuario"),
                        result.getString("nome"),
                        result.getInt("num_residentes"),
                        result.getString("estado_idestado")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário por nome: " + e.getMessage());
        }
        return null;
    }

    public List<String> listarNomesUsuarios() {
        String sql = "SELECT nome FROM usuario";
        List<String> nomes = new ArrayList<>();

        try (Connection connect = Conexao.getConexao();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                nomes.add(result.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar nomes de usuários: " + e.getMessage());
        }
        return nomes;
    }

    public void excluirUsuario(int idUsuario) {
        String sqlDeletarConsumos = "DELETE FROM consumo WHERE usuario_idusuario = ?";
        String sqlDeletarUsuario = "DELETE FROM usuario WHERE idusuario = ?";

        try (Connection connect = Conexao.getConexao()) {
            connect.setAutoCommit(false);

            try (PreparedStatement stmtConsumos = connect.prepareStatement(sqlDeletarConsumos);
                 PreparedStatement stmtUsuario = connect.prepareStatement(sqlDeletarUsuario)) {

                stmtConsumos.setInt(1, idUsuario);
                stmtConsumos.executeUpdate();

                stmtUsuario.setInt(1, idUsuario);
                stmtUsuario.executeUpdate();

                connect.commit();
            } catch (SQLException e) {
                connect.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário e histórico: " + e.getMessage());
        }
    }
}