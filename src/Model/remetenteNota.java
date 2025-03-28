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
public class remetenteNota {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int remetenteId;
	
	@Column(unique=true)
	private String tipoDoc;
	private Long numero;
	private String nome;
	
	@OneToMany(mappedBy="remetentes")
	private List<EntradaProdutoModel> entrada;
	
	
	public int getRemetenteId() {
		return remetenteId;
	}
	public void setRemetenteId(int remetenteId) {
		this.remetenteId = remetenteId;
	}
	public String getTipoDoc() {
		return tipoDoc;
	}
	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
}
