package Model;

import jakarta.persistence.*;


@Entity
public class senhas {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer senha_id;
	private String senha;
	@OneToOne
	@JoinColumn(name = "funcionario_id", referencedColumnName = "Funcionario_id")
	private Funcionarios funcionario;
	
	public Integer getSenha_id() {
		return senha_id;
	}
	public void setSenha_id(Integer senha_id) {
		this.senha_id = senha_id;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Funcionarios getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionarios funcionario) {
		this.funcionario = funcionario;
	}
	
	
}


