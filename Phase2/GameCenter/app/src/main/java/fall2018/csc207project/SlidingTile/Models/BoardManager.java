package fall2018.csc207project.SlidingTile.Models;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Observer;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable, Iterable<Tile>{

    /**
     * The board being managed.
     */
    private Board board;

    private Stack<Integer> stackOfMovements;

    private int complexity;

    private int undoTimes;

    /**
     * The counting of total steps moved.
     */
    private int totalMoveSteps;

    /**
     * The counting of total steps undid.
     */
    private int totalUndoSteps;

    /**
     * Manage a new shuffled board.
     */
    public BoardManager(int dim, int undoTimes) {
        this.complexity = dim;
        this.undoTimes = undoTimes;
        stackOfMovements = new Stack<>();
        if (dim == 3) {
            this.board = (new SlidingTileGameShuffler()).shuffle(dim, 2);
        } else if (dim == 4) {
            this.board = (new SlidingTileGameShuffler()).shuffle(dim, 2);
        } else if (dim == 5) {
            this.board = (new SlidingTileGameShuffler()).shuffle(dim, 2);
        }
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return board.iterator();
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        int count = 1;
        Iterator<Tile> newIterator = board.iterator();
        while(newIterator.hasNext() && solved){
            solved = newIterator.next().getId() == count++;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / board.NUM_COLS;
        int col = position % board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int row = position / board.NUM_ROWS;
        int col = position % board.NUM_COLS;
        int blankId = board.numTiles();
        int rowBlank=0,colBlank=0;
        Iterator<Tile> newIterator = board.iterator();
        for (int i = 0; i < blankId; ++i){
            Tile temp = newIterator.next();
            if (temp.getId() == blankId) {
                rowBlank = i / board.NUM_COLS;
                colBlank = i % board.NUM_COLS;
            }
        }
        board.swapTiles(row,col,rowBlank,colBlank);
        totalMoveSteps++;
    }

    /**
     * Return the blank tile's position.
     *
     * @return the blank tile's position
     */
    private int findBlinkTilePosition() {
        int blankId = board.numTiles();
        Iterator<Tile> iterator = board.iterator();
        int blankPosition = 0;
        for (int i = 0; i < blankId; ++i){
            Tile temp = iterator.next();
            if (temp.getId() == blankId) {
                blankPosition = i;
            }
        }
        return blankPosition;
    }

    /**
     * Process the undo movement by the given steps.
     *
     * @param steps the steps you want to go back.
     */
    private void processUndoMovement(int steps) {
        for (int i = 0; i < steps; i++) {
            touchMove(stackOfMovements.pop());
            totalMoveSteps--;
        }
        totalUndoSteps++;
        undoTimes--;
    }

    public void pushLastStep(){
        stackOfMovements.push(findBlinkTilePosition());
    }

    /**
     * Return the total steps you did.
     *
     * @return the total steps you did
     */
    public int getTotalMoveSteps() {
        return totalMoveSteps;
    }

    /**
     * Return the total steps you moved.
     *
     * @return the total steps you moved
     */
    public int getTotalUndoSteps() {
        return totalUndoSteps;
    }

    public int getComplexity(){
        return complexity;
    }


    public void subscribe(Observer o){
        board.addObserver(o);
    }

    public boolean undo(int step){
        if (undoTimes == 0 || step > stackOfMovements.size()){
            return false;
        }
        processUndoMovement(step);
        return true;
    }
}
