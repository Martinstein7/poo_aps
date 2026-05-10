package view;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Interface extends JFrame {

    private String[] obterSiglasEstados() {//Metodo de siglas para puxar informações na pg 3
        return new String[]{"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO","MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    }
    private JLabel  lblPerCapita, lblStatusONU;

    private CardLayout cardLayout; //Nossa interface será em formato de cartão :)
    private JPanel painelPrincipal;
    private EntradaSaida es = new EntradaSaida(); // O Maestro

    //campo onde irá entrar as informações de dados do usuario
    private JTextField txtNome, txtResidentes, txtConsumo;
    private JComboBox<String> cbEstado;
    private JLabel lblResultadoFinal, lblIdGerado;

    public Interface() {
        // configurando a janela do cartão
        setUndecorated(true);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 450, 650, 35, 35));

        // Customização de titulo
        JPanel barraTitulo = new JPanel(new BorderLayout());
        barraTitulo.setBackground(new Color(100, 180, 255));
        barraTitulo.setPreferredSize(new Dimension(450, 40));

        JLabel lblTitulo = new JLabel(" Calculadora Consumo");
        lblTitulo.setForeground(new Color(20, 20, 40));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel botoesAcao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesAcao.setOpaque(false);
        JButton btnFechar = criarBotaoBarra("X", Color.RED);
        btnFechar.addActionListener(e -> System.exit(0));
        botoesAcao.add(btnFechar);

        barraTitulo.add(lblTitulo, BorderLayout.WEST);
        barraTitulo.add(botoesAcao, BorderLayout.EAST);

        // Background estilizado com um gradiente de cores
        cardLayout = new CardLayout();
        painelPrincipal = new PainelD(); // Classe customizada abaixo
        painelPrincipal.setLayout(cardLayout);
        painelPrincipal.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Telas que estão na interface
        montarTela1(); // Boas-vindas
        montarTela2(); // Cadastro de informações
        montarTela3(); // informaçoes para poder calcular
        montarTela4(); // Loading (como se o computador estivesse carregando por alguns segundo executando o codigo)
        montarTela5(); // Resultado

        // Layout Final
        setLayout(new BorderLayout());
        add(barraTitulo, BorderLayout.NORTH);
        add(painelPrincipal, BorderLayout.CENTER);
    }

    // telas

    private void montarTela1() {
        JPanel tela = criarContainer();
        tela.add(Box.createVerticalGlue());
        JLabel logo = new JLabel("<html><div style='text-align:center;'>Vamos calular seu gasto?</div></html>");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        //metodo para add a imagem da onu na interface
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/img/agua.jpg"));
            // Redimensionar se estiver muito grande
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(img));
            lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
            tela.add(lblLogo);
            tela.add(Box.createVerticalStrut(20));
        } catch (Exception e) {
            System.out.println("Logo não encontrada, pulando...");
        }

        JButton btn = criarBotaoEstilizado("COMEÇAR");
        btn.addActionListener(e -> cardLayout.show(painelPrincipal, "t2"));

        tela.add(Box.createVerticalGlue());
        tela.add(logo);
        tela.add(Box.createVerticalStrut(40));
        tela.add(btn);
        tela.add(Box.createVerticalGlue());
        painelPrincipal.add(tela, "t1");
    }

    private void montarTela2() {
        JPanel tela = criarContainer();
        txtNome = criarInput("Digite seu nome...");
        txtResidentes = criarInput("Número de residentes...");

        JButton btn = criarBotaoEstilizado("SALVAR E PROSSEGUIR");

        btn.addActionListener(e -> cardLayout.show(painelPrincipal, "t3"));

        tela.add(new JLabel("CADASTRO")).setForeground(new Color(100, 180, 255));
        tela.add(Box.createVerticalStrut(20));
        tela.add(txtNome);
        tela.add(Box.createVerticalStrut(15));
        tela.add(txtResidentes);
        tela.add(Box.createVerticalStrut(30));
        tela.add(btn);
        painelPrincipal.add(tela, "t2");
    }

    private void montarTela3() {

        JPanel tela = criarContainer();
        tela.add(Box.createVerticalGlue());
        JLabel lblTitulo = new JLabel("FORMULÁRIO DE CONSUMO");
        lblTitulo.setForeground(new Color(100, 180, 255));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);


        cbEstado = new JComboBox<>(obterSiglasEstados());
        cbEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        cbEstado.setMaximumSize(new Dimension(300, 40)); // AQUI TRAVA O TAMANHO
        cbEstado.setPreferredSize(new Dimension(300, 40));

        txtConsumo = criarInput("Consumo em m³...");
        txtConsumo.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtConsumo.setMaximumSize(new Dimension(300, 50));

        JButton btn = criarBotaoEstilizado("REALIZAR CÁLCULO");
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> dispararCalculo());
        tela.add(lblTitulo);
        tela.add(Box.createVerticalStrut(30));
        tela.add(new JLabel("Selecione o Estado:"));
        ((JLabel)tela.getComponent(tela.getComponentCount()-1)).setAlignmentX(Component.CENTER_ALIGNMENT);
        tela.add(cbEstado);
        tela.add(Box.createVerticalStrut(20));
        tela.add(new JLabel("Informe o Gasto:"));
        ((JLabel)tela.getComponent(tela.getComponentCount()-1)).setAlignmentX(Component.CENTER_ALIGNMENT);
        tela.add(txtConsumo);
        tela.add(Box.createVerticalStrut(40));
        tela.add(btn);
        tela.add(Box.createVerticalGlue());
        painelPrincipal.add(tela, "t3");
    }

    // tela de "computador pensando"
    private void montarTela4() {
        JPanel tela = criarContainer();
        tela.add(Box.createVerticalGlue());

        JLabel lblStatus = new JLabel("MÁQUINA OPERANDO...");
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        JProgressBar progress = new JProgressBar();
        progress.setIndeterminate(true);
        progress.setMaximumSize(new Dimension(300, 20));
        progress.setForeground(new Color(100, 180, 255));
        progress.setAlignmentX(Component.CENTER_ALIGNMENT);

        tela.add(lblStatus);
        tela.add(Box.createVerticalStrut(20));
        tela.add(progress);

        tela.add(Box.createVerticalGlue());
        painelPrincipal.add(tela, "t4");
    }

    private void montarTela5() {
        JPanel tela = criarContainer();
        tela.add(Box.createVerticalGlue());

        JLabel lblTitulo = new JLabel("RESULTADO ESTIMADO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblResultadoFinal = new JLabel("R$ 0,00");
        lblResultadoFinal.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblResultadoFinal.setForeground(new Color(100, 180, 255));
        lblResultadoFinal.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPerCapita = new JLabel("Consumo: -- L/pessoa");
        lblPerCapita.setForeground(Color.WHITE);
        lblPerCapita.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblStatusONU = new JLabel("ONU: --");
        lblStatusONU.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnReset = criarBotaoEstilizado("NOVO CÁLCULO");
        btnReset.addActionListener(e -> cardLayout.show(painelPrincipal, "t1"));

        tela.add(lblTitulo);
        tela.add(Box.createVerticalStrut(20));
        tela.add(lblResultadoFinal);
        tela.add(Box.createVerticalStrut(20));
        tela.add(lblPerCapita);
        tela.add(lblStatusONU);
        tela.add(Box.createVerticalStrut(40));
        tela.add(btnReset);

        tela.add(Box.createVerticalGlue());
        painelPrincipal.add(tela, "t5");
    }

    private void dispararCalculo() {
        Double consumo = es.lerConsumo(txtConsumo.getText());
        if (consumo != null) {
            cardLayout.show(painelPrincipal, "t4");

            int numRes = Integer.parseInt(txtResidentes.getText());
            String sigla = (String) cbEstado.getSelectedItem();

            Timer timer = new Timer(3000, e -> {
                double valorF = es.calcF(sigla, consumo);
                double perCapita = es.getCalc().calcularPerCapita(consumo, numRes);
                String status = es.getCalc().ConsumoONU(perCapita);

                lblResultadoFinal.setText(es.fResult(valorF));
                lblPerCapita.setText(String.format("Média: %.1f L/dia por pessoa", perCapita));
                lblStatusONU.setText("ONU: " + status);
                lblStatusONU.setForeground(perCapita <= 110 ? Color.GREEN : Color.YELLOW);

                cardLayout.show(painelPrincipal, "t5");
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    // componentes do codigo estilizados!
    private JPanel criarContainer() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }

    private JButton criarBotaoEstilizado(String texto) {
        JButton b = new JButton(texto);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(300, 45));
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JTextField criarInput(String placeholder) {
        JTextField t = new JTextField();
        t.setMaximumSize(new Dimension(300, 40));
        t.setBorder(BorderFactory.createTitledBorder(placeholder));
        return t;
    }

    private JButton criarBotaoBarra(String t, Color c) {
        JButton b = new JButton(t);
        b.setForeground(c);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        return b;
    }

    class PainelD extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            Color color1 = new Color(20, 20, 40); // Azul quase preto
            Color color2 = new Color(10, 10, 15); // Preto
            GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }
}