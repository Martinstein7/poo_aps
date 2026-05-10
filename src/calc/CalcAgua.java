package calc;

import model.FaixaConsumo;
import model.Estado;
import java.math.*;

public class CalcAgua {

    public double Calculo(double consumoMensal, FaixaConsumo faixas, Estado estado){
        double valorAgua = faixas.getFixo(); //Valor minimo da agua
        double restante = consumoMensal - faixas.getVolInc(); //Acima ed 10m3

        if (restante > 0) {
            double[] steps = {faixas.getStep1(), faixas.getStep2(), faixas.getStep3(), faixas.getStep4(), faixas.getStep5()};
            int[] limites = {faixas.getFaixa1(), faixas.getFaixa2(), faixas.getFaixa3(), faixas.getFaixa4(), faixas.getFaixa5()};

            for (int i = 0; i < steps.length; i++) {
                if (restante <= 0) break;

                double gastoF = Math.min (restante, limites[i]);
                valorAgua += gastoF * steps[i];
                restante -= gastoF;
            }
        }
        return valorAgua * (1 + estado.getcEsg());
    }
}
