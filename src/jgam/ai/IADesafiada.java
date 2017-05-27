/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jgam.ai;

import jgam.game.BoardSetup;
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
        return "Bagulho";
    }

    /**
     * get a short description of this method.
     *
     * @return String
     */
    @Override
    public String getDescription() {
        return "Bagulho Escroto";
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
        return new SingleMove[0];
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
        return 0;
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
        return 0;
    }
}
