import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Livro {
    private String titulo;
    private String autor;
    private String numeroRegistro;
    private boolean disponivel;

    public Livro(String titulo, String autor, String numeroRegistro) {
        this.titulo = titulo;
        this.autor = autor;
        this.numeroRegistro = numeroRegistro;
        this.disponivel = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}

class Biblioteca {
    private List<Livro> livros;

    public Biblioteca() {
        livros = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livros.stream()
                    .filter(b -> b.getTitulo().equalsIgnoreCase(titulo) && b.isDisponivel())
                    .collect(Collectors.toList());
    }

    public List<Livro> buscarPorAutor(String autor) {
        return livros.stream()
                    .filter(b -> b.getAutor().equalsIgnoreCase(autor) && b.isDisponivel())
                    .collect(Collectors.toList());
    }

    public boolean emprestarLivro(String numeroRegistro) {
        for (Livro livro : livros) {
            if (livro.getNumeroRegistro().equals(numeroRegistro) && livro.isDisponivel()) {
                livro.setDisponivel(false);
                return true;
            }
        }
        return false;
    }

    public boolean devolverLivro(String numeroRegistro) {
        for (Livro livro : livros) {
            if (livro.getNumeroRegistro().equals(numeroRegistro) && !livro.isDisponivel()) {
                livro.setDisponivel(true);
                return true;
            }
        }
        return false;
    }
}

class VisualizacaoBiblioteca {
    public void imprimirDetalhesLivro(List<Livro> livros) {
        for (Livro livro : livros) {
            System.out.println("Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor() + ", Número de Registro: " + livro.getNumeroRegistro());
        }
    }

    public void imprimirMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}

class ControladorBiblioteca {
    private Biblioteca modelo;
    private VisualizacaoBiblioteca visualizacao;

    public ControladorBiblioteca(Biblioteca modelo, VisualizacaoBiblioteca visualizacao) {
        this.modelo = modelo;
        this.visualizacao = visualizacao;
    }

    public void adicionarLivro(String titulo, String autor, String numeroRegistro) {
        Livro novoLivro = new Livro(titulo, autor, numeroRegistro);
        modelo.adicionarLivro(novoLivro);
        visualizacao.imprimirMensagem("Livro adicionado com sucesso!");
    }

    public void buscarLivrosPorTitulo(String titulo) {
        List<Livro> livros = modelo.buscarPorTitulo(titulo);
        visualizacao.imprimirDetalhesLivro(livros);
    }

    public void buscarLivrosPorAutor(String autor) {
        List<Livro> livros = modelo.buscarPorAutor(autor);
        visualizacao.imprimirDetalhesLivro(livros);
    }

    public void emprestarLivro(String numeroRegistro) {
        boolean resultado = modelo.emprestarLivro(numeroRegistro);
        if(resultado) {
            visualizacao.imprimirMensagem("Livro emprestado com sucesso!");
        } else {
            visualizacao.imprimirMensagem("Livro não está disponível.");
        }
    }

    public void devolverLivro(String numeroRegistro) {
        boolean resultado = modelo.devolverLivro(numeroRegistro);
        if(resultado) {
            visualizacao.imprimirMensagem("Livro devolvido com sucesso!");
        } else {
            visualizacao.imprimirMensagem("Livro não estava emprestado.");
        }
    }
}

public class SistemaGerenciamentoBiblioteca {
    public static void main(String[] args) {
        Biblioteca modelo = new Biblioteca();
        VisualizacaoBiblioteca visualizacao = new VisualizacaoBiblioteca();
        ControladorBiblioteca controlador = new ControladorBiblioteca(modelo, visualizacao);

        controlador.adicionarLivro("1984", "George Orwell", "1234");
        controlador.adicionarLivro("O Hobbit", "J.R.R. Tolkien", "5678");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um título para pesquisa:");
        String titulo = scanner.nextLine();
        controlador.buscarLivrosPorTitulo(titulo);

        System.out.println("Digite um número de registro para emprestar:");
        String numeroRegistro = scanner.nextLine();
        controlador.emprestarLivro(numeroRegistro);

        System.out.println("Digite um número de registro para devolver:");
        numeroRegistro = scanner.nextLine();
        controlador.devolverLivro(numeroRegistro);

        scanner.close();
    }
}
