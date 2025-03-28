package Repository;

import java.util.List;

import Model.produtoDevolucao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class produtoDevolucaoRepository {
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(produtoDevolucao fornecedor) {
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
	
	
	public String update(produtoDevolucao fornecedor) {
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

	        produtoDevolucao funcionario = this.consultar(id);

	        if (entityManager.contains(funcionario)) {
	            entityManager.remove(funcionario);
	        } else {
	        	produtoDevolucao mesaGerenciado = entityManager.merge(funcionario);
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
	public List<produtoDevolucao> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<produtoDevolucao> query = entityManager.createQuery("SELECT p FROM produtoDevolucao p", produtoDevolucao.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	
	//metodo testado ok
	public produtoDevolucao consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			produtoDevolucao fornecedor=etm.find(produtoDevolucao.class, id);
			return fornecedor;
		}
		catch(Exception e) {
			return null;
		}		
		
		
	}
	
	public List<produtoDevolucao> buscarPorNumDoc(String numDoc) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM Fornecedor f WHERE f.numDoc LIKE :numDoc";
        TypedQuery<produtoDevolucao> query = etm.createQuery(jpql, produtoDevolucao.class);
        query.setParameter("numDoc", "%" + numDoc + "%");
        return query.getResultList();
    }
	
	public List<produtoDevolucao> buscarPorNome(String nome) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM Fornecedor f WHERE f.nome LIKE :nome";
        TypedQuery<produtoDevolucao> query = etm.createQuery(jpql, produtoDevolucao.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

	
	
	
	
	
}
