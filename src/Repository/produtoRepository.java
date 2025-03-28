package Repository;
import java.util.List;
import Model.produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class produtoRepository {
	
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(produto produto) {
		EntityManager etm=this.getEM();
		try{
			etm.getTransaction().begin();
			etm.persist(produto);
			etm.getTransaction().commit();
			etm.close();
			return "ok";
		}
		catch(Exception e) {
			return "erro";
		}
	}
	
	
	public String update(produto produto) {
	    EntityManager etm = this.getEM();
	    try {
	        etm.getTransaction().begin();
	        etm.merge(produto); // Atualiza o objeto no banco de dados
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

	        produto produto = this.consultar(id);

	        if (entityManager.contains(produto)) {
	            entityManager.remove(produto);
	        } else {
	        	produto produto0 = entityManager.merge(produto);
	            entityManager.remove(produto0);
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
	public List<produto> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<produto> query = entityManager.createQuery("SELECT p FROM produto p", produto.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	

	
	//metodo testado ok
	public produto consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			produto fornecedor=etm.find(produto.class, id);
			return fornecedor;
		}
		catch(Exception e) {
			return null;
		}		
		
		
	}
	
	public List<produto> buscarPorNumDoc(String numDoc) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM produto f WHERE f.codBarra LIKE :numDoc";
        TypedQuery<produto> query = etm.createQuery(jpql, produto.class);
        query.setParameter("numDoc", "%" + numDoc + "%");
        return query.getResultList();
    }
	
	public List<produto> buscarPorNome(String nome) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM produto f WHERE f.nomeProduto LIKE :nome";
        TypedQuery<produto> query = etm.createQuery(jpql, produto.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }
	
}