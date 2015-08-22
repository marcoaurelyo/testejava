package dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface DaoGenerico<T, ID> {
	public abstract Class<T> getObjectClass();
	public abstract T salvar(T object) throws SQLException;
	public abstract T pesquisarPorId(ID id) throws SQLException;
	public abstract T atualizar(T object) throws SQLException;
	public abstract void excluir(T object) throws SQLException;
	public abstract List<T> todos() throws SQLException;
	public abstract List<T> listPesqParam(String query, Map<String, Object> params);
	public abstract List<T> listPesqParam(String query, Map<String, Object> params, 
			int maximo, int atual);
	public abstract List<T> listPesq(String query) throws SQLException;
	public abstract T pesqParam(String query, Map<String, Object> params);
	public abstract T pesquisarPorDescricao(String desc) throws SQLException;
	public abstract T PesqString(String query) throws SQLException;
	public abstract Long PesqCount(String sql) throws SQLException;
}
