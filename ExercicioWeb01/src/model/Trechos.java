package model;

public class Trechos {
	
		private int idTrechos;
		private String origem;
		private String destino;
		private int nrPoltronas;
		private String saida;
		private String chegada;
		public String getSaida() {
			return saida;
		}
		public void setSaida(String saida) {
			this.saida = saida;
		}
		public String getChegada() {
			return chegada;
		}
		public void setChegada(String chegada) {
			this.chegada = chegada;
		}
		public int getIdTrechos() {
			return idTrechos;
		}
		public void setIdTrechos(int idTrechos) {
			this.idTrechos = idTrechos;
		}
		
		public int getNrPoltronas() {
			return nrPoltronas;
		}
		public void setNrPoltronas(int nrPoltronas) {
			this.nrPoltronas = nrPoltronas;
		}
		public String getOrigem() {
			return origem;
		}
		public void setOrigem(String origem) {
			this.origem = origem;
		}
		public String getDestino() {
			return destino;
		}
		public void setDestino(String destino) {
			this.destino = destino;
		}
		
		
}
