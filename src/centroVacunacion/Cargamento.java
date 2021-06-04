package centroVacunacion;

public class Cargamento {
	Vacuna vacuna;
	int cantidad;
	Fecha fechaIngreso;
	boolean estanVencidas;

	public Cargamento(Vacuna vacuna, int cantidad, Fecha fechaIngreso, boolean estanVencidas) {
		this.vacuna = vacuna;
		this.cantidad = cantidad;
		this.fechaIngreso = fechaIngreso;
		estanVencidas=false;
	}
	
	public void seVencio() {
		estanVencidas=true;
	}
}
