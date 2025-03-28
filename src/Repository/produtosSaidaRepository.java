package Repository;

import java.util.List;

import Model.enderecoCliente;
import Model.produtoSaida;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class produtosSaidaRepository {

	
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(produtoSaida fornecedor) {
		EntityManager etm=this.getEM();
		try{
			etm.getTransaction().begin();
			etm.persist(fornecedor);
			etm.getTransaction().commit();
			etm.close();
			return "ok";
		}
		catch(Exception e) {
			return "erro";
		}
	}
	
	
	public String update(produtoSaida fornecedor) {
	    EntityManager etm = this.getEM();
	    try {
	        etm.getTransaction().begin();
	        etm.merge(fornecedor); // Atualiza o objeto no banco de dados
	        etm.getTransaction().commit();
	        etm.close();
	        return "ok";
	    } catch (Exception e) {
	        if (etm.getTransaction().isActive()) {
	            etm.getTransaction().rollback(); // Faz rollback em caso de erro
	        }
	        etm.close();
	        return "erro: " + e.getMessage(); // Retorna a mensagem de erro para diagnóstico
	    }
	}
	
	//metodo testado ok
	public String deletar(Integer id) {
	    EntityManager entityManager = this.getEM();
	    try {
	        entityManager.getTransaction().begin();
	
	        produtoSaida funcionario = this.consultar(id);
	
	        if (entityManager.contains(funcionario)) {
	            entityManager.remove(funcionario);
	        } else {
	        	produtoSaida mesaGerenciado = entityManager.merge(funcionario);
	            entityManager.remove(mesaGerenciado);
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
	public List<produtoSaida> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<produtoSaida> query = entityManager.createQuery("SELECT p FROM produtoSaida p", produtoSaida.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	
	//metodo testado ok
	public produtoSaida consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			produtoSaida fornecedor=etm.find(produtoSaida.class, id);
			return fornecedor;
		}
		catch(Exception e) {
			return null;
		}		
		
		
	}
	
	
	
	public List<produtoSaida> buscarPorNumDoc(String numDoc) {
		EntityManager etm=this.getEM();
	    String jpql = "SELECT f FROM devolucao f WHERE f.numDoc LIKE :numDoc";
	    TypedQuery<produtoSaida> query = etm.createQuery(jpql, produtoSaida.class);
	    query.setParameter("numDoc", "%" + numDoc + "%");
	    return query.getResultList();
	}
	
	public List<produtoSaida> buscarPorNome(String nome) {
		EntityManager etm=this.getEM();
	    String jpql = "SELECT f FROM devolucao f WHERE f.nome LIKE :nome";
	    TypedQuery<produtoSaida> query = etm.createQuery(jpql, produtoSaida.class);
	    query.setParameter("nome", "%" + nome + "%");
	    return query.getResultList();
	}
}