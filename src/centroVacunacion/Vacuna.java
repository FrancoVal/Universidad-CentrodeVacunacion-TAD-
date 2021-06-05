package centroVacunacion;

abstract class Vacuna {
	String nombre;
	int cantidad, grados;
	boolean esParaMayores60;
	Integer duracionVacuna;
	
	@Override
	public String toString() {
		return "Vacuna = " + nombre.toString();
	}

	Vacuna(String nombre, int grados, boolean esParaMayores60,Integer duracionVacuna) {
		this.nombre = nombre;
		this.grados = grados;
		this.esParaMayores60=esParaMayores60;
		this.duracionVacuna=duracionVacuna;
	}
}

class AstraZeneca extends Vacuna {
	AstraZeneca() {
		super("AstraZeneca", 3,false,null);
	}

}

class Sputnik extends Vacuna {
	Sputnik() {
		super("Sputnik", 3,true,null);
	}

}

class Moderna extends Vacuna {
	Moderna() {
		super("Moderna", -18,false,60);
	}
}

class Sinopharm extends Vacuna {
	Sinopharm() {
		super("Sinopharm", 3,false,null);
	}
}

class Pfizer extends Vacuna {
	Pfizer() {
		super("Pfizer", -18,true,30);
	}
}