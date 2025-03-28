package Repository;
import java.util.List;

import Model.cliente;
import Model.enderecoCliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class cadEndClienteRepository {
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(enderecoCliente categoria) {
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
	
	
	public String update(enderecoCliente categoria) {
	    EntityManager etm = this.getEM();
	    try {
	        etm.getTransaction().begin();
	        etm.merge(categoria); // Atualiza o objeto no banco de dados
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
	public String deletar(enderecoCliente categoria) {
	    EntityManager entityManager = this.getEM();
	    try {
	        entityManager.getTransaction().begin();
	        if (entityManager.contains(categoria)) {
	            entityManager.remove(categoria);
	        } else {
	        	enderecoCliente mesaGerenciado = entityManager.merge(categoria);
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
	public List<enderecoCliente> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<enderecoCliente> query = entityManager.createQuery("SELECT p FROM enderecoCliente p", enderecoCliente.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	public List<enderecoCliente> buscarPorId(Long nome) {
		EntityManager etm=this.getEM();
		String jpql = "SELECT e FROM enderecoCliente e JOIN e.cliente c WHERE c.Clienteid = :nome";
        TypedQuery<enderecoCliente> query = etm.createQuery(jpql, enderecoCliente.class);
        query.setParameter("nome",nome);
        return query.getResultList();
    }
	
	
		
	
}
