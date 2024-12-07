import tkinter as tk
from tkinter import ttk, messagebox

class CalculadoraFinanceira:
    def __init__(self):
        # Configuração da janela principal
        self.janela = tk.Tk()
        self.janela.title("Calculadora Financeira")
        self.janela.geometry("400x400")
        
        # Configuração de estilo
        self.configurar_estilo()
        
        # Criação dos componentes da interface
        self.criar_frame_entrada()
        self.criar_frame_resultado()
        self.criar_frame_botoes()
    
    def configurar_estilo(self):
        """Configura o estilo dos componentes"""
        estilo = ttk.Style()
        estilo.configure("TLabel", font=("Arial", 10))
        estilo.configure("TButton", font=("Arial", 10))
        estilo.configure("TLabelframe", font=("Arial", 10, "bold"))
    
    def criar_frame_entrada(self):
        """Cria o frame com os campos de entrada"""
        self.frame_entrada = ttk.LabelFrame(self.janela, text="Dados de Entrada", padding="10")
        self.frame_entrada.pack(padx=10, pady=5, fill="x")
        
        # Campos de entrada
        self.campos = {}
        for texto, row in [("Valor:", 0), ("Taxa de Juros (%):", 1), ("Períodos:", 2)]:
            ttk.Label(self.frame_entrada, text=texto).grid(row=row, column=0, padx=5, pady=5)
            entrada = ttk.Entry(self.frame_entrada)
            entrada.grid(row=row, column=1, padx=5, pady=5)
            self.campos[texto] = entrada
    
    def criar_frame_resultado(self):
        """Cria o frame de resultado"""
        self.frame_resultado = ttk.LabelFrame(self.janela, text="Resultado", padding="10")
        self.frame_resultado.pack(padx=10, pady=5, fill="x")
        self.label_resultado = ttk.Label(self.frame_resultado, text="")
        self.label_resultado.pack(padx=5, pady=5)
    
    def criar_frame_botoes(self):
        """Cria o frame com os botões de cálculo"""
        frame_botoes = ttk.Frame(self.janela)
        frame_botoes.pack(pady=10)
        
        ttk.Button(frame_botoes, text="Calcular Valor Futuro (VF)", 
                  command=lambda: self.calcular("VF")).pack(side="left", padx=5)
        ttk.Button(frame_botoes, text="Calcular Valor Presente (VP)", 
                  command=lambda: self.calcular("VP")).pack(side="left", padx=5)
    
    def calcular_valor_futuro(self, valor_presente, taxa_juros, periodos):
        """Calcula o Valor Futuro (VF)"""
        try:
            vp = float(valor_presente)
            i = float(taxa_juros) / 100
            n = float(periodos)
            return vp * (1 + i) ** n
        except ValueError:
            return None
    
    def calcular_valor_presente(self, valor_futuro, taxa_juros, periodos):
        """Calcula o Valor Presente (VP)"""
        try:
            vf = float(valor_futuro)
            i = float(taxa_juros) / 100
            n = float(periodos)
            return vf / ((1 + i) ** n)
        except ValueError:
            return None
    
    def calcular(self, tipo):
        """Realiza o cálculo e exibe o resultado"""
        valor = self.campos["Valor:"].get()
        juros = self.campos["Taxa de Juros (%):"].get()
        periodos = self.campos["Períodos:"].get()
        
        if tipo == "VF":
            resultado = self.calcular_valor_futuro(valor, juros, periodos)
            valor_texto = "Valor Presente"
        else:
            resultado = self.calcular_valor_presente(valor, juros, periodos)
            valor_texto = "Valor Futuro"
        
        if resultado is not None:
            self.label_resultado.config(
                text=f"{'Valor Futuro' if tipo == 'VF' else 'Valor Presente'}: "
                     f"R$ {resultado:.2f}\n\n"
                     f"Dados utilizados:\n"
                     f"{valor_texto}: R$ {float(valor):.2f}\n"
                     f"Taxa de Juros: {float(juros)}%\n"
                     f"Períodos: {periodos}"
            )
        else:
            messagebox.showerror("Erro", "Por favor, insira valores numéricos válidos.")
    
    def executar(self):
        """Inicia a execução da calculadora"""
        self.janela.mainloop()

if __name__ == "__main__":
    calculadora = CalculadoraFinanceira()
    calculadora.executar()