package Repository;
import java.util.List;
import Model.pagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class pagamentoRepository {
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(pagamento fornecedor) {
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
	
	
	public String update(pagamento fornecedor) {
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

	        pagamento funcionario = this.consultar(id);

	        if (entityManager.contains(funcionario)) {
	            entityManager.remove(funcionario);
	        } else {
	        	pagamento mesaGerenciado = entityManager.merge(funcionario);
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
	public List<pagamento> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<pagamento> query = entityManager.createQuery("SELECT p FROM pagamento p", pagamento.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	//metodo testado ok
	public pagamento consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			pagamento fornecedor=etm.find(pagamento.class, id);
			return fornecedor;
		}
		catch(Exception e) {
			return null;
		}		
		
		
	}
	
	public List<pagamento> buscarPorNumDoc(String numDoc) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM Fornecedor f WHERE f.numDoc LIKE :numDoc";
        TypedQuery<pagamento> query = etm.createQuery(jpql, pagamento.class);
        query.setParameter("numDoc", "%" + numDoc + "%");
        return query.getResultList();
    }
}
