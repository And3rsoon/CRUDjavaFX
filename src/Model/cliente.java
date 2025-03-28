package Model;


import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Clienteid;

    @Column( nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipoDoc;
    
    @Column(unique = true, nullable = false)
    private String numDoc;
    
    @Column(nullable = false)
    private boolean credito;
    
    @Column(nullable = false)
    private double valorCredito;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<enderecoCliente> enderecos;

    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<saidaProdutoModel> saidaProd;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<telefonesClientes> telefones;

	public Long getClienteid() {
		return Clienteid;
	}

	public void setClienteid(Long clienteid) {
		Clienteid = clienteid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public List<enderecoCliente> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<enderecoCliente> enderecos) {
		this.enderecos = enderecos;
	}

	public List<saidaProdutoModel> getSaidaProd() {
		return saidaProd;
	}

	public void setSaidaProd(List<saidaProdutoModel> saidaProd) {
		this.saidaProd = saidaProd;
	}

	public List<telefonesClientes> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<telefonesClientes> telefones) {
		this.telefones = telefones;
	}

	public boolean isCredito() {
		return credito;
	}

	public void setCredito(boolean credito) {
		this.credito = credito;
	}

	public double getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(double valorCredito) {
		this.valorCredito = valorCredito;
	}

	
}
