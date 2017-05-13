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
    private float heuristicaAvanco(BoardSetup setup) {
        int player = setup.getActivePlayer();

        return setup.calcPip(player);
    }

    private float heuristicaProtecao(BoardSetup setup) {
        int player = setup.getActivePlayer();

        float protegidos = 0;

        for (int i = 0; i < 24; i++) {
            if (setup.getPoint(player, i) > 1) {
                protegidos++;
            }
        }
        return protegidos;
    }

    private float heuristicaAtaque(BoardSetup setup) {
        float atacaveis = 0;

        int playerAtacante = setup.getActivePlayer();
        int playerDefensor = (playerAtacante == 1) ? 1: 2;

        PossibleMoves possibleMoves = new PossibleMoves(setup);

        java.util.List<java.util.List<SingleMove>> moveChains = possibleMoves.getPossibleMoveChains();
        for (java.util.List  list : moveChains) {
            for (Object move : list) {
                int to = ((SingleMove)move).to();

                atacaveis += setup.getPoint(playerDefensor, to);
            }
        }

        return atacaveis;
    }

    @Override
    public double propabilityToWin(BoardSetup setup) throws CannotDecideException {
        /* @todo só escreve aqui teu codigo */

        return heuristicaProtecao(setup) + heuristicaAvanco(setup);
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
        return "IA Radical";
    }

    /**
     * get a short description of this method.
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return "Ia mais radical";
    }
}