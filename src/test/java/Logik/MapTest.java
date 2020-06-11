package Logik;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MapTest {
    private int columns = 9;
    private int rows = 9;
    private Field field = new Field(new Coord(columns,rows));
    Map map = new Map(10, new Coord(columns, rows));


    @Test
    void mapTest(){
        MatrixOfField matrix = new MatrixOfField(ImageStorage.closed, field);
        map.startFlagging();
        checkMatrix(matrix, map.getMatrix(1));
        matrix.setPictureOnPosition(new Coord(0,0),ImageStorage.opened);
        matrix.setPictureOnPosition(new Coord(1,1),ImageStorage.opened);
        map.open(new Coord(0,0));
        map.open(new Coord(1,1));
        checkMatrix(matrix, map.getMatrix(1));

        ArrayList<Coord> thisList = new ArrayList<Coord>();
        thisList.add(new Coord(0,1));
        thisList.add(new Coord(1,1));
        thisList.add(new Coord(1,0));
        testAround(thisList, map.around(new Coord(0,0)));

        Map map1 = new Map(1, new Coord(9, 9));
        map1.startPlaceBomb();
        checkStartPlaceBomb(map1.getMatrix(2), map1.field.getListOfAllCoords());

    }

    void checkStartPlaceBomb(MatrixOfField matrix, ArrayList<Coord> list) {
        Coord bomb = null;
          for (Coord coord : list) {
            if (matrix.getPictureOnPosition(coord) == ImageStorage.bomb) bomb = coord;
        }
        assert bomb != null;
        for (Coord k : map.around(bomb)) {
            ImageStorage image = matrix.getPictureOnPosition(k);
            if (image != ImageStorage.num1) throw new AssertionError();

            }
    }


    private void testAround(ArrayList<Coord> thisList, ArrayList<Coord> around) {
        if (thisList.size() == around.size()){
            for (Coord coord : thisList) {
                if (!around.contains(coord)) return;
            }
        }
    }


    void checkMatrix(MatrixOfField thisM, MatrixOfField testM){
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                Coord coord = new Coord(i,j);
                if (thisM.getPictureOnPosition(coord) != testM.getPictureOnPosition(coord)) return;
            }
        }
    }

}