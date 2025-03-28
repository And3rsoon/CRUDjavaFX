package Model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class parcelas {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_parcelas;
	@Column(nullable=false)
	private LocalDate data;
	@Column(nullable=false)
	private double valorParcela;
	@Column(nullable=false)
	private String status;

	
	@ManyToOne
	@JoinColumn(name = "parcela") 
	private pagamento pagamento;


	public int getId_parcelas() {
		return id_parcelas;
	}


	public void setId_parcelas(int id_parcelas) {
		this.id_parcelas = id_parcelas;
	}


	public LocalDate getData() {
		return data;
	}


	public void setData(LocalDate data) {
		this.data = data;
	}


	public double getValorParcela() {
		return valorParcela;
	}


	public void setValorParcela(double valorParcela) {
		this.valorParcela = valorParcela;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public pagamento getPagamento() {
		return pagamento;
	}


	public void setPagamento(pagamento pagamento) {
		this.pagamento = pagamento;
	}
	



}