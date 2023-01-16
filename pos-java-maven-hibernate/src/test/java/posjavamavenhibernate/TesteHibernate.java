package posjavamavenhibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {

	@Test //salvar criar
	public void testeHibernateUtil() {
		
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa=new UsuarioPessoa();
		
		pessoa.setEmail("luangf222@gmail.com");
		pessoa.setIdade("22");
		pessoa.setLogin("luangf");
		pessoa.setNome("luan 2");
		pessoa.setSenha("123");
		pessoa.setSobrenome("gomes");
		
		daoGeneric.salvar(pessoa);
		
	}
	
	@Test
	public void testeBuscar() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa=new UsuarioPessoa();
		pessoa.setId(2L);
		
		pessoa=daoGeneric.pesquisar(pessoa);
		
		System.out.println(pessoa);
	}
	
	@Test
	public void testeBuscar2() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa=daoGeneric.pesquisar(2L, UsuarioPessoa.class);
		
		System.out.println(pessoa);
	}
	
	@Test
	public void testeUpdate () {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa=daoGeneric.pesquisar(2L, UsuarioPessoa.class);
		
		pessoa.setIdade("123");
		pessoa.setNome("nome atualizado");
		
		pessoa=daoGeneric.updateMerge(pessoa);
		
		System.out.println(pessoa);
	}
	
	@Test
	public void testeDelete() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa=daoGeneric.pesquisar(2L, UsuarioPessoa.class);
		
		daoGeneric.deletarPorId(pessoa);
	}
	
	@Test
	public void testeConsultarLista() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list=daoGeneric.listar(UsuarioPessoa.class);
	
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("---------------------------");
		}
	}
	
	@Test //Criando Queryes específicas
	public void testeQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list=daoGeneric.getEntityManager().createQuery("from UsuarioPessoa where nome='luan 2'").getResultList(); //HQL
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("---------------------------");
		}
	}
	
	//ex.: primeiras 4 pessoas ordenadas por nome
	@Test
	public void testeQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list=daoGeneric.getEntityManager()
				.createQuery("from UsuarioPessoa order by nome")
				.setMaxResults(4)
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@Test
	public void testeQueryListParameter() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list=daoGeneric.getEntityManager()
				.createQuery("from UsuarioPessoa where nome=:nome or sobrenome=:sobrenome")
				.setParameter("nome", "luan 2")
				.setParameter("sobrenome", "gomes")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
	
	/* Método para somar todas idades da tabela usuariopessoa, para funcionar trocar o String idade do model para int idade...
	@Test
	public void testeQuerySomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		Long somaIdade=(Long) daoGeneric.getEntityManager()
				.createQuery("select sum(u.idade) from UsuarioPessoa u").getSingleResult();
		System.out.println(somaIdade);
	}
	*/
	
	@Test
	public void testeNamedQuery1() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list=daoGeneric.getEntityManager()
				.createNamedQuery("UsuarioPessoa.todos")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@Test
	public void testeNamedQuery2() {
		DaoGeneric<UsuarioPessoa> daoGeneric=new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list=daoGeneric.getEntityManager()
				.createNamedQuery("UsuarioPessoa.buscarPorNome")
				.setParameter("nome", "luan 2")
				.getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
	}
	
	@Test
	public void testeGravaTelefone() {
		DaoGeneric daoGeneric=new DaoGeneric();
		
		UsuarioPessoa pessoa=(UsuarioPessoa) daoGeneric.pesquisar(3L, UsuarioPessoa.class);
		
		TelefoneUser telefoneUser=new TelefoneUser();
		
		telefoneUser.setNumero("654654363465433564");
		telefoneUser.setTipo("casa");
		telefoneUser.setUsuarioPessoa(pessoa);
		
		daoGeneric.salvar(telefoneUser);
	}
	
	@Test
	public void testeConsultaTelefones() {
		DaoGeneric daoGeneric=new DaoGeneric();
		
		UsuarioPessoa pessoa=(UsuarioPessoa) daoGeneric.pesquisar(3L, UsuarioPessoa.class);
		
		for (TelefoneUser fone : pessoa.getTelefoneUsers()) {
			System.out.println(fone.getNumero());
			System.out.println(fone.getTipo());
			System.out.println(fone.getUsuarioPessoa().getNome());
			System.out.println("----------------------------------");
		}
	}
	
}