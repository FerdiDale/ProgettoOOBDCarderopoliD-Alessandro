
public class Tavolo 
{
	private int Id_Tavolo;
	private int Capacita;
	private int Id_Sala;
	private int Numero;
	private int PosX;
	private int PosY;
	private int DimX;
	private int DimY;
	//metti tavoliAdiacenti
	public Tavolo(int id_Tavolo, int numero, int posX, int posY, int dimX, int dimY) {
		super();
		Id_Tavolo = id_Tavolo;
		Numero = numero;
		PosX = posX;
		PosY = posY;
		DimX = dimX;
		DimY = dimY;
	}
	public int getId_Tavolo() {
		return Id_Tavolo;
	}
	public void setId_Tavolo(int id_Tavolo) {
		Id_Tavolo = id_Tavolo;
	}
	public int getCapacita() {
		return Capacita;
	}
	public void setCapacita(int capacita) {
		Capacita = capacita;
	}
	public int getId_Sala() {
		return Id_Sala;
	}
	public void setId_Sala(int id_Sala) {
		Id_Sala = id_Sala;
	}
	public int getNumero() {
		return Numero;
	}
	public void setNumero(int numero) {
		Numero = numero;
	}
	public int getPosX() {
		return PosX;
	}
	public void setPosX(int posX) {
		PosX = posX;
	}
	public int getPosY() {
		return PosY;
	}
	public void setPosY(int posY) {
		PosY = posY;
	}
	public int getDimX() {
		return DimX;
	}
	public void setDimX(int dimX) {
		DimX = dimX;
	}
	public int getDimY() {
		return DimY;
	}
	public void setDimY(int dimY) {
		DimY = dimY;
	}
	public Tavolo()
	{
		
	}
	
}
