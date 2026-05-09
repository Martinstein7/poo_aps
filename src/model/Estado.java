package model;

public class Estado {

    //Strings para "armazenar" os dados que iremos puxar do BD.
    private String idEstado;
    private String nomeEstado;
    private double cEsg;
    private double consumoMedio;

    //metodo construtor para poder criar objetos com o BD.
    public Estado(String idEstado,String nomeEstado, double cEsg, double consumoMedio) {
        this.idEstado = idEstado;
        this.nomeEstado = nomeEstado;
        this.cEsg = cEsg;
        this.consumoMedio = consumoMedio;
    }
         //nesse caso utilizamos o this para diferenciar o atributo de um paramentro

        //getters para que seja possivel outras classes lerem os dados
        public String getIdEstado() {return idEstado;}
        public String getNomeEstado()
            {return nomeEstado;}
        public double getcEsg()
            {return cEsg;}
       public double getConsumoMedio()
            {return consumoMedio;}

        //Setter para poder alterar os dados se necessario
        public void setNomeEstado(String nomeEstado)
        {this.nomeEstado = nomeEstado;}
        public void setcEsg(double cEsg)
        {this.cEsg = cEsg;}
        public void setConsumoMedio(double consumoMedio)
        {this.consumoMedio = consumoMedio;}
}



