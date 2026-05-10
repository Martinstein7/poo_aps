package view;

import dao.EstadoDAO;
import dao.FaixaConsumoDAO;
import model.Estado;
import model.FaixaConsumo;
import calc.CalcAgua;

public class EntradaSaida {
    private Painel painel = new Painel();

    //instanciando classes que trazem informação do bd e a calculadora
    private EstadoDAO estadoDAO = new EstadoDAO();
    private FaixaConsumoDAO faixaDAO = new FaixaConsumoDAO();
    private CalcAgua calc = new CalcAgua();

    public Double lerConsumo(String txt) {
        try{
            double valor = Double.parseDouble(txt.replace(",","."));
            if (valor < 0) {
                painel.msgE("Consumo não pode ser negativo!");
                return null; //Verificando se o usuario não irá informar um numero negativo
            }
            return valor;
        } catch (NumberFormatException e) {
            painel.msgE("Por favor, insira apenas numero neste campo!");
            return null; //Verificando se o usuario não irá informar letras em campos de numeros
        }
    }

    public double calcF(String sigla, double consumo) {
        try {

            Estado est = estadoDAO.buscarPorSigla(sigla);
            FaixaConsumo faixas = faixaDAO.buscarPorEstado(sigla);

            if (est != null && faixas != null) {//metodo que realizará o calculo dos dados informados
                return calc.Calculo(consumo, faixas, est);
            } else {
                painel.msgE("Erro: Dados do estado" + sigla + "não encontrados.");
                return 0.0;
            }
        } catch (Exception e) {
            painel.msgE("Erro tecnico ao acessar as informações: " + e.getMessage());
            return 0.0;
        }
    }

    private CalcAgua ca = new CalcAgua();

    public CalcAgua getCalc() {
        return ca;
    }

    public String fResult(double valor) {
        return String.format("R$ %.2f", valor);
    }
}
