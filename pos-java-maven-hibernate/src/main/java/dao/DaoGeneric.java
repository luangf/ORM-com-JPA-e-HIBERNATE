package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import posjavamavenhibernate.HibernateUtil;

//pq generic? nomeclatura; serve para qualquer classe/tabela, seja usuario, telefone, animal
//<T> generico, pode ser E de Entidade/Entity, mas vamos usar E agr, qlquer coisa -?...
public class DaoGeneric<E> {

	// conexão DB, instancia aqui
	private EntityManager entityManager = HibernateUtil.getEntityManager();

	public void salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
	}

	public E updateMerge(E entidade) { // salva ou atualiza
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeSalva = entityManager.merge(entidade);
		transaction.commit();

		return entidadeSalva;
	}

	public E pesquisar(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		E e = (E) entityManager.find(entidade.getClass(), id);

		return e;
	}

	public E pesquisar(Long id, Class<E> entidade) {
		E e = (E) entityManager.find(entidade, id);

		return e;
	}

	public void deletarPorId(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.createNativeQuery(
				"delete from " + entidade.getClass().getSimpleName().toLowerCase() +
				" where id=" + id).executeUpdate();  //faz delete
		
		transaction.commit();
	}

	//param: classe/tabela/entidade que queremos q consulte, ex.: users, telefones, predios...
	public List<E> listar(Class<E> entidade){
		EntityTransaction transaction=entityManager.getTransaction();
		transaction.begin();
		
		List<E> lista=entityManager.createQuery("from "+entidade.getName()).getResultList();
		
		transaction.commit();
		
		return lista;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
