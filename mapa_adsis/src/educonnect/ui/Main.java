package educonnect.ui;

import educonnect.model.*;
import educonnect.repository.*;
import educonnect.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Camada de dados
        AlunoRepository alunoRepo = new AlunoRepository();
        ProfessorRepository profRepo = new ProfessorRepository();
        CursoRepository cursoRepo = new CursoRepository();
        TurmaRepository turmaRepo = new TurmaRepository();

        // Serviços
        AlunoService alunoService = new AlunoService(alunoRepo);
        ProfessorService professorService = new ProfessorService(profRepo);
        CursoService cursoService = new CursoService(cursoRepo);
        TurmaService turmaService = new TurmaService(turmaRepo);
        RelatorioService relatorioService = new RelatorioService();

        // Seed opcional (demonstração rápida)
        Professor p1 = professorService.cadastrar("Prof. Ana", "ana", "123", "Eng. Software", "B001");
        Curso c1 = cursoService.cadastrarPresencial("POO", "CPOO", 60, "Sala 001");
        Curso c2 = cursoService.cadastrarEAD("Algoritmos", "CALG", 80, "Moodle");
        Aluno a1 = alunoService.cadastrar("Luan", "luan", "123", "A001", c1);

        turmaService.criarTurma("T-POO-01", p1, c1);
        turmaService.adicionarAluno("T-POO-01", a1);

        // Menu
        Scanner sc = new Scanner(System.in);
        int op;
        do {
            System.out.println("\n=== EduConnect - SGE (Console) ===");
            System.out.println("1) Cadastrar Aluno");
            System.out.println("2) Cadastrar Professor");
            System.out.println("3) Cadastrar Curso (Presencial/EAD)");
            System.out.println("4) Criar Turma");
            System.out.println("5) Adicionar/Remover Aluno na Turma");
            System.out.println("6) Registrar Avaliação");
            System.out.println("7) Relatórios Gerais");
            System.out.println("0) Sair");
            System.out.print("Opção: ");
            op = lerInt(sc);

            try {
                switch (op) {
                    case 1 -> cadastrarAluno(sc, alunoService, cursoService);
                    case 2 -> cadastrarProfessor(sc, professorService);
                    case 3 -> cadastrarCurso(sc, cursoService);
                    case 4 -> criarTurma(sc, professorService, cursoService, turmaService);
                    case 5 -> gerenciarMatriculas(sc, alunoService, turmaService);
                    case 6 -> registrarAvaliacao(sc, turmaService);
                    case 7 -> gerarRelatorios(relatorioService, alunoService, professorService, cursoService, turmaService);
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Falha: " + e.getMessage());
            }
        } while (op != 0);

        sc.close();
    }

    private static void cadastrarAluno(Scanner sc, AlunoService alunoService, CursoService cursoService) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Código do curso (ou ENTER para nenhum): ");
        String codCurso = sc.nextLine();

        Curso curso = null;
        if (!codCurso.isBlank()) {
            curso = cursoService.buscarPorCodigo(codCurso).orElse(null);
        }
        Aluno a = alunoService.cadastrar(nome, login, senha, matricula, curso);
        System.out.println("Aluno cadastrado: " + a.gerarRelatorio());
    }

    private static void cadastrarProfessor(Scanner sc, ProfessorService professorService) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Especialidade: ");
        String esp = sc.nextLine();
        System.out.print("Registro: ");
        String reg = sc.nextLine();

        Professor p = professorService.cadastrar(nome, login, senha, esp, reg);
        System.out.println("Professor cadastrado: " + p.gerarRelatorio());
    }

    private static void cadastrarCurso(Scanner sc, CursoService cursoService) {
        System.out.print("Tipo [1=Presencial | 2=EAD]: ");
        int tipo = lerInt(sc);
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Código: ");
        String cod = sc.nextLine();
        System.out.print("Carga Horária: ");
        int ch = lerInt(sc);

        Curso c;
        if (tipo == 1) {
            System.out.print("Sala de Aula: ");
            String sala = sc.nextLine();
            c = cursoService.cadastrarPresencial(nome, cod, ch, sala);
        } else {
            System.out.print("Plataforma Virtual: ");
            String plat = sc.nextLine();
            c = cursoService.cadastrarEAD(nome, cod, ch, plat);
        }
        System.out.println("Curso cadastrado: " + c.detalharCurso());
    }

    private static void criarTurma(Scanner sc, ProfessorService professorService, CursoService cursoService, TurmaService turmaService) {
        System.out.print("Código da Turma: ");
        String codTurma = sc.nextLine();
        System.out.print("Registro do Professor: ");
        String reg = sc.nextLine();
        System.out.print("Código do Curso: ");
        String codCurso = sc.nextLine();

        Professor prof = professorService.buscarPorRegistro(reg).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));
        Curso curso = cursoService.buscarPorCodigo(codCurso).orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        Turma t = turmaService.criarTurma(codTurma, prof, curso);
        System.out.println("Turma criada: " + t.resumo());
    }

    private static void gerenciarMatriculas(Scanner sc, AlunoService alunoService, TurmaService turmaService) {
        System.out.print("Código da Turma: ");
        String codTurma = sc.nextLine();
        System.out.print("[1=Adicionar | 2=Remover]: ");
        int acao = lerInt(sc);
        System.out.print("Matrícula do Aluno: ");
        String mat = sc.nextLine();

        if (acao == 1) {
            Aluno a = alunoService.buscarPorMatricula(mat).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
            boolean ok = turmaService.adicionarAluno(codTurma, a);
            System.out.println(ok ? "Aluno adicionado." : "Aluno já estava na turma.");
        } else {
            boolean ok = turmaService.removerAluno(codTurma, mat);
            System.out.println(ok ? "Aluno removido." : "Aluno não estava na turma.");
        }
    }

    private static void registrarAvaliacao(Scanner sc, TurmaService turmaService) {
        System.out.print("Código da Turma: ");
        String codTurma = sc.nextLine();
        System.out.print("Matrícula do Aluno: ");
        String mat = sc.nextLine();
        System.out.print("Descrição da Avaliação: ");
        String desc = sc.nextLine();
        System.out.print("Nota (0..10): ");
        double nota = lerDouble(sc);

        turmaService.registrarAvaliacao(codTurma, mat, desc, nota);
        System.out.println("Avaliação registrada.");
    }

    private static void gerarRelatorios(RelatorioService rel,
                                        AlunoService alunoService,
                                        ProfessorService professorService,
                                        CursoService cursoService,
                                        TurmaService turmaService) {
        System.out.println(rel.relatorioAlunos(alunoService.listar()));
        System.out.println(rel.relatorioProfessores(professorService.listar()));
        System.out.println(rel.relatorioCursos(cursoService.listar()));
        System.out.println(rel.relatorioTurmasDetalhado(turmaService.listar()));
    }

    private static int lerInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine();
            try { return Integer.parseInt(s.trim()); } catch (Exception ignored) { System.out.print("Número inválido, tente novamente: "); }
        }
    }

    private static double lerDouble(Scanner sc) {
        while (true) {
            String s = sc.nextLine();
            try { return Double.parseDouble(s.trim()); } catch (Exception ignored) { System.out.print("Número inválido, tente novamente: "); }
        }
    }
}
