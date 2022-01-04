
public class Avventori 
{
	private String Nome;
	private String Cognome;
	private String N_CID;
	private String N_tel;
	
	public Avventori(String nome, String cognome, String n_CID, String n_tel) 
	{
		super();
		Nome = nome;
		Cognome = cognome;
		N_CID = n_CID;
		N_tel = n_tel;
	}
	
	public String toString()
	{
		return Nome+" "+Cognome+" "+N_CID;
	}
}
