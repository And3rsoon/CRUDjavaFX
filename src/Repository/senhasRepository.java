package Repository;
import java.util.List;
import jakarta.persistence.*;
import Model.senhas;


public class senhasRepository {
	
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(senhas senha) {
		EntityManager etm=this.getEM();
		try{
			etm.getTransaction().begin();
			etm.persist(senha);
			etm.getTransaction().commit();
			etm.close();
			return "ok";
		}
		catch(Exception e) {
			return "erro";
		}
	}
	
	//metodo testado ok
	public String deletar(Integer id) {
	    EntityManager entityManager = this.getEM();
	    try {
	        entityManager.getTransaction().begin();

	        senhas senha = this.consultar(id);

	        if (entityManager.contains(senha)) {
	            entityManager.remove(senha);
	        } else {
	        	senhas mesaGerenciado = entityManager.merge(senha);
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
	public List<senhas> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<senhas> query = entityManager.createQuery("SELECT p FROM senhas p", senhas.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	

	
	//metodo testado ok
	public senhas consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			senhas produtos=etm.find(senhas.class, id);
			return produtos;
		}
		catch(Exception e) {
			return null;
		}		
		
		
	}
	
	public List<senhas> buscarPorParteDoNome(String parteDoNome) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT p FROM Mesa p WHERE p.nome LIKE :parteDoNome";
        TypedQuery<senhas> query = etm.createQuery(jpql, senhas.class);
        query.setParameter("parteDoNome", "%" + parteDoNome + "%");
        return query.getResultList();
    }

}