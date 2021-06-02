package centroVacunacion;

public class Persona {

	boolean personaTieneEnfermedad;
	boolean personaTrabajaSalud;
	Integer DNI;
	Fecha fecha;

	public Persona(Integer DNI, Fecha fecha, boolean personaTieneEnfermedad, boolean personaTrabajaSalud) {
		this.DNI = DNI;
		this.fecha = fecha;
		this.personaTieneEnfermedad = personaTieneEnfermedad;
		this.personaTrabajaSalud = personaTrabajaSalud;
		}
	
	@Override
	public String toString() {
		return "DNI = " + DNI.toString();
	}
}