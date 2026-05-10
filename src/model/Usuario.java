package model;

public class Usuario {

    private int idUsuario;
    private String nome;
    private int numResidentes;
    private String idEstado;

    public Usuario(int idUsuario,String nome, int numResidentes, String idEstado) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.numResidentes = numResidentes;
        this.idEstado = idEstado;
    }

    public int getIdUsuario()
    {return idUsuario;}
    public String getNome()
    {return nome;}
    public int getNumResidentes()
    {return numResidentes;}
    public String getIdEstado()
    {return idEstado;}

    public void setNome(String nome)
    {this.nome = nome;}
    public void setNumResidentes(int numResidentes)
    {this.numResidentes = numResidentes;}

}
