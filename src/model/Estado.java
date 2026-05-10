package model;

public class Estado {

    //Strings para "armazenar" os dados que iremos puxar do BD.
    private String idEstado;
    private String nome;
    private double coef_Esg;
    private double consumoMedio;

    //metodo construtor para poder criar objetos com o BD.
    public Estado(String idEstado,String nome, double coef_Esg, double consumoMedio) {
        this.idEstado = idEstado;
        this.nome = nome;
        this.coef_Esg = coef_Esg;
        this.consumoMedio = consumoMedio;
    }
         //nesse caso utilizamos o this para diferenciar o atributo de um paramentro

        //getters para que seja possivel outras classes lerem os dados
        public String getIdEstado() {return idEstado;}
        public String getNome()
            {return nome;}
        public double getCoef_Esg()
            {return coef_Esg;}
       public double getConsumoMedio()
            {return consumoMedio;}

        //Setter para poder alterar os dados se necessario
        public void setNomeEstado(String nomeEstado)
        {this.nome = nome;}
        public void setCoef_Esg(double coef_Esg)
        {this.coef_Esg = coef_Esg;}
        public void setConsumoMedio(double consumoMedio)
        {this.consumoMedio = consumoMedio;}
}



