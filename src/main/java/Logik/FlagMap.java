package Logik;

//"верхний" слой поля, на котором можно ставить флаги и открывать ячийки, отображая лежащее под ними BoombMap
public class FlagMap {
    MatrixOfField flag;
    int closed = Field.getSize().x * Field.getSize().y; //считает количество закрытых ячеек, если оно = кол-ву бомб, игрок победил

    void startFlagging() { //сначала наше поля заполнено закрытыми ячейками
        flag = new MatrixOfField(ImageStorage.closed);//запускается из Controller и создает "верхнее" поле на основе MatrixOfField
    }

    ImageStorage getFromFlagMap(Coord coord) {
        return flag.getPictureOnPosition(coord);
    }

    void open(Coord coord) {
        flag.setPictureOnPosition(coord, ImageStorage.opened);
        closed--;
    }

    int getCountOfFlagged(Coord coord) {  //подсчитывает флаги рядом с заданной ячейкой
        int count = 0;
        for (Coord near : Field.around(coord)) if (flag.getPictureOnPosition(near) == ImageStorage.flaged) count++;
        return count;
    }

}
