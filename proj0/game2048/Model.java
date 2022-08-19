package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author Haitian Li
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.setViewingPerspective(side);
        for (int col = 0; col < board.size(); col += 1) {
            if (tiltUpOneSingleColumn(col, board)) {
                changed = true;
            }
        }
        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** True iff a single tile at (COL, 3) or (COL, 2) comes from merging. */
    private boolean row3Merged = false, row2Merged = false;

    /** Helper method that processes a single column COL in board B
     * when tilt up North. Return true iff this changes the board. */
    private boolean tiltUpOneSingleColumn(int col, Board b) {
        boolean changed2 = tiltUpOneSingleTileAtRow2(col, b);
        boolean changed1 = tiltUpOneSingleTileAtRow1(col, b);
        boolean changed0 = tiltUpOneSingleTileAtRow0(col, b);

        row3Merged = false;
        row2Merged = false;

        return changed2 || changed1 || changed0;
    }

    /** Helper method that processes a single tile at (COL, 2) in board B
     * when tilt up North. Return true iff this changes the board. */
    private boolean tiltUpOneSingleTileAtRow2(int col, Board b) {
        boolean changed = false;

        if (b.tile(col, 2) != null) {
            if (b.tile(col, 3) == null) {
                Tile t = b.tile(col, 2);
                b.move(col, 3, t);
                changed = true;
            } else if (b.tile(col, 2).value() == b.tile(col, 3).value()) {
                Tile t = b.tile(col, 2);
                b.move(col, 3, t);
                score += b.tile(col, 3).value();
                row3Merged = true;
                changed = true;
            }
        }

        return changed;
    }

    /** Helper method that processes a single tile at (COL, 1) in board B
     * when tilt up North. Return true iff this changes the board. */
    private boolean tiltUpOneSingleTileAtRow1(int col, Board b) {
        boolean changed = false;

        if (b.tile(col, 1) != null) {
            if (b.tile(col, 3) == null && b.tile(col, 2) == null) {
                Tile t = b.tile(col, 1);
                b.move(col, 3, t);
                changed = true;
            } else if (b.tile(col, 2) == null) {
                if (!row3Merged && b.tile(col, 1).value() == b.tile(col, 3).value()) {
                    Tile t = b.tile(col, 1);
                    b.move(col, 3, t);
                    score += b.tile(col, 3).value();
                    row3Merged = true;
                    changed = true;
                } else {
                    Tile t = b.tile(col, 1);
                    b.move(col, 2, t);
                    changed = true;
                }
            } else if (b.tile(col, 1).value() == b.tile(col, 2).value()) {
                Tile t = b.tile(col, 1);
                b.move(col, 2, t);
                score += b.tile(col, 2).value();
                row2Merged = true;
                changed = true;
            }
        }

        return changed;
    }

    /** Helper method that processes a single tile at (COL, 0) in board B
     * when tilt up North. Return true iff this changes the board. */
    private boolean tiltUpOneSingleTileAtRow0(int col, Board b) {
        boolean changed = false;

        if (b.tile(col, 0) != null) {
            if (b.tile(col, 3) == null && b.tile(col, 2) == null && b.tile(col, 1) == null) {
                Tile t = b.tile(col, 0);
                b.move(col, 3, t);
                changed = true;
            } else if (b.tile(col, 2) == null) {
                if (!row3Merged && b.tile(col, 0).value() == b.tile(col, 3).value()) {
                    Tile t = b.tile(col, 0);
                    b.move(col, 3, t);
                    score += b.tile(col, 3).value();
                    row3Merged = true;
                    changed = true;
                } else {
                    Tile t = b.tile(col, 0);
                    b.move(col, 2, t);
                    changed = true;
                }
            } else if (b.tile(col, 1) == null) {
                if (!row2Merged && b.tile(col, 0).value() == b.tile(col, 2).value()) {
                    Tile t = b.tile(col, 0);
                    b.move(col, 2, t);
                    score += b.tile(col, 2).value();
                    row2Merged = true;
                    changed = true;
                } else {
                    Tile t = b.tile(col, 0);
                    b.move(col, 1, t);
                    changed = true;
                }
            } else if (b.tile(col, 0).value() == b.tile(col, 1).value()) {
                Tile t = b.tile(col, 0);
                b.move(col, 1, t);
                score += b.tile(col, 1).value();
                changed = true;
            }
        }

        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for (int col = 0; col < b.size(); col += 1) {
            for (int row = 0; row < b.size(); row += 1) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for (int col = 0; col < b.size(); col += 1) {
            for (int row = 0; row < b.size(); row += 1) {
                if (b.tile(col, row) != null) {
                    if (b.tile(col, row).value() == MAX_PIECE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if (emptySpaceExists(b)) {
            return true;
        }
        for (int col = 0; col < b.size() - 1; col += 1) {
            for (int row = 0; row < b.size(); row += 1) {
                    if (b.tile(col, row).value() == b.tile(col+1, row).value()) {
                        return true;
                    }
            }
        }
        for (int col = 0; col < b.size(); col += 1) {
            for (int row = 0; row < b.size() - 1; row += 1) {
                if (b.tile(col, row).value() == b.tile(col, row+1).value()) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}