package battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Field {

    List<List<String>> field = new ArrayList<>();


    public List<List<String>> getField() {
        return field;
    }

    public void setField(List<List<String>> field) {
        this.field = field;
    }

    public void makeField() {
        for (int i = 0; i < 11; i++) {
            field.add(new ArrayList<>(Collections.nCopies(11, "~ ")));
        }
        // 0행 0열에는 " "를 넣어줍니다.
        field.get(0).set(0, "  ");

        // 각 열의 1행에는 " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"을 넣어줍니다.
        for (int i = 1; i < 11; i++) {
            field.get(0).set(i, i + " ");
        }

        // 각 행의 1열에는 " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"를 넣어줍니다.
        for (int i = 1; i < 11; i++) {
            field.get(i).set(0, (char) (i + 64) + " ");
        }
    }

    public void printField() {
        for (List<String> row : field) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }




}
