package Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class unidadeMedida {
	
	
	@Id
	@SequenceGenerator(name = "seq_produto", sequenceName = "produto_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private int idUnMedida;
	private String unMedida;
	private String abreviacao;
	private int quantidade;
	
	@OneToMany(mappedBy = "Unmedida", cascade = CascadeType.ALL)
	private List<produto> Produto;
	
	
	public int getIdUnMedida() {
		return idUnMedida;
	}
	public void setIdUnMedida(int idUnMedida) {
		this.idUnMedida = idUnMedida;
	}
	public String getUnMedida() {
		return unMedida;
	}
	public void setUnMedida(String unMedida) {
		this.unMedida = unMedida;
	}
	public String getAbreviacao() {
		return abreviacao;
	}
	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public List<produto> getProduto() {
		return Produto;
	}
	public void setProduto(List<produto> produto) {
		Produto = produto;
	}
	
	
	

}
