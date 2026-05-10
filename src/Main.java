import dao.EstadoDAO;
import dao.FaixaConsumoDAO;
import model.Estado;
import model.FaixaConsumo;
import calc.CalcAgua;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTE DO SISTEMA ---");

        // 1. Instanciar as classes necessárias
        EstadoDAO estadoDAO = new EstadoDAO();
        FaixaConsumoDAO faixaDAO = new FaixaConsumoDAO();
        CalcAgua calculadora = new CalcAgua();

        try {
            // 2. Tentar buscar dados do Banco (Simulando SP)
            String siglaTeste = "SP";
            Estado sp = estadoDAO.buscarPorSigla(siglaTeste);
            FaixaConsumo faixasSp = faixaDAO.buscarPorEstado(siglaTeste);

            if (sp != null && faixasSp != null) {
                System.out.println("✅ Conexão e Busca OK: Dados de " + sp.getNomeEstado() + " carregados.");

                // 3. Simular um consumo (ex: 15 metros cúbicos)
                double consumoTeste = 15.0;

                // 4. Rodar o cálculo
                double resultado = calculadora.Calculo(consumoTeste, faixasSp, sp);

                // 5. Mostrar o veredito
                System.out.println("----------------------------------");
                System.out.println("Consumo testado: " + consumoTeste + " m3");
                System.out.printf("Valor total calculado: R$ %.2f %n", resultado);
                System.out.println("----------------------------------");

            } else {
                System.out.println("❌ Erro: Não foi possível encontrar os dados no banco.");
                System.out.println("Verifique se rodou o script de carga de dados!");
            }

        } catch (Exception e) {
            System.out.println("❌ Ocorreu um erro durante o teste:");
            e.printStackTrace();
        }
    }
}