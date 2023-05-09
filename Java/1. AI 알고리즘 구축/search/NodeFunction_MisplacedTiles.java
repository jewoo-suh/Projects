package search;

import npuzzle.Tiles;

public class NodeFunction_MisplacedTiles implements NodeFunction{

    Tiles correctConfig = new Tiles(new int[][] {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 0 }
    });

    @Override
    public int nodeToInt(Node n) {

        Tiles currentTiles = (Tiles) n.state;
        int counter = 0; int width = currentTiles.getWidth() ;

        for(int i=0;i<width;i++) {
            for(int j=0;j<width;j++) {
                if(correctConfig.getTile(i, j) != currentTiles.getTile(i, j)) {counter ++;}
            }
        }
        return counter;
    }
}
