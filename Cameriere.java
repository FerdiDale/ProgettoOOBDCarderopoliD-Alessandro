
public class Cameriere 
{

	private int Id_Cameriere;
	private String CID_Cameriere;
	private String Nome;
	private String Cognome;
	private int	Id_Ristorante;
	private String	Data_Ammissione;
	private String	Data_Licenziamento;
	
	public int getId_Cameriere() {
		return Id_Cameriere;
	}
	public void setId_Cameriere(int id_Cameriere) {
		Id_Cameriere = id_Cameriere;
	}
	public String getCID_Cameriere() {
		return CID_Cameriere;
	}
	public void setCID_Cameriere(String cID_Cameriere) {
		CID_Cameriere = cID_Cameriere;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getCognome() {
		return Cognome;
	}
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	public int getId_Ristorante() {
		return Id_Ristorante;
	}
	public void setId_Ristorante(int id_Ristorante) {
		Id_Ristorante = id_Ristorante;
	}
	public String getData_Ammissione() {
		return Data_Ammissione;
	}
	public void setData_Ammissione(String data_Ammissione) {
		Data_Ammissione = data_Ammissione;
	}
	public String getData_Licenziamento() {
		return Data_Licenziamento;
	}
	public void setData_Licenziamento(String data_Licenziamento) {
		Data_Licenziamento = data_Licenziamento;
	}
	public Cameriere(int id_Cameriere, String cID_Cameriere, String nome, String cognome, int id_Ristorante,
			String data_Ammissione, String data_Licenziamento) {
		super();
		Id_Cameriere = id_Cameriere;
		CID_Cameriere = cID_Cameriere;
		Nome = nome;
		Cognome = cognome;
		Id_Ristorante = id_Ristorante;
		Data_Ammissione = data_Ammissione;
		Data_Licenziamento = data_Licenziamento;
	}
	
	public String toString()
	{
		return Nome+" "+Cognome+" "+CID_Cameriere;
	}
}
