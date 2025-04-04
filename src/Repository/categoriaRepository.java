package Repository;

import java.util.List;

import Model.Fornecedor;
import Model.categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class categoriaRepository {
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(categoria categoria) {
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
	
	
	public String update(categoria categoria) {
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
	public String deletar(categoria categoria) {
	    EntityManager entityManager = this.getEM();
	    try {
	        entityManager.getTransaction().begin();
	        if (entityManager.contains(categoria)) {
	            entityManager.remove(categoria);
	        } else {
	        	categoria mesaGerenciado = entityManager.merge(categoria);
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
	public List<categoria> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<categoria> query = entityManager.createQuery("SELECT p FROM categoria p", categoria.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
}
