public class Ristorante {
	
	Integer Id_Ristorante;
	String Nome;
	String Via;
	Integer N_Civico;
	String Citta;
	
	public Integer getId_Ristorante() {
		return Id_Ristorante;
	}
	public void setId_Ristorante(Integer id_Ristorante) {
		Id_Ristorante = id_Ristorante;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getVia() {
		return Via;
	}
	public void setVia(String via) {
		Via = via;
	}
	public Integer getN_Civico() {
		return N_Civico;
	}
	public void setN_Civico(Integer n_Civico) {
		N_Civico = n_Civico;
	}
	public String getCitta() {
		return Citta;
	}
	public void setCitta(String citta) {
		Citta = citta;
	}

	public String toString(){
		
		String risultato = new String();
		risultato = Nome + ", " + Citta + ", " + Via + " " + N_Civico;
		
		return risultato;
		
	}
}