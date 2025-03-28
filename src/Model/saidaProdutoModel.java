package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class saidaProdutoModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int saidaID;
	
	@Column(unique=true)
	private LocalDate data;
	
	@Column(unique=false,nullable=false)
	private String tipoOperacao;
	
	@Column(unique=false,nullable=false)
	private String status;
	
	
	@ManyToOne
	@JoinColumn(name="cliente")
	private cliente cliente;

	@ManyToOne
	@JoinColumn(name="saida")
	private Funcionarios funcionario;
	
	@OneToMany(mappedBy = "saidaprod")
	private List<produtoSaida> prodSaida;

	public int getSaidaID() {
		return saidaID;
	}

	public void setSaidaID(int saidaID) {
		this.saidaID = saidaID;
	}

	

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public cliente getCliente() {
		return cliente;
	}

	public void setCliente(cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public List<produtoSaida> getProdSaida() {
		return prodSaida;
	}

	public void setProdSaida(List<produtoSaida> prodSaida) {
		this.prodSaida = prodSaida;
	}
	
}