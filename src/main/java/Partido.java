import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class Partido implements Callable<Partido.ResultadoPartido> {
    private final Jugador jugador1;
    private final Jugador jugador2;
    private final Random random = new Random();

    public Partido(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    @Override
    public ResultadoPartido call() {
        int setsJ1 = 0, setsJ2 = 0, set = 1;
        List<String> resultadosSets = new ArrayList<>();
        while (setsJ1 < 2 && setsJ2 < 2) {
            Jugador ganadorSet;
            if (random.nextBoolean()) {
                ganadorSet = jugador1;
            } else {
                ganadorSet = jugador2;
            }
            resultadosSets.add("Set " + set + ": " + ganadorSet.getNombre());
            if (ganadorSet == jugador1) setsJ1++;
            else setsJ2++;
            set++;
        }
        //Simula la duracion entre 1.5 y 2 segundos por partido
        try {
            Thread.sleep(1500 + random.nextInt(500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Determinar el ganador del partido
        Jugador ganador;
        if (setsJ1 == 2) {
            ganador = jugador1;
        } else {
            ganador = jugador2;
        }
        return new ResultadoPartido(jugador1, jugador2, resultadosSets, ganador);
    }

    public static class ResultadoPartido {
        public final Jugador jugador1, jugador2, ganador;
        public final List<String> resultadosSets;

        public ResultadoPartido(Jugador jugador1, Jugador jugador2, List<String> resultadosSets, Jugador ganador) {
            this.jugador1 = jugador1;
            this.jugador2 = jugador2;
            this.resultadosSets = resultadosSets;
            this.ganador = ganador;
        }
    }
}
