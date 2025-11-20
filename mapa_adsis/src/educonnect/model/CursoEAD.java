package educonnect.model;

public class CursoEAD extends Curso {
    private String plataformaVirtual;

    public CursoEAD(String nome, String codigo, int cargaHoraria, String plataformaVirtual) {
        super(nome, codigo, cargaHoraria);
        this.plataformaVirtual = plataformaVirtual;
    }

    @Override
    public String detalharCurso() {
        return super.detalharCurso() + String.format(" [EAD / Plataforma: %s]", plataformaVirtual);
    }

    public String getPlataformaVirtual() { return plataformaVirtual; }
}
