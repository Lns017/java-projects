package educonnect.model;

public class CursoPresencial extends Curso {
    private String salaAula;

    public CursoPresencial(String nome, String codigo, int cargaHoraria, String salaAula) {
        super(nome, codigo, cargaHoraria);
        this.salaAula = salaAula;
    }

    @Override
    public String detalharCurso() {
        return super.detalharCurso() + String.format(" [Presencial / Sala: %s]", salaAula);
    }

    public String getSalaAula() { return salaAula; }
}
