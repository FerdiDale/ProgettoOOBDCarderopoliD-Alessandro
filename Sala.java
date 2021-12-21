


public class Sala 
{
	private int Id_Sala;
	private String Nome;
	private int Id_Ristorante;
	public int getId_Sala() {
		return Id_Sala;
	}
	public void setId_Sala(int id_Sala) {
		Id_Sala = id_Sala;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public int getId_Ristorante() {
		return Id_Ristorante;
	}
	public void setId_Ristorante(int id_Ristorante) {
		Id_Ristorante = id_Ristorante;
	}
	public String toString()
	{
		return Nome;
	}
}
