package Model;
import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity
public class produtosEntrada {
	
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private int produtosId;
		private double precoCompra;
		private double precoVenda;
		private int quantEntrada;
		private int quantEstoque; 
		private LocalDate dataVal;
		private String status;

		@ManyToOne
		@JoinColumn(name = "prodEntrada") 
		private produto produto;
		
		@ManyToOne
		@JoinColumn(name = "entrada_id") 
		private EntradaProdutoModel entrada;

		@OneToOne
		private produtoSaida proSaida;
		
		public int getProdutosId() {
			return produtosId;
		}

		public void setProdutosId(int produtosId) {
			this.produtosId = produtosId;
		}

		public double getPrecoCompra() {
			return precoCompra;
		}

		public void setPrecoCompra(double precoCompra) {
			this.precoCompra = precoCompra;
		}

		public double getPrecoVenda() {
			return precoVenda;
		}

		public void setPrecoVenda(double precoVenda) {
			this.precoVenda = precoVenda;
		}

		public int getQuantEntrada() {
			return quantEntrada;
		}

		public void setQuantEntrada(int quantEntrada) {
			this.quantEntrada = quantEntrada;
		}

		public int getQuantEstoque() {
			return quantEstoque;
		}

		public void setQuantEstoque(int quantEstoque) {
			this.quantEstoque = quantEstoque;
		}

		public LocalDate getDataVal() {
			return dataVal;
		}

		public void setDataVal(LocalDate dataVal) {
			this.dataVal = dataVal;
		}

		public produto getProduto() {
			return produto;
		}

		public void setProduto(produto produto) {
			this.produto = produto;
		}

		public EntradaProdutoModel getEntrada() {
			return entrada;
		}

		public void setEntrada(EntradaProdutoModel entrada) {
			this.entrada = entrada;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public produtoSaida getProSaida() {
			return proSaida;
		}

		public void setProSaida(produtoSaida proSaida) {
			this.proSaida = proSaida;
		}
}
