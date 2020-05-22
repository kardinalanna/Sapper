package Logik;

//реализует связь между логикой и отрисовкой
public class Controller {
    private BomdMap bomdMap;
    private FlagMap flagMap;
    private State state;
    private int countOfBomb;

    public Controller(int columns, int rows, int bombNumber) { //передаем размеры поля из ViewClass и отсюда все понеслось...
        countOfBomb = bombNumber;
        Field.setSize(new Coord(columns, rows)); //запускаем разбиение поля на ячейкис координатами
        bomdMap = new BomdMap(bombNumber); //созжаем нижнее поле
        flagMap = new FlagMap(); //создаем верхнее поле
    }

    public void start() { //ключевой метод, запускающийся в DialogWithUser! Именно с него начинается логика!
        bomdMap.StartPlaceBomb(); //в класск BombMap разбрасываем бомбы и окружаем их цифрами
        flagMap.startFlagging(); //верхнее поле сначала полность состоит из closed
        state = State.playing;//определяем состояние, которое будет playing пока не выполнится условие для winner или bombed
    }

    public ImageStorage getPictureFromImageStorage(Coord coord) { //непосредственно используется в ViewClass для отображения матрицы из картинок
        if (flagMap.getFromFlagMap(coord) == ImageStorage.opened) //(если в "верхнем" поле ячейка открыта - отображаем соответсвую ее координатам ячейку в "нмжнем" поле)
            return bomdMap.getFromBombMap(coord);
        else
            return flagMap.getFromFlagMap(coord);
    }

    public void presButton1(Coord coord) { //необъходимо заменит картинку верхнего поля на картинку нижнего
        if (gameOver()) return;
        openBox(coord);
        winner();
    }

    private void openBox(Coord coord) { //при нажатии левой кнопкой мыши
        switch (flagMap.getFromFlagMap(coord)) {
            case opened: { //если ячейка открыта и бомбы вокруг нее помечены флагами, открываем оставшие цифры и пустые ячейки
                if (bomdMap.getFromBombMap(coord) != ImageStorage.bomb && flagMap.getCountOfFlagged(coord) == bomdMap.getFromBombMap(coord).getNumber())
                    for (Coord near : Field.around(coord)) {
                        if (flagMap.getFromFlagMap(near) == ImageStorage.closed) openBox(near);
                    }
            }
            case flaged:
                return; //не можем открыть флагированную ячейку
            case closed:
                switch (bomdMap.getFromBombMap(coord)) { //если ячейка закрыта, проверяем, что находится под ней в BombMap
                    case zero: { //если пустая открываем саму ячейку и  рекурсивно все ячейки до первых бомб
                        flagMap.open(coord);
                        for (Coord near : Field.around(coord)) {
                            openBox(near);
                        }
                    }
                    break;
                    case bomb:
                        findBomb(coord);
                        break;
                    default:
                        flagMap.open(coord);
                        break;
                }
        }
    }

    private void findBomb(Coord coord) {
        state = State.bombed;
        flagMap.flag.setPictureOnPosition(coord, ImageStorage.bombed); //если игрок наткнулся на бомбу, отображаем взрыв в этой координате
        for (Coord point : Field.getListOfAllCoords()) {
            if (bomdMap.getFromBombMap(point) == ImageStorage.bomb)
                flagMap.flag.setPictureOnPosition(point, ImageStorage.opened); //открываем все ячейки с бомбами после проигрыша
            else if (flagMap.getFromFlagMap(point) == ImageStorage.flaged)
                flagMap.flag.setPictureOnPosition(point, ImageStorage.nobomb); //омечаем флагированные ячейки без бомб в конце ингры
        }
    }

    public void presButton3(Coord coord) {//обработка нажатия правой клавищи - постановка/снятие флага
        if (gameOver()) return;
        switch (flagMap.flag.getPictureOnPosition(coord)) {
            case flaged: {
                flagMap.flag.setPictureOnPosition(coord, ImageStorage.closed);
            }
            break;
            case closed: {
                flagMap.flag.setPictureOnPosition(coord, ImageStorage.flaged);
            }
            break;
        }
    }

    public State getState() {
        return state;
    } //getter для состояния

    private boolean gameOver() {
        if (state == State.playing) return false;
        start();
        return true;
    }

    private void winner() { //если кол-во закрытых ячеек, считающихся при открытии ячеек в "верхнем" поле = кол-ву бомб, игрок победил
        if (state == State.playing && flagMap.closed == countOfBomb) {
            state = State.winner;
        }
    }


}
