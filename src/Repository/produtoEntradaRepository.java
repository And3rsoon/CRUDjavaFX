package Repository;

import java.time.LocalDate;
import java.util.List;

import Model.EntradaProdutoModel;
import Model.FuncionarioSenhaDTO;
import Model.devolucao;
import Model.produtosEntrada;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class produtoEntradaRepository {


		@SuppressWarnings("exports")
		public EntityManager getEM() {
			EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
			return em;
		}
		
		//metodo testado ok
		public String gavar(produtosEntrada categoria) {
			EntityManager etm=this.getEM();
			try{
				etm.getTransaction().begin();
				etm.persist(categoria);
				etm.getTransaction().commit();
				etm.close();
				return "ok";
			}
			catch(Exception e) {
				return "erro";
			}
		}
		
		
		public String update(produtosEntrada categoria) {
		    EntityManager etm = this.getEM();
		    try {
		        etm.getTransaction().begin();
		        etm.merge(categoria);;// Atualiza o objeto no banco de dados
		        etm.getTransaction().commit();
		        return "ok";
		    } catch (Exception e) {
		        if (etm.getTransaction().isActive()) {
		            etm.getTransaction().rollback(); // Faz rollback em caso de erro
		        }
		        e.printStackTrace();
		        return "erro: " + e.getMessage(); // Retorna a mensagem de erro para diagnóstico
		    } finally {
		        if (etm.isOpen()) {
		            etm.close(); // Fecha o EntityManager no bloco finally
		        }
		    }
		}
		
		public String deletar(produtosEntrada categoria) {
		    EntityManager entityManager = this.getEM();
		    try {
		        entityManager.getTransaction().begin();
		        produtosEntrada managedEntity = entityManager.find(produtosEntrada.class, categoria.getProdutosId());
		        if (managedEntity != null) {
		            entityManager.remove(managedEntity);
		        }
		        entityManager.getTransaction().commit();
		        return "ok";
		    } catch (Exception e) {
		        if (entityManager.getTransaction().isActive()) {
		            entityManager.getTransaction().rollback();
		        }
		        e.printStackTrace();
		        return "erro";
		    } finally {
		        entityManager.close();
		    }
		}
		
		
		//metodo ok
		public List<produtosEntrada> ListarTodos(String status) {
		    EntityManager entityManager = this.getEM();
		    try {
		    	Query query = entityManager.createQuery("SELECT p FROM produtosEntrada p WHERE p.status = :status", produtosEntrada.class);
		    	query.setParameter("status", status);
		    	List<produtosEntrada> resultado = query.getResultList();
		    	return resultado;
		    } catch (Exception e) {
		        e.printStackTrace();
		        return null;
		    } finally {
		        entityManager.close();
		    }
		}
		
		

		
		public void subtrairQuantidadeEstoque(int produtosId, int quantidade) {
		    EntityManager etm = this.getEM();
		    
		    try {
		        // Inicia a transação
		        etm.getTransaction().begin();

		        // Localiza o produto pelo produtosId
		        String jpql = "SELECT p FROM produtosEntrada p WHERE p.produtosId = :produtosId";
		        TypedQuery<produtosEntrada> query = etm.createQuery(jpql, produtosEntrada.class);
		        query.setParameter("produtosId", produtosId);
		        produtosEntrada produto = query.getSingleResult();

		        if (produto != null) {
		            // Subtrai a quantidade do estoque e atualiza a entidade
		            int novaQuantidade = produto.getQuantEstoque() - quantidade;
		            produto.setQuantEstoque(novaQuantidade);

		            // Persiste a mudança no banco de dados
		            etm.merge(produto);
		        }

		        // Confirma a transação
		        etm.getTransaction().commit();
		        
		    } catch (Exception e) {
		        // Em caso de erro, faz rollback da transação
		        if (etm.getTransaction().isActive()) {
		            etm.getTransaction().rollback();
		        }
		        e.printStackTrace();
		    } finally {
		        // Fecha o EntityManager
		        etm.close();
		    }
		}
		
		
		
		
		//este método faz uma consulta unificando todos os lotes e retornando a quantidade total
		public List<Object[]> getUnificado(String status) {
		 	EntityManager etm=this.getEM();
		 	String jpql = "SELECT p.nomeProduto, " +
		              "AVG(e.precoCompra) AS avg_precoCompra, " +
		              "AVG(e.precoVenda) AS avg_precoVenda, " +
		              "SUM(e.quantEstoque) AS total_quantEstoque " +
		              "FROM produto p " +
		              "INNER JOIN produtosEntrada e " +
		              "ON p.id_produto = e.produto.id_produto and e.status =:status " +
		              "GROUP BY p.nomeProduto";
		Query query = etm.createQuery(jpql, Object[].class);
		query.setParameter("status", status);
		List<Object[]> resultado = query.getResultList();
		 	return resultado;
		 }
		
		
		public List<produtosEntrada> getNome(String nome, String status) {
		    EntityManager etm = this.getEM();
		    String jpql = "SELECT p " + 
		                  "FROM produtosEntrada p " +
		                  "JOIN p.produto s " +
		                  "ON s.nomeProduto LIKE :nome and p.status =:status ";
		    TypedQuery<produtosEntrada> query = etm.createQuery(jpql, produtosEntrada.class);
		    query.setParameter("nome", "%" + nome + "%");
		    query.setParameter("status", status);
		    return query.getResultList();
		}
		
		 public List<produtosEntrada> getcod(String nome,String status) {
			 	EntityManager etm=this.getEM();
			 	String jpql = "SELECT p " + 
			               "FROM produtosEntrada p JOIN p.produto s " +
			               "WHERE s.codBarra LIKE :nome and p.status =:status ";
			 	TypedQuery<produtosEntrada> query = etm.createQuery(jpql, produtosEntrada.class);
			 	query.setParameter("nome", "%"+nome+"%");
			 	query.setParameter("status", status);
			 	return query.getResultList();
			 }
		 
}