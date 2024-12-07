package calculosfinanceiros;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class CalculosFinanceiros extends JFrame {
    private JTextField campoValor;
    private JTextField campoJuros;
    private JTextField campoPeriodos;
    private JLabel labelResultado;
    private DecimalFormat formatoMoeda;

    public CalculosFinanceiros() {
        // Configuração da janela
        setTitle("Calculadora Financeira");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Configuração do formato de moeda
        formatoMoeda = new DecimalFormat("R$ #,##0.00");
        
        // Criação dos componentes da interface
        criarPainelEntrada();
        criarPainelResultado();
        criarPainelBotoes();
        
        // Layout principal
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }
    
    private void criarPainelEntrada() {
        JPanel painelEntrada = new JPanel();
        painelEntrada.setBorder(BorderFactory.createTitledBorder("Dados de Entrada"));
        painelEntrada.setLayout(new GridLayout(3, 2, 5, 5));
        
        // Campos de entrada
        painelEntrada.add(new JLabel("Valor:"));
        campoValor = new JTextField(10);
        painelEntrada.add(campoValor);
        
        painelEntrada.add(new JLabel("Taxa de Juros (%):"));
        campoJuros = new JTextField(10);
        painelEntrada.add(campoJuros);
        
        painelEntrada.add(new JLabel("Períodos:"));
        campoPeriodos = new JTextField(10);
        painelEntrada.add(campoPeriodos);
        
        add(painelEntrada);
    }
    
    private void criarPainelResultado() {
        JPanel painelResultado = new JPanel();
        painelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));
        labelResultado = new JLabel(" ");
        painelResultado.add(labelResultado);
        add(painelResultado);
    }
    
    private void criarPainelBotoes() {
        JPanel painelBotoes = new JPanel();
        JButton botaoVF = new JButton("Calcular Valor Futuro (VF)");
        JButton botaoVP = new JButton("Calcular Valor Presente (VP)");
        
        botaoVF.addActionListener(e -> calcular("VF"));
        botaoVP.addActionListener(e -> calcular("VP"));
        
        painelBotoes.add(botaoVF);
        painelBotoes.add(botaoVP);
        add(painelBotoes);
    }
    
    private Double calcularValorFuturo(double valorPresente, double taxaJuros, double periodos) {
        try {
            double i = taxaJuros / 100;
            return valorPresente * Math.pow(1 + i, periodos);
        } catch (Exception e) {
            return null;
        }
    }
    
    private Double calcularValorPresente(double valorFuturo, double taxaJuros, double periodos) {
        try {
            double i = taxaJuros / 100;
            return valorFuturo / Math.pow(1 + i, periodos);
        } catch (Exception e) {
            return null;
        }
    }
    
    private void calcular(String tipo) {
        try {
            double valor = Double.parseDouble(campoValor.getText().replace(",", "."));
            double juros = Double.parseDouble(campoJuros.getText().replace(",", "."));
            double periodos = Double.parseDouble(campoPeriodos.getText().replace(",", "."));
            
            Double resultado;
            String valorTexto;
            String tipoCalculo;
            
            if (tipo.equals("VF")) {
                resultado = calcularValorFuturo(valor, juros, periodos);
                valorTexto = "Valor Presente";
                tipoCalculo = "Valor Futuro";
            } else {
                resultado = calcularValorPresente(valor, juros, periodos);
                valorTexto = "Valor Futuro";
                tipoCalculo = "Valor Presente";
            }
            
            if (resultado != null) {
                String textoResultado = String.format("<html>%s: %s<br><br>" +
                        "Dados utilizados:<br>" +
                        "%s: %s<br>" +
                        "Taxa de Juros: %.2f%%<br>" +
                        "Períodos: %.0f</html>",
                        tipoCalculo, formatoMoeda.format(resultado),
                        valorTexto, formatoMoeda.format(valor),
                        juros, periodos);
                labelResultado.setText(textoResultado);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, insira valores numéricos válidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculosFinanceiros calculadora = new CalculosFinanceiros();
            calculadora.setVisible(true);
        });
    }
}