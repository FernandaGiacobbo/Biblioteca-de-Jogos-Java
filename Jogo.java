public class Jogo {
    private String titulo;
    private String genero;
    private int ano;

    public Jogo(String titulo, String genero, int ano) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public int getAno() {
        return ano;
    }

    @Override
    public String toString() {
        return String.format("Título: %s | Gênero: %s | Ano: %d", titulo, genero, ano);
    }
}
