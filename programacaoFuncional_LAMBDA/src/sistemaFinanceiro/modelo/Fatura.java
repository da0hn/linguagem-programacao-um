package sistemaFinanceiro.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fatura {
	
	private String emailDevedor;
	private Double valor;
	private LocalDate dataVencimento;
	
	public Fatura(String emailDevedor, Double valor, LocalDate dataVencimento) {
		this.emailDevedor = emailDevedor;
		this.valor = valor;
		this.dataVencimento = dataVencimento;
	}
	public String getEmailDevedor() {
		return emailDevedor;
	}
	public void setEmailDevedor(String emailDevedor) {
		this.emailDevedor = emailDevedor;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String resumo() {
		String dataVencimento = this.dataVencimento.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
		return String.format("Valor RS %s, vencimento: %s", this.valor, dataVencimento);
	}
	
	
}
