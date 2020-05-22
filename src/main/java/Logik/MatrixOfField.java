package Logik;

//этот кдасс непосредственно работает с заполнением ячеек картинками (в то время как Field - представлял поле как матрицу с координатами ячеек)
//он является основой "верхнего" и "нижнего" полей
class MatrixOfField {
    private ImageStorage[][] matrix;

    MatrixOfField(ImageStorage picture) {                                // в конструкторетмы заполняем матрицу поля заданной в параметре картинкой из хранилища картинок
        matrix = new ImageStorage[Field.getSize().x][Field.getSize().y];   // размер поля берем из Field (он равен кол-ву столбцов и строк, задаваемых в ViewClass)
        for (Coord coord : Field.getListOfAllCoords())                      // перебирая ячейки в Field, заполняем матрицу
            matrix[coord.x][coord.y] = picture;
    }

    ImageStorage getPictureOnPosition(Coord coord) {                      //можем узнать, какая картинка лежит в ячейке; используется в FlagMap и BombMap
        if (Field.inField(coord)) return matrix[coord.x][coord.y];
        else {
            return null;
        }
    }

    void setPictureOnPosition(Coord coord, ImageStorage thisPicture) {     //можем поместить катринку в нужную ячейку. с помощью этого метода отображаются все изменения на игровом поле
        if (Field.inField(coord))
            matrix[coord.x][coord.y] = thisPicture; //на игровом поле, в нужную координате изменяем картинку, потом матрица снова отрисовывается (после mouseEvent) в ViewClass
    }


}
