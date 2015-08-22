package dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

public class DaoGenericoImpl<T, ID> implements DaoGenerico<T, ID> {
	
	protected EntityManager conexao;
	protected CriteriaBuilder criteria;
	private final Class<T> oClass;
	
	
	public DaoGenericoImpl() {
		this.oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
	}
	public EntityManager getConexao() {
		return conexao;
	}
	public CriteriaBuilder getCriteria() {
		return criteria;
	}
	@Override
	public Class<T> getObjectClass() {
		return this.oClass;
	}
	@Override
	public T salvar(T object) throws SQLException {
		getConexao().clear();
		getConexao().persist(object);
		return object;
	}
	@Override
	public T pesquisarPorId(ID id) throws SQLException {
		return (T) getConexao().find(oClass, id);
	}
	@Override
	public T atualizar(T object) throws SQLException {
		getConexao().merge(object);
		return object;
	}
	@Override
	public void excluir(T object) throws SQLException {
		object = getConexao().merge(object);
		getConexao().remove(object);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> todos() throws SQLException {
		String consulta = "Select obj FROM " + oClass.getSimpleName() + " obj";
		Query query = getConexao().createQuery(consulta);
		return query.getResultList();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listPesqParam(String query, Map<String, Object> params) {
		Query q = conexao.createQuery(query);
		for(String chave : params.keySet()){
			q.setParameter(chave, params.get(chave));
		}
		return q.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listPesqParam(String query, Map<String, Object> params,
			int maximo, int atual) {
		Query q = conexao.createQuery(query).setMaxResults(maximo).setFirstResult(atual);
		for(String chave : params.keySet()){
			q.setParameter(chave, params.get(chave));
		}
		return q.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> listPesq(String query) throws SQLException {
		Query consulta = getConexao().createQuery(query);
		return consulta.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public T pesqParam(String query, Map<String, Object> params) {
		Query q = conexao.createQuery(query);
		for(String chave : params.keySet()){
			q.setParameter(chave, params.get(chave));
		}
		return (T) q.getSingleResult();
	}
	@Override
	public T pesquisarPorDescricao(String desc) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T PesqString(String query) throws SQLException {
		Query consulta = getConexao().createQuery(query);
		return (T) consulta.getSingleResult();
	}
	
	public Long PesqCount(String query) throws SQLException {
		Query consulta = getConexao().createQuery(query);
		return (Long) consulta.getSingleResult();
	}
	
	/*public byte[] pesquisarArquivo(String query) throws SQLException {
		Query consulta = getConexao().createQuery(query);
		consulta.getSingleResult().getClass().;
	}*/
}