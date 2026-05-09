package model;

public class FaixaConsumo {

    private String estadoIdEstado;
    private double fixo;
    private int volInc;
    private double step1;
    private int faixa1;
    private double step2;
    private  int faixa2;
    private double step3;
    private int faixa3;
    private double step4;
    private int faixa4;
    private double step5;
    private int faixa5;
    private String emp;

    public FaixaConsumo(String estadoIdEstado, Double fixo, int volInc, Double step1, int faixa1, Double step2,
                        int faixa2, Double step3, int faixa3, Double step4,
                        int faixa4, Double step5, int faixa5, String emp) {
        this.estadoIdEstado = estadoIdEstado;
        this.fixo = fixo;
        this.volInc = volInc;
        this.step1 = step1;
        this.faixa1 = faixa1;
        this.step2 = step2;
        this.faixa2 = faixa2;
        this.step3 = step3;
        this.faixa3 = faixa3;
        this.step4 = step4;
        this.faixa4 = faixa4;
        this.step5 = step5;
        this.faixa5 = faixa5;
        this.emp = emp;
    }

    public String getEstadoIdEstado() {return estadoIdEstado;}
    public double getFixo() {return fixo;}
    public int getVolInc() {return volInc;}
    public double getStep1() {return step1;}
    public int getFaixa1() {return faixa1;}
    public double getStep2() {return step2;}
    public int getFaixa2() {return faixa2;}
    public double getStep3() {return step3;}
    public int getFaixa3() {return faixa3;}
    public double getStep4() {return step4;}
    public int getFaixa4() {return faixa4;}
    public double getStep5() {return step5;}
    public int getFaixa5() {return faixa5;}
    public String getEmp() {return emp;}

    public void setFixo(double fixo) {this.fixo = fixo;}
    public void setEmp(String emp) {this.emp = emp;}
}
