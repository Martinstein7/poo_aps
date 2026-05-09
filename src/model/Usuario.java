package model;

public class Usuario {

    private int idUsuario;
    private String nomeUsuario;
    private int numResidentes;
    private String idEstado;

    public Usuario(int idUsuario,String nomeUsuario, int numResidentes, String idEstado) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.numResidentes = numResidentes;
        this.idEstado = idEstado;
    }

    public int getIdUsuario()
    {return idUsuario;}
    public String getNomeUsuario()
    {return nomeUsuario;}
    public int getNumResidentes()
    {return numResidentes;}
    public String getIdEstado()
    {return idEstado;}

    public void setNomeUsuario(String nomeUsuario)
    {this.nomeUsuario = nomeUsuario;}
    public void setNumResidentes(int numResidentes)
    {this.numResidentes = numResidentes;}

}
