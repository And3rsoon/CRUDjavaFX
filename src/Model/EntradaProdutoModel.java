package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class EntradaProdutoModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id_entrada;
	private String tipoOperacao;
	private LocalDate data;
	private String nNotaFiscal;
	private String serieNotaFiscal;
	private String chaveAcesso;
	private String emissaoNota;

	@OneToMany(mappedBy = "entrada")
	private List<produtosEntrada> prodEntrada;
	
	@ManyToOne
	@JoinColumn(name = "fornecedorId") 
	private Fornecedor fornecedor;
	 
	@ManyToOne
	@JoinColumn(name = "entradas") 
	private remetenteNota remetentes;
	
	@ManyToOne
	@JoinColumn(name = "funcionerioId") 
	private Funcionarios funcionario;
	
	@OneToOne(mappedBy = "entrada") 
	private pagamento pagamento;

	public int getId_entrada() {
		return id_entrada;
	}

	public void setId_entrada(int id_entrada) {
		this.id_entrada = id_entrada;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}


	public String getChaveAcesso() {
		return chaveAcesso;
	}

	public void setChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}

	public List<produtosEntrada> getProdEntrada() {
		return prodEntrada;
	}

	public void setProdEntrada(List<produtosEntrada> prodEntrada) {
		this.prodEntrada = prodEntrada;
	}

	public Funcionarios getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}

	public pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getnNotaFiscal() {
		return nNotaFiscal;
	}

	public void setnNotaFiscal(String nNotaFiscal) {
		this.nNotaFiscal = nNotaFiscal;
	}

	public String getSerieNotaFiscal() {
		return serieNotaFiscal;
	}

	public void setSerieNotaFiscal(String serieNotaFiscal) {
		this.serieNotaFiscal = serieNotaFiscal;
	}

	public String getEmissaoNota() {
		return emissaoNota;
	}

	public void setEmissaoNota(String emissaoNota) {
		this.emissaoNota = emissaoNota;
	}

	public remetenteNota getRemetentes() {
		return remetentes;
	}

	public void setRemetentes(remetenteNota remetentes) {
		this.remetentes = remetentes;
	}
	
}
