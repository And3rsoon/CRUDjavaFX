package Model;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Fornecedor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer fornecedor_id;
	@Column(nullable = false)
	private String tipoDoc;
	@Column(nullable = false,unique=true)
	private String numDoc;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cep;
	@Column(nullable = false)
	private String rua;
	@Column(nullable = false)
	private String bairro;
	@Column(nullable = false)
	private String cidade;
	@Column(nullable = false)
	private String estado;
	@Column(nullable = false)
	private String numEndereco;
	@Column(nullable = false)
	private String telefone;
	
	@OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL)
	private List<produto> Produto;
	
	@OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL)
	private List<EntradaProdutoModel> EntradaProdutoModel;
	
	
	public Integer getFornecedor_id() {
		return fornecedor_id;
	}
	public void setFornecedor_id(Integer fornecedor_id) {
		this.fornecedor_id = fornecedor_id;
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public String getNumDoc() {
		return numDoc;
	}
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNumEndereco() {
		return numEndereco;
	}
	public void setNumEndereco(String numEndereco) {
		this.numEndereco = numEndereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public List<produto> getProduto() {
		return Produto;
	}
	public void setProduto(List<produto> produto) {
		Produto = produto;
	}
}
