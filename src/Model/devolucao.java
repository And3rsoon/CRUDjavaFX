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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;



@Entity
public class devolucao {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int devolucaoID;
	
	@Column(unique=true)
	private Date data;
	
	@Column(unique=false,nullable=false)
	private String motivo;
	
	@Column(unique=false,nullable=false)
	private String status;
	
	
	@OneToMany
	@JoinColumn(name = "produtos") 
	private List<produtoDevolucao> prodDevolucao;

	@ManyToOne
	@JoinColumn(name="devolucao")
	private Funcionarios funcionario;
	
	@OneToMany(mappedBy = "devolucao", cascade = CascadeType.ALL)
	private List<produto> produtos;

	public int getDevolucaoID() {
		return devolucaoID;
	}

	public void setDevolucaoID(int devolucaoID) {
		this.devolucaoID = devolucaoID;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<produtoDevolucao> getProdDevolucao() {
		return prodDevolucao;
	}

	public void setProdDevolucao(List<produtoDevolucao> prodDevolucao) {
		this.prodDevolucao = prodDevolucao;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public List<produto> getProduto() {
		return produtos;
	}

	public void setProduto(List<produto> produto) {
		produtos = produto;
	}

	
}