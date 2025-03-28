package Model;
import java.sql.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
public class produtoSaida {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int prodSaidaID;
	
	@Column(unique=true)
	private int quantidade;
	
	
	@OneToOne(mappedBy = "proSaida")
	private produtosEntrada prodEntrada;

	@ManyToOne
	@JoinColumn(name="devolucao")
	private Funcionarios funcionario;
	
	@ManyToOne
	@JoinColumn(name="prodSaida")
	private saidaProdutoModel saidaprod;
	

	public saidaProdutoModel getSaidaprod() {
		return saidaprod;
	}

	public void setSaidaprod(saidaProdutoModel saidaprod) {
		this.saidaprod = saidaprod;
	}

	public int getProdSaidaID() {
		return prodSaidaID;
	}

	public void setProdSaidaID(int prodSaidaID) {
		this.prodSaidaID = prodSaidaID;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public produtosEntrada getProdEntrada() {
		return prodEntrada;
	}

	public void setProdEntrada(produtosEntrada prodEntrada) {
		this.prodEntrada = prodEntrada;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}
	
}

