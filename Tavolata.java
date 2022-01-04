
public class Tavolata 
{
	private int id_tavolata;
	private int id_tavolo;
	private String data;
	public int getId_tavolo() {
		return id_tavolo;
	}
	public void setId_tavolo(int id_tavolo) {
		this.id_tavolo = id_tavolo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Tavolata(int id_tavolo, String data) {
		super();
		this.id_tavolo = id_tavolo;
		this.data = data;
	}
	
	
}
