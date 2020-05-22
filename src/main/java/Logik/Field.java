package Logik;
//этот класс работает с маштабами поля, разбивая его на ячейки из картинок

import java.util.ArrayList;
import java.util.Random;

public class Field {
    private static Random random = new Random();
    private static Coord sizeOfField;
    private static ArrayList<Coord> listOfAllCoords;

    static void setSize(Coord size) {
        sizeOfField = size;                          //получаем размер поля
        listOfAllCoords = new ArrayList<Coord>();
        for (int y = 0; y < sizeOfField.y; y++)
            for (int x = 0; x < sizeOfField.x; x++) {
                listOfAllCoords.add(new Coord(x, y));  //список всех ячеек (матрицу 11 12 13 из объектов Сoord(x 1, y 1); в частоности использует для отображения поля в VIewClass
            }
    }

    public static Coord getSize() {
        return sizeOfField;
    }

    public static ArrayList<Coord> getListOfAllCoords() { //используется в MatrixOfField,
        return listOfAllCoords;
    }

    static boolean inField(Coord coord) { //проверяем, находится ли нащ элемент в пределах поля
        if ((coord.x >= 0 && coord.x < sizeOfField.x) && (coord.y >= 0 && coord.y < sizeOfField.y)) return true;
        return false;
    }

    static Coord randomCoord() { // метод для растановки бомб
        return new Coord(random.nextInt(sizeOfField.x), random.nextInt(sizeOfField.y));
    }

    static ArrayList<Coord> around(Coord coord) { //возвраущает лист с координатами вокруг заданной ячейки
        Coord num = null;
        ArrayList<Coord> list = new ArrayList<Coord>();
        for (int i = coord.x - 1; i <= coord.x + 1; i++) {
            for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                num = new Coord(i, y);
                if (inField(num) && !num.equals(coord)) {
                    list.add(num);
                }
            }
        }
        return list;
    }


}
