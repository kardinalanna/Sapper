package Logik;

public class BomdMap {
    private MatrixOfField bombMatrix;
    private int bombNumber;

    BomdMap(int bombNumber) {
        this.bombNumber = bombNumber;
        int limit = Field.getSize().x * Field.getSize().y / 2; //устанавливоем ограничение на количество бомб,если их больше, выставляем максимально возможное значение
        if (bombNumber > limit) bombNumber = limit;
    }

    void StartPlaceBomb() { //рандомно распалагаем бомбы, получая случайные координаты из Field
        bombMatrix = new MatrixOfField(ImageStorage.zero);
        for (int i = 0; i < bombNumber; i++) {
            while (true) {
                Coord coord = Field.randomCoord();
                if (bombMatrix.getPictureOnPosition(coord) == ImageStorage.bomb)
                    continue; //чтобы бомбы не накладывались друг на друга
                bombMatrix.setPictureOnPosition(coord, ImageStorage.bomb);
                for (Coord near : Field.around(coord)) { //окружаем бомбу цифрами, имея список коодинат вокруг, вносим в соответсвуюшие ячейки следующую по номеру картинку из
                    // хранилища(там соблюден порядок, после бомбы идет 1, а в этой ячейке уже есть цифра, берем следующую по номеру картинку 2 и т.д.
                    if (bombMatrix.getPictureOnPosition(near) != ImageStorage.bomb)
                        bombMatrix.setPictureOnPosition(near, bombMatrix.getPictureOnPosition(near).nextImage());
                }
                break;
            }
        }
    }

    public ImageStorage getFromBombMap(Coord coord) {
        return bombMatrix.getPictureOnPosition(coord);
    }
}
