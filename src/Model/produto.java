package Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Lob;

@Entity
public class produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_produto;
	private String nomeProduto;
	private String codBarra;
	private String urlImagem;
	private int quantMinima;
	private int quantMax;
	private String status;
	
	@OneToMany(mappedBy = "produto")
	private List<produtosEntrada> prodEntrada;
	
	@ManyToOne
	@JoinColumn(name = "Produto") 
	private Fornecedor fornecedor;
	
	@ManyToOne
	private devolucao devolucao;
	
	@ManyToOne
	@JoinColumn(name = "idUnMedida") 
	private unidadeMedida Unmedida;
	
	@ManyToOne
	@JoinColumn(name = "id_categora") 
	private categoria categoria;

	public int getId_produto() {
		return id_produto;
	}

	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCodBarra() {
		return codBarra;
	}

	public void setCodBarra(String codBarra) {
		this.codBarra = codBarra;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	

	public List<produtosEntrada> getProdEntrada() {
		return prodEntrada;
	}

	public void setProdEntrada(List<produtosEntrada> prodEntrada) {
		this.prodEntrada = prodEntrada;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public devolucao getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(devolucao devolucao) {
		this.devolucao = devolucao;
	}

	public unidadeMedida getUnmedida() {
		return Unmedida;
	}

	public void setUnmedida(unidadeMedida unmedida) {
		Unmedida = unmedida;
	}

	public categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(categoria categoria) {
		this.categoria = categoria;
	}

	public int getQuantMinima() {
		return quantMinima;
	}

	public void setQuantMinima(int quantMinima) {
		this.quantMinima = quantMinima;
	}

	public int getQuantMax() {
		return quantMax;
	}

	public void setQuantMax(int quantMax) {
		this.quantMax = quantMax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
