import java.util.Scanner;

public class Salao {
    private static int n, b, c;
    private static char[][] salao;
    private static int totalSolucoes = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Recebendo entrada do usuário
        System.out.print("Digite o tamanho do salão (n x n): ");
        n = sc.nextInt();

        System.out.print("Digite o número de Bigodudos (b): ");
        b = sc.nextInt();

        System.out.print("Digite o número de Capetas (c): ");
        c = sc.nextInt();

        // Inicializando o salão com '.' representando lajotas vazias
        salao = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                salao[i][j] = '.';
            }
        }

        // Iniciando o processo de backtracking
        colocarPistoleiros(0, 0, b, c);

        // Mostrando o total de soluções válidas
        System.out.println("Número de soluções possíveis: " + totalSolucoes);
    }

    // Função de backtracking para posicionar os pistoleiros
    private static void colocarPistoleiros(int linha, int coluna, int bigodudos, int capetas) {
        // Se não houver mais Bigodudos e Capetas a colocar, verifica a configuração
        if (bigodudos == 0 && capetas == 0) {
            if (verificaConfiguracao()) {
                totalSolucoes++;
            }
            return;
        }

        // Avançar para a próxima coluna
        if (coluna == n) {
            linha++;
            coluna = 0;
        }

        // Se já percorremos todas as linhas
        if (linha == n) {
            return;
        }

        // Tentando colocar um Bigodudo
        if (bigodudos > 0) {
            salao[linha][coluna] = 'B'; // 'B' representa Bigodudo
            colocarPistoleiros(linha, coluna + 1, bigodudos - 1, capetas);
            salao[linha][coluna] = '.'; // Backtracking (desfaz a escolha)
        }

        // Tentando colocar um Capeta
        if (capetas > 0) {
            salao[linha][coluna] = 'C'; // 'C' representa Capeta
            colocarPistoleiros(linha, coluna + 1, bigodudos, capetas - 1);
            salao[linha][coluna] = '.'; // Backtracking (desfaz a escolha)
        }

        // Tentando não colocar pistoleiros nesta posição
        colocarPistoleiros(linha, coluna + 1, bigodudos, capetas);
    }

    // Função para verificar se a configuração atual é válida
    private static boolean verificaConfiguracao() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (salao[i][j] == 'B' || salao[i][j] == 'C') {
                    if (!verificaStandoff(i, j, salao[i][j])) {
                        return false; // Se qualquer pistoleiro não satisfaz as condições, a configuração é inválida
                    }
                }
            }
        }
        return true;
    }

    // Verifica se o pistoleiro em (linha, coluna) vê pelo menos dois inimigos e nenhum aliado
    private static boolean verificaStandoff(int linha, int coluna, char pistoleiro) {
        int inimigosVisiveis = 0;

        // Verificar linha
        for (int j = 0; j < n; j++) {
            if (j != coluna && salao[linha][j] != '.') {
                if (salao[linha][j] != pistoleiro) {
                    inimigosVisiveis++;
                }
            }
        }

        // Verificar coluna
        for (int i = 0; i < n; i++) {
            if (i != linha && salao[i][coluna] != '.') {
                if (salao[i][coluna] != pistoleiro) {
                    inimigosVisiveis++;
                }
            }
        }

        // Verificar diagonais
        for (int i = -n; i < n; i++) {
            if (linha + i >= 0 && linha + i < n && coluna + i >= 0 && coluna + i < n) {
                if (i != 0 && salao[linha + i][coluna + i] != '.') {
                    if (salao[linha + i][coluna + i] != pistoleiro) {
                        inimigosVisiveis++;
                    }
                }
            }

            if (linha + i >= 0 && linha + i < n && coluna - i >= 0 && coluna - i < n) {
                if (i != 0 && salao[linha + i][coluna - i] != '.') {
                    if (salao[linha + i][coluna - i] != pistoleiro) {
                        inimigosVisiveis++;
                    }
                }
            }
        }

        // Retorna verdadeiro se vir pelo menos dois inimigos e nenhum aliado
        return inimigosVisiveis >= 2;
    }
}
