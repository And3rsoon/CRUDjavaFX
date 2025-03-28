package Model;

import jakarta.persistence.*;

@Entity
public class telefonesClientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "telefones", nullable = false)
    private cliente cliente;

    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String nomeResponsavel;
    @Column(nullable = false)
    private String cargoResponsavel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public cliente getCliente() {
		return cliente;
	}

	public void setCliente(cliente cliente) {
		this.cliente = cliente;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getCargoResponsavel() {
		return cargoResponsavel;
	}

	public void setCargoResponsavel(String cargoResponsavel) {
		this.cargoResponsavel = cargoResponsavel;
	}

    // Getters e Setters
}
