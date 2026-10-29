package uk.ac.mmu.Game.applicationCode.domainmodel;

public class Player {
    private final int HOME_INDEX;
    private final int END_INDEX;
    private final int[] POSITIONS;

    private final String playerName;
    private final int direction;
    private int currentIndex;
    private int turnCount = 0;


    public Player(int start_index,int end_index, int[] positions, int direction, String playerName){
        this.HOME_INDEX = start_index;
        this.END_INDEX = end_index;
        this.POSITIONS = positions;
        this.currentIndex = start_index;
        this.direction = direction;
        this.playerName = playerName;
    }
    public String getPlayerName(){
        return playerName;
    }


    public int getTurnCount() {
        return turnCount;
    }

    public void incrementTurnCount() {
        this.turnCount++;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getDirection() {
        return direction;
    }


    public boolean isHome() {
        return currentIndex == HOME_INDEX;
    }

    public boolean isAtEnd() {
        return currentIndex == END_INDEX;
    }

    public int getPosition(){
        return POSITIONS[currentIndex];
    }

    public int getEND_INDEX(){
        return END_INDEX;
    }

    public int findIndexForPosition(int position) {
        for (int i = 0; i < POSITIONS.length; i++) {
            if (POSITIONS[i] == position) {
                return i;
            }
        }
        return -1;
    }



    @Override
    public String toString(){
        if(isHome())
            return String.format("Home (Position %d)", getPosition());
        if(isAtEnd())
            return String.format("End (Tail Position %d)", getPosition());
        else return String.format("Position %d",getPosition());

    }

}
