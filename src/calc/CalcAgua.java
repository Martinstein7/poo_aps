package calc;

import model.FaixaConsumo;
import model.Estado;
import model.ReferenciaSustentavelOnu;
import dao.ReferenciaSustentavelOnuDAO;
import java.util.List;

public class CalcAgua {

    public double Calculo(double consumoMensal, FaixaConsumo faixas, Estado estado){
        double valorAgua = faixas.getFixo();
        double restante = consumoMensal - faixas.getVolInc();

        if (restante > 0) {
            double[] steps = {faixas.getStep1(), faixas.getStep2(), faixas.getStep3(), faixas.getStep4(), faixas.getStep5()};
            int[] limites = {faixas.getFaixa1(), faixas.getFaixa2(), faixas.getFaixa3(), faixas.getFaixa4(), faixas.getFaixa5()};

            for (int i = 0; i < steps.length; i++) {
                if (restante <= 0) break;

                double gastoF = Math.min(restante, limites[i]);
                valorAgua += gastoF * steps[i];
                restante -= gastoF;
            }
        }
        return valorAgua * (1 + estado.getCoef_Esg());
    }

    public double calcularPerCapita(double consumoM3, int residentes) {
        double consumoLitros = consumoM3 * 1000;
        return consumoLitros / (residentes * 30);
    }

    public String ConsumoONU(double perCapita) {
        ReferenciaSustentavelOnuDAO onuDAO = new ReferenciaSustentavelOnuDAO();
        List<ReferenciaSustentavelOnu> diretrizes = onuDAO.listarTodas();

        int metaSustentavel = 110;
        for (ReferenciaSustentavelOnu ref : diretrizes) {
            if (ref.getNivel_acesso().equalsIgnoreCase("sustentavel") || ref.getNivel_acesso().contains("ideal")) {
                metaSustentavel = ref.getLitro_pessoa();
                break;
            }
        }

        if (perCapita < (metaSustentavel * 0.3)) {
            return "Muito abaixo da média recomendada. Atenção: Caso sua residência sofra com a falta de água crônica ou ausência de rede encanada, considere formalizar um pedido de saneamento básico junto aos órgãos governamentais de sua região.";
        } else if (perCapita <= (metaSustentavel * 0.6)) {
            return "Abaixo da média recomendada. Certifique-se de que o consumo atende às necessidades básicas de higiene e saúde de todos os residentes.";
        } else if (perCapita <= metaSustentavel) {
            return "Ideal (Base ONU)";
        } else if (perCapita > (metaSustentavel * 1.5)) {
            return "Muito acima da média recomendada. Recomendações: Reduza o tempo no banho, feche a torneira ao escovar os dentes e reutilize a água da máquina de lavar.";
        } else {
            return "Acima da média recomendada";
        }
    }
}