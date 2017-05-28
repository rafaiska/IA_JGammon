/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgam.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jgam.game.BoardSetup;
import jgam.game.PossibleMoves;
import jgam.game.SingleMove;

public class IADesafiante implements AI{
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
        return "IA Ofensiva";
    }

    /**
     * get a short description of this method.
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return "IA implementada com MiniMax de profundidade 2 com heurísticas ofensivas";
    }

    /**
     * given a board make decide which moves to make.
     * There may not be any dice values left after call to this function.
     *
     * @param boardSetup BoardSetup to evaluate
     * @return SingleMove[] a complete set of moves.
     * @throws CannotDecideException if the AI cannot decide which moves to make
     */

    double agressiveHeuristic (BoardSetup boardSetup) {
      double returnValue = 0.0;
      int player = boardSetup.getPlayerAtMove();
      int opponent = 3 - player;

      for (int i = 1; i <= 24; i++) {
        int checkersPoint = boardSetup.getPoint(player, i);

        //Casas com mais de 3 pecas acumuladas eh ruim, pois o agressivo pretende mover o rapido possivel
        if (checkersPoint >= 3) {
          returnValue -= 75.0;
        }

        else if (checkersPoint == 1){
          returnValue -= 25.0;
        }

        else
            returnValue += 25.0;
      }

      //Para o agressivo, quanto mais peca inimiga no inicio, eh melhor
      if (boardSetup.getPoint(opponent, 0) >= 1)
        returnValue += 100.0;

      if (boardSetup.getPoint(player, 0) >= 1) 
        returnValue -= 50.0;

      int point = boardSetup.getPoint(player, 25);
      returnValue += point * 50.0; 

      point = boardSetup.getPoint(opponent, 25);
      returnValue -= point * 50.0;

      return returnValue;
    }

    double opponentBestMove(BoardSetup boardSetup) {
        double bestValue = Double.NEGATIVE_INFINITY;
        for(int firstdie = 1; firstdie <= 6; ++firstdie) {
            for(int seconddie = firstdie; seconddie <= 6; ++seconddie) {
                boardSetup.getDice()[0] = firstdie;
                boardSetup.getDice()[1] = seconddie;

                PossibleMoves pm = new PossibleMoves(boardSetup);
                List list = pm.getPossbibleNextSetups();
                int index = 0;

                for (Iterator iter = list.iterator(); iter.hasNext(); index++) {
                    BoardSetup setup = (BoardSetup) iter.next();
                    double value = agressiveHeuristic(setup);

                    if (firstdie == seconddie)
                        value *= 0.8;

                    if (value > bestValue)
                        bestValue = value;

                }
            }
        }
        return bestValue;
    }

    @Override
    public SingleMove[] makeMoves(BoardSetup boardSetup) throws CannotDecideException {
        double bestValue = Double.NEGATIVE_INFINITY;
        int bestIndex = -1;
        int index = 0;

        PossibleMoves pm = new PossibleMoves(boardSetup);
        List list = pm.getPossbibleNextSetups();

        for (Iterator iter = list.iterator(); iter.hasNext(); index++) {
            BoardSetup setup = (BoardSetup) iter.next();
            double value = agressiveHeuristic(setup);
            value -= opponentBestMove(setup);

            if (value > bestValue) {
                bestValue = value;
                bestIndex = index;
            }
        }

        if (bestIndex == -1)
            return new SingleMove[0];
        else
            return pm.getMoveChain(bestIndex);

    }

    /**
     * given a board make decide whether to roll or to double.
     *
     * @param boardSetup BoardSetup
     * @return either DOUBLE or ROLL
     * @throws CannotDecideException if the AI cannot decide which moves to make
     */
    @Override
    public int rollOrDouble(BoardSetup boardSetup) throws CannotDecideException {
        return ROLL;
    }

    /**
     * given a board and a double offer, take or drop.
     * Evalutate the player whose turn it is NOT!
     *
     * @param boardSetup BoardSetup
     * @return either TAKE or DROP
     * @throws CannotDecideException if the AI cannot decide which moves to make
     */
    @Override
    public int takeOrDrop(BoardSetup boardSetup) throws CannotDecideException {
        return TAKE;
    }
}
