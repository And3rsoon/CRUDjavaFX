package Repository;
import java.util.List;
import Model.parcelas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class parcelasRepository {
	@SuppressWarnings("exports")
	public EntityManager getEM() {
	    return Persistence.createEntityManagerFactory("geral").createEntityManager();
	}

	// Método para gravar dados no banco
	public String gavar(parcelas fornecedor) {
	    EntityManager etm = this.getEM();
	    try {
	        etm.getTransaction().begin();
	        etm.persist(fornecedor);
	        etm.getTransaction().commit();
	        return "ok";
	    } catch (Exception e) {
	        // Fazer rollback da transação se ocorrer um erro
	        if (etm.getTransaction().isActive()) {
	            etm.getTransaction().rollback();
	        }
	        // Logar ou exibir a exceção para facilitar a depuração
	        e.printStackTrace();
	        return "erro";
	    } finally {
	        // Garantir que o EntityManager seja fechado mesmo em caso de exceção
	        if (etm != null && etm.isOpen()) {
	            etm.close();
	        }
	    }
	}
	
	public String update(parcelas fornecedor) {
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

	        parcelas funcionario = this.consultar(id);

	        if (entityManager.contains(funcionario)) {
	            entityManager.remove(funcionario);
	        } else {
	        	parcelas mesaGerenciado = entityManager.merge(funcionario);
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
	public List<parcelas> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<parcelas> query = entityManager.createQuery("SELECT p FROM parcelas p", parcelas.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	
	//metodo testado ok
	public parcelas consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			parcelas fornecedor=etm.find(parcelas.class, id);
			return fornecedor;
		}
		catch(Exception e) {
			return null;
		}		
		
		
	}
	
	public List<parcelas> buscarPorNumDoc(String numDoc) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM Fornecedor f WHERE f.numDoc LIKE :numDoc";
        TypedQuery<parcelas> query = etm.createQuery(jpql, parcelas.class);
        query.setParameter("numDoc", "%" + numDoc + "%");
        return query.getResultList();
    }
	
	public List<parcelas> buscarPorNome(String nome) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT f FROM Fornecedor f WHERE f.nome LIKE :nome";
        TypedQuery<parcelas> query = etm.createQuery(jpql, parcelas.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

}
