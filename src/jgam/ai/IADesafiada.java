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

/**
 *
 * @author rafael
 */
public class IADesafiada implements AI{
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
        return "IA Defensiva";
    }

    /**
     * get a short description of this method.
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return "IA implementada com MiniMax de profundidade 2 com heurísticas defensivas";
    }

    double HeuristicaDefensiva(BoardSetup boardSetup)
    {
        double retorno = 0.0;
        int jogador = boardSetup.getPlayerAtMove();
        int oponente = 3 - jogador;        

        //Avaliação: número de peças nas casas
        for (int i = 1; i <= 24; i++) {
            int p = boardSetup.getPoint(jogador, i);
//            System.out.print(p + " ");
            
            //Casa com uma peça: PERIGO!
            if (p == 1) {
                retorno -= 50.0;
            }
            //Número ideal de peças na casa
            else if (p == 2 || p == 3) {
                retorno += 50.0;
            }
            //Mais que três peças em uma casa forma uma torre. O ideal é que as
            //peças fiquem mais distribuídas
            else {
                retorno -= 20.0;
            }
        }
        
        //Peça inimiga foi "comida"?
        if(boardSetup.getPoint(oponente, 0) == 1)
        {
            retorno += 20.0;
        }
        
        //Peça aliada foi "comida"?
        if(boardSetup.getPoint(jogador, 0) == 1)
        {
            retorno -= 50.0;
        }
        
        //Pontos totais do jogador
        int pontos = boardSetup.getPoint(jogador, 25);
        retorno += pontos * 50.0;
        
        //Pontos totais do oponente
        pontos = boardSetup.getPoint(oponente, 25);
        retorno -= pontos * 50.0;
        
//        System.out.print("\n");
        
        return retorno;
    }
    
    double MelhorJogadaOponente(BoardSetup boardSetup)
    {
        double bestValue = Double.NEGATIVE_INFINITY;
        for(int firstdie = 1; firstdie <= 6; ++firstdie)
        {
            for(int seconddie = firstdie; seconddie <= 6; ++seconddie)
            {
                boardSetup.getDice()[0] = firstdie;
                boardSetup.getDice()[1] = seconddie;

                PossibleMoves pm = new PossibleMoves(boardSetup);
                List list = pm.getPossbibleNextSetups();
                int index = 0;
                for (Iterator iter = list.iterator(); iter.hasNext(); index++) {
                    BoardSetup setup = (BoardSetup) iter.next();
                    double value = HeuristicaDefensiva(setup);
                    if (firstdie == seconddie)
                    {
                        //É mais improvável cair dois dados iguais
                        //Esse peso funciona para balancear esse caso especial
                        value *= 0.8;
                    }
                    if (value > bestValue) {
                        bestValue = value;
                    }
                }
            }
        }
        return bestValue;
    }
    
    /**
     * given a board make decide which moves to make.
     * There may not be any dice values left after call to this function.
     *
     * @param boardSetup BoardSetup to evaluate
     * @return SingleMove[] a complete set of moves.
     * @throws CannotDecideException if the AI cannot decide which moves to make
     */
    @Override
    public SingleMove[] makeMoves(BoardSetup boardSetup) throws CannotDecideException {
        double bestValue = Double.NEGATIVE_INFINITY;
        int bestIndex = -1;

        PossibleMoves pm = new PossibleMoves(boardSetup);
        List list = pm.getPossbibleNextSetups();
        int index = 0;
        for (Iterator iter = list.iterator(); iter.hasNext(); index++) {
            BoardSetup setup = (BoardSetup) iter.next();
            double value = HeuristicaDefensiva(setup);
            value -= MelhorJogadaOponente(setup);
            if (value > bestValue) {
                bestValue = value;
                bestIndex = index;
            }
        }

        if (bestIndex == -1)
            return new SingleMove[0];
        else {
            // Sy stem.out.println("Evaluation for this move: "+bestValue);
            return pm.getMoveChain(bestIndex);
        }
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
