package Repository;
import java.util.List;
import jakarta.persistence.*;
import Model.FuncionarioSenhaDTO;
import Model.Fornecedor;


public class FornecedorRepository {
	
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(Fornecedor fornecedor) {
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
	
	
	public String update(Fornecedor fornecedor) {
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

	        Fornecedor funcionario = this.consultar(id);

	        if (entityManager.contains(funcionario)) {
	            entityManager.remove(funcionario);
	        } else {
	        	Fornecedor mesaGerenciado = entityManager.merge(funcionario);
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
	public List<Fornecedor> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<Fornecedor> query = entityManager.createQuery("SELECT p FROM Fornecedor p", Fornecedor.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	

	
	//metodo testado ok
	public Fornecedor consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			Fornecedor fornecedor=etm.find(Fornecedor.class, id);
			return fornecedor;
		}
		catch(Exception e) {
			return null;
		}		
		
		
	}
	
	public List<Fornecedor> buscarPorNumDoc(String numDoc) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM Fornecedor f WHERE f.numDoc LIKE :numDoc";
        TypedQuery<Fornecedor> query = etm.createQuery(jpql, Fornecedor.class);
        query.setParameter("numDoc", "%" + numDoc + "%");
        return query.getResultList();
    }
	
	public List<Fornecedor> buscarPorNome(String nome) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM Fornecedor f WHERE f.nome LIKE :nome";
        TypedQuery<Fornecedor> query = etm.createQuery(jpql, Fornecedor.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }
	
}
