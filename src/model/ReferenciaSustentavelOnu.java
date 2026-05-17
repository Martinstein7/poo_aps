package model;

public class ReferenciaSustentavelOnu {

    private int idreferencia_sustentavel_onu;
    private String nivel_acesso;
    private int litro_pessoa;

    public ReferenciaSustentavelOnu(int idreferencia_sustentavel_onu, String nivel_acesso, int litro_pessoa) {
        this.idreferencia_sustentavel_onu = idreferencia_sustentavel_onu;
        this.nivel_acesso = nivel_acesso;
        this.litro_pessoa = litro_pessoa;
    }

    public int getIdreferencia_sustentavel_onu() {
        return idreferencia_sustentavel_onu;
    }

    public String getNivel_acesso() {
        return nivel_acesso;
    }

    public int getLitro_pessoa() {
        return litro_pessoa;
    }

    public void setIdreferencia_sustentavel_onu(int idreferencia_sustentavel_onu) {
        this.idreferencia_sustentavel_onu = idreferencia_sustentavel_onu;
    }

    public void setNivel_acesso(String nivel_acesso) {
        this.nivel_acesso = nivel_acesso;
    }

    public void setLitro_pessoa(int litro_pessoa) {
        this.litro_pessoa = litro_pessoa;
    }
}