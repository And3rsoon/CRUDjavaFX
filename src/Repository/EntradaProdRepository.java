package Repository;
import java.sql.Date;
import java.util.List;
import Model.EntradaProdutoModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class EntradaProdRepository {
	
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(EntradaProdutoModel fornecedor) {
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
	
	
	public String update(EntradaProdutoModel fornecedor) {
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

	        EntradaProdutoModel funcionario = this.consultar(id);

	        if (entityManager.contains(funcionario)) {
	            entityManager.remove(funcionario);
	        } else {
	        	EntradaProdutoModel mesaGerenciado = entityManager.merge(funcionario);
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
	public List<EntradaProdutoModel> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<EntradaProdutoModel> query = entityManager.createQuery("SELECT p FROM EntradaProdutoModel p", EntradaProdutoModel.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	
	public List<EntradaProdutoModel> buscarPorData(Date data) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM EntradaProdutoModel f WHERE f.data LIKE :numDoc";
        TypedQuery<EntradaProdutoModel> query = etm.createQuery(jpql, EntradaProdutoModel.class);
        query.setParameter("numDoc", "%" + data + "%");
        return query.getResultList();
    }
	

	
	//metodo testado ok
	public EntradaProdutoModel consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			EntradaProdutoModel fornecedor=etm.find(EntradaProdutoModel.class, id);
			return fornecedor;
		}
		catch(Exception e) {
			return null;
		}		
		
		
	}
	
	
	
}
