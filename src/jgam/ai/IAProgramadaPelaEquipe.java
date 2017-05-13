package jgam.ai;

import jgam.game.*;

public class IAProgramadaPelaEquipe extends EvaluatingAI {
    /**
     * evaluate a BoardSetup.
     * <p>
     * The result is an approximation of the probabiity to win.
     *
     * @param setup BoardSetup
     * @return double between 0 and 1.
     */


    /**
     * retorna a soma das pecas da ia
     *
     * <p>A soma das pecas da ia em cada casa sera poderada considerando
     * o avanco</p>
     *
     * @param setup
     * @return float greater or equal than 0
     */
    private float heuristicaAvanco(ArrayBoardSetup setup) {
        int player = setup.getActivePlayer();

        return setup.calcPip(player);
    }

    private float heuristicaProtecao(ArrayBoardSetup setup) {
        int player = setup.getActivePlayer();

        float protegidos = 0;

        for (int i = 0; i < 24; i++) {
            if (setup.getPoint(player, i) > 1) {
                protegidos += setup.getPoint(player, i);
            }
        }
        return protegidos;
    }

    private float heuristicaAtaque(ArrayBoardSetup setup) {
        PossibleMoves possibleMoves = new PossibleMoves(setup);

        java.util.List<> = possibleMoves.moveChains;
        return 0;
    }

    @Override
    public double propabilityToWin(BoardSetup setup) throws CannotDecideException {
        /* @todo só escreve aqui teu codigo */



        return 0;
    }

    /**
     * initialize this instance. Is called before it is used to
     * make decisions.
     *
     * @throws Exception if sth goes wrong during init.
     */
    @Override
    public void init() throws Exception {

    }

    /**
     * free all used resources.
     */
    @Override
    public void dispose() {

    }

    /**
     * get the name of this AI Method.
     *
     * @return String
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * get a short description of this method.
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return null;
    }
}