package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the tipoproduto database table.
 * 
 */
@Entity
@NamedQuery(name="TipoProduto.findAll", query="SELECT t FROM TipoProduto t")
public class TipoProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idTipoProduto;

	private String descricao;

	private String status;

	//bi-directional many-to-one association to Produto
	@OneToMany(mappedBy="tipoProduto")
	private List<Produto> produtos;

	//bi-directional many-to-one association to TipoProduto
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="\"idSubTipo\"")
	private TipoProduto subTipoProduto;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;

	public TipoProduto() {
	}

	public Integer getIdTipoProduto() {
		return this.idTipoProduto;
	}

	public void setIdTipoProduto(Integer idTipoProduto) {
		this.idTipoProduto = idTipoProduto;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto addProduto(Produto produto) {
		getProdutos().add(produto);
		produto.setTipoproduto(this);

		return produto;
	}

	public Produto removeProduto(Produto produto) {
		getProdutos().remove(produto);
		produto.setTipoproduto(null);

		return produto;
	}

	public TipoProduto getSubTipoProduto() {
		return subTipoProduto;
	}

	public void setSubTipoProduto(TipoProduto subTipoProduto) {
		this.subTipoProduto = subTipoProduto;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTipoProduto == null) ? 0 : idTipoProduto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoProduto other = (TipoProduto) obj;
		if (idTipoProduto == null) {
			if (other.idTipoProduto != null)
				return false;
		} else if (!idTipoProduto.equals(other.idTipoProduto))
			return false;
		return true;
	}
	

}