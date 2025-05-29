import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Jugador> jugadores = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            jugadores.add(new Jugador("Jugador " + i));
        }

        try (ExecutorService executor = Executors.newFixedThreadPool(8)) {

            String[] rondas = {"OCTAVOS DE FINAL", "CUARTOS DE FINAL", "SEMIFINAL", "FINAL"};
            int partidosPorRonda = 8;

            for (String ronda : rondas) {
                System.out.println("----- " + ronda + " -----");
                List<Future<Partido.ResultadoPartido>> resultados = new ArrayList<>();
                for (int i = 0; i < partidosPorRonda; i++) {
                    Partido partido = new Partido(jugadores.get(i), jugadores.get(jugadores.size() - 1 - i));
                    resultados.add(executor.submit(partido));
                }
                List<Jugador> ganadores = new ArrayList<>();
                for (Future<Partido.ResultadoPartido> future : resultados) {
                    Partido.ResultadoPartido res = future.get();
                    System.out.println(res.jugador1.getNombre() + " vs " + res.jugador2.getNombre());
                    for (String set : res.resultadosSets) {
                        System.out.println(set);
                    }
                    System.out.println("Ganador del partido: " + res.ganador.getNombre() + "\n");
                    ganadores.add(res.ganador);
                }
                jugadores = new ArrayList<>(ganadores);
                partidosPorRonda /= 2;
            }
            System.out.println("¡Campeón del torneo: " + jugadores.getFirst().getNombre() + "!");
            executor.shutdown();
        }
    }
}