package Model;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class pagamento {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_pagamento;
	@Column(nullable=false)
	private String forma;
	@Column(nullable=false)
	private double valorTotal;
	@Column(nullable=false)
	private int parcelas;
	@Column(nullable=false)
	private String status;

	
	@OneToMany(mappedBy = "pagamento", cascade = CascadeType.ALL)
	private List<parcelas> parcela;
	
	@OneToOne
	@JoinColumn(name="pagamento")
	private EntradaProdutoModel entrada;


	public int getId_pagamento() {
		return id_pagamento;
	}


	public void setId_pagamento(int id_pagamento) {
		this.id_pagamento = id_pagamento;
	}


	public String getForma() {
		return forma;
	}


	public void setForma(String forma) {
		this.forma = forma;
	}


	public double getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}


	public int getParcelas() {
		return parcelas;
	}


	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<parcelas> getParcela() {
		return parcela;
	}


	public void setParcela(List<parcelas> parcela) {
		this.parcela = parcela;
	}


	public EntradaProdutoModel getEntrada() {
		return entrada;
	}


	public void setEntrada(EntradaProdutoModel entrada) {
		this.entrada = entrada;
	}
	



}