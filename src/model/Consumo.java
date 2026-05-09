package model;

public class Consumo {

    private int idConsumo;
    private double m3Gastos;
    private String dataLeitura;
    private int usuarioIdUsuario;

    public Consumo(int idConsumo, double m3Gastos, String dataLeitura, int usuarioIdUsuario) {
        this.idConsumo = idConsumo;
        this.m3Gastos = m3Gastos;
        this.dataLeitura = dataLeitura;
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    public int getIdConsumo() {
        return idConsumo;
    }

    public double getM3Gastos() {
        return m3Gastos;
    }

    public String getDataLeitura() {
        return dataLeitura;
    }

    public int getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setM3Gastos(double m3Gastos) {
        this.m3Gastos = m3Gastos;
    }

    public void setDataLeitura(String dataLeitura) {
        this.dataLeitura = dataLeitura;
    }
}
