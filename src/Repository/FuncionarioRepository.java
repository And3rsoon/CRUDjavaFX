package Repository;
import java.util.List;
import jakarta.persistence.*;
import Model.FuncionarioSenhaDTO;
import Model.Funcionarios;


public class FuncionarioRepository {
	
	@SuppressWarnings("exports")
	public EntityManager getEM() {
		EntityManager em=Persistence.createEntityManagerFactory("geral").createEntityManager();
		return em;
	}
	
	//metodo testado ok
	public String gavar(Funcionarios funcionario) {
		EntityManager etm=this.getEM();
		try{
			etm.getTransaction().begin();
			etm.persist(funcionario);
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

	        Funcionarios funcionario = this.consultar(id);

	        if (entityManager.contains(funcionario)) {
	            entityManager.remove(funcionario);
	        } else {
	        	Funcionarios mesaGerenciado = entityManager.merge(funcionario);
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
	public List<Funcionarios> ListarTodos() {
	    EntityManager entityManager = this.getEM();
	    try {
	        List<Funcionarios> query = entityManager.createQuery("SELECT p FROM Funcionarios p", Funcionarios.class).getResultList();
	        return query;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        entityManager.close();
	    }
	}
	
	

	
	//metodo testado ok
	public Funcionarios consultar(Integer id) {
		EntityManager etm=this.getEM();
		try{
			Funcionarios produtos=etm.find(Funcionarios.class, id);
			return produtos;
		}
		catch(Exception e) {
			return null;
		}			
	}
	
	public List<Funcionarios> buscarPorParteDoNome(String parteDoNome) {
		EntityManager etm=this.getEM();
        String jpql = "SELECT p FROM Mesa p WHERE p.nome LIKE :parteDoNome";
        TypedQuery<Funcionarios> query = etm.createQuery(jpql, Funcionarios.class);
        query.setParameter("parteDoNome", "%" + parteDoNome + "%");
        return query.getResultList();
    }
	
	public Funcionarios buscarPorNumDoc(String numDoc) {
	    EntityManager etm = this.getEM();
	    String jpql = "SELECT p FROM Funcionarios p WHERE p.numDoc = :numDoc";
	    TypedQuery<Funcionarios> query = etm.createQuery(jpql, Funcionarios.class);
	    query.setParameter("numDoc", numDoc);
	    try {
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null; // Or handle it as needed
	    }
	}
		

}
