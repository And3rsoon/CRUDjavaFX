package Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class produtoDevolucao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int prodDevolucaoID;
	
	
	@Column(unique=false,nullable=false)
	private String motivo;
	
	@Column(unique=false,nullable=false)
	private int quantidade;
	
	@Column(unique=false,nullable=false)
	private boolean retornoEstoque;
	
	
	@ManyToOne
	@JoinColumn(name="prodDevolucao")
	private devolucao devolucao;


	public int getProdDevolucaoID() {
		return prodDevolucaoID;
	}


	public void setProdDevolucaoID(int prodDevolucaoID) {
		this.prodDevolucaoID = prodDevolucaoID;
	}


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	public int getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}


	public boolean isRetornoEstoque() {
		return retornoEstoque;
	}


	public void setRetornoEstoque(boolean retornoEstoque) {
		this.retornoEstoque = retornoEstoque;
	}


	public devolucao getDevolucao() {
		return devolucao;
	}


	public void setDevolucao(devolucao devolucao) {
		this.devolucao = devolucao;
	}
	
	
	
	
	
	

}
