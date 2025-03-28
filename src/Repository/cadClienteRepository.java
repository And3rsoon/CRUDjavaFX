package Repository;
import java.sql.Date;
import java.util.List;

import Model.EntradaProdutoModel;
import Model.cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class cadClienteRepository {
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(cliente categoria) {
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
	
	
	public String update(cliente categoria) {
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
	public String deletar(cliente categoria) {
	    EntityManager entityManager = this.getEM();
	    try {
	        entityManager.getTransaction().begin();
	        if (entityManager.contains(categoria)) {
	            entityManager.remove(categoria);
	        } else {
	        	cliente mesaGerenciado = entityManager.merge(categoria);
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
	public List<cliente> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<cliente> query = entityManager.createQuery("SELECT p FROM cliente p", cliente.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	
	public List<cliente> buscarPorId(Long id) {
		EntityManager etm=this.getEM();
		String jpql = "SELECT c FROM cliente c " +
	             "LEFT JOIN FETCH c.enderecos " +
	             "LEFT JOIN FETCH c.saidaProd " +
	             "LEFT JOIN FETCH c.telefones " +
	             "WHERE c.Clienteid = :id";
        TypedQuery<cliente> query = etm.createQuery(jpql, cliente.class);
        query.setParameter("id",id);
        return query.getResultList();
    }


	
	public List<cliente> buscarPorPartNome(String nome) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM cliente f WHERE f.nome LIKE :nome";
        TypedQuery<cliente> query = etm.createQuery(jpql, cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }
}
