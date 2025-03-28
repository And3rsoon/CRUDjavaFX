package Model;


public class FuncionarioSenhaDTO {
    private String nome;
    private String cargo;
    private String senha;
    private String cpf;

    public FuncionarioSenhaDTO(String nome, String cargo, String senha,String cpf) {
        this.nome = nome;
        this.cargo = cargo;
        this.senha = senha;
        this.cpf=cpf;
    }

    // Getters e Setters
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}