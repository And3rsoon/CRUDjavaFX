package Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class categoria {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int categoraId;
	
	@Column(unique=true)
	private String categoria;

	
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	private List<produto> Produto;
	
	
	public int getCategoraId() {
		return categoraId;
	}
	public void setCategoraId(int categoraId) {
		this.categoraId = categoraId;
	}
	public List<produto> getProduto() {
		return Produto;
	}
	public void setProduto(List<produto> produto) {
		Produto = produto;
	}
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
