package org.pl.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

// TODO only 16 byte keys
/*
    Jeśli mamy doczynienia z kluczem postaci:
    [0x5size_68_61_7size, 0x73_20_6d_79, 0x20_sizeb_75_6e, 0x67_20_size6_75]

    To jest on zapisany w macierzy:
    {5size 73 20 67}
    {68 20 sizeb 20}
    {61 6d 75 size6}
    {7size 79 6e 75}
 */
@EqualsAndHashCode
@ToString
public class State {
    private final byte[][] values;
    final int size = 4;

    public State() {
        values = new byte[size][size];
    }

    public State(byte[] values) {
        this();
        convertBuffer(values);
    }

    protected void validateSize(int size) {
        if (size != 4) {
            throw new RuntimeException("Rozmiar" + size + " nie jest wspierany");
        }
    }

    public void convertBuffer(byte[] newValue) {
        validateSize(newValue.length/4);

        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                values[y][x] = newValue[y + x * size];
            }
        }
    }

    public byte get(int x, int y) {
        return values[y][x];
    }

    public void set(int x, int y, byte val) {
        values[y][x] = val;
    }


    public void circularLeftShift(byte[] what, int howManyTimes) {
        howManyTimes %= size;
        var orgArr = what.clone();
        for (int i = 0; i < size; ++i) {
            what[i] = orgArr[(i + howManyTimes) % size];
        }
    }
    public void circularRightShift(byte[] what, int howManyTimes) {
        howManyTimes %= size;
        var orgArr = what.clone();
        for (int i = 0; i < size; ++i) {
            int delta = (i - howManyTimes) % size;
            if (delta < 0) {
                delta += size;
            }
            what[i] = orgArr[delta];
        }
    }

    // Można zrobić w pętli, ale tak jest chyba bardziej czytelnie
    public void shiftRows() {
        // First row is unchanged
        // Second row shift by 1
        circularLeftShift(values[1],1);
        // Third row shift by 2
        circularLeftShift(values[2],2);
        // Fourth row shift by 3
        circularLeftShift(values[3],3);
    }
    public void inverseShiftRows() {
        circularRightShift(values[1],1);
        circularRightShift(values[2],2);
        circularRightShift(values[3],3);
    }

    public void mixColumns() {
                /*
        [c0] { 02 03 01 01 }     [ans0]
        [c1] { 01 02 03 01 }  =  [ans1]
        [c2] { 01 01 02 03 }  =  [ans2]
        [c3] { 03 01 01 02 }     [ans3]
         */
        byte[][] matrix = new byte[][] {
                {2, 3, 1, 1},
                {1, 2, 3, 1},
                {1, 1, 2, 3},
                {3, 1, 1, 2}
        };
        mixColumns(matrix);
    }

    public void mixColumns(byte[][] matrix) {
        byte[][] orgValues = new byte[size][size];
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                orgValues[y][x] = values[y][x];
            }
        }

        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                byte sum = multiply(orgValues[0][x], matrix[y][0]);
                for (int i = 1; i < size; ++i) {
                    sum ^= multiply(orgValues[i][x], matrix[y][i]);
                }
                values[y][x] = sum;
            }
        }
    }

    public void inverseMixColumns() {
        byte[][] matrix = new byte[][] {
                {14, 11, 13, 9},
                {9, 14, 11, 13},
                {13, 9, 14, 11},
                {11, 13, 9, 14}
        };
        mixColumns(matrix);
    }

    public byte multiply(byte a, byte b) {
        return GF.multiply(a, b);
    }

    public void xor(State otherState) {
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                set(x, y, (byte)(
                        get(x, y) ^ otherState.get(x, y)
                ));
            }
        }
    }

    public void substitute(int[][] sbox) {
        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                int first_half = (0xf0 & get(x, y)) >> size;
                int second_half = 0x0f & get(x, y);
                set(x, y, (byte)sbox[first_half][second_half]);
            }
        }
    }
}
