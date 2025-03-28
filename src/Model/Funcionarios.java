package Model;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Funcionarios {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer Funcionario_id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cargo;
	@Column(nullable = false)
	private String tipoDoc;
	@Column(nullable = false,unique=true)
	private String numDoc;
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
	@Column(nullable = false)
	private LocalDate dataEntrada;
	@Column(nullable = true)
	private LocalDate dataSaida;
	@Column(nullable = false)
	private String status;
	
	 @OneToOne(mappedBy = "funcionario", cascade = CascadeType.ALL)
	 private senhas senhas;
	
	public Integer getFuncionario_id() {
		return Funcionario_id;
	}
	public void setFuncionario_id(Integer funcionario_id) {
		Funcionario_id = funcionario_id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
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
	public senhas getSenhas() {
		return senhas;
	}
	public void setSenhas(senhas senhas) {
		this.senhas = senhas;
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
	public LocalDate getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public LocalDate getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
