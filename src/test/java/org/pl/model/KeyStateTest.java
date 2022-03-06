package org.pl.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/*
    Jeśli mamy doczynienia z kluczem postaci:
    [0x54_68_61_74, 0x73_20_6d_79, 0x20_4b_75_6e, 0x67_20_46_75]

    To jest on zapisany w macierzy:
    {54 73 20 67}
    {68 20 4b 20}
    {61 6d 75 46}
    {74 79 6e 75}
 */
class KeyStateTest {
    KeyState key;

    public KeyStateTest() {
        key = new KeyState();
        key.convertKey(new int[]{
                0x54_68_61_74, 0x73_20_6d_79, 0x20_4b_75_6e, 0x67_20_46_75
        });
    }

    @Test
    void getterTest() {
        // First column
        assertThat(key.get(0, 0), is((byte)0x54));
        assertThat(key.get(0, 1), is((byte)0x68));
        assertThat(key.get(0, 2), is((byte)0x61));
        assertThat(key.get(0, 3), is((byte)0x74));

        //Second Column
        assertThat(key.get(1, 0), is((byte)0x73));
        assertThat(key.get(1, 1), is((byte)0x20));
        assertThat(key.get(1, 2), is((byte)0x6d));
        assertThat(key.get(1, 3), is((byte)0x79));

        //Third Column
        assertThat(key.get(2, 0), is((byte)0x20));
        assertThat(key.get(2, 1), is((byte)0x4b));
        assertThat(key.get(2, 2), is((byte)0x75));
        assertThat(key.get(2, 3), is((byte)0x6e));

        //Fourth Column
        assertThat(key.get(3, 0), is((byte)0x67));
        assertThat(key.get(3, 1), is((byte)0x20));
        assertThat(key.get(3, 2), is((byte)0x46));
        assertThat(key.get(3, 3), is((byte)0x75));
    }

    @Test
    void circularLeftShiftTest() {
        byte[] arr = {1, 2, 3, 4};
        key.circularLeftShift(arr, 0);
        assertThat(arr, equalTo(new byte[]{1, 2, 3, 4}));

        key.circularLeftShift(arr, 4);
        assertThat(arr, equalTo(new byte[]{1, 2, 3, 4}));

        key.circularLeftShift(arr, 1);
        assertThat(arr, equalTo(new byte[]{2, 3, 4, 1}));

        arr = new byte[]{1, 2, 3, 4};
        key.circularLeftShift(arr, 2);
        assertThat(arr, equalTo(new byte[]{3, 4, 1, 2}));

        arr = new byte[]{1, 2, 3, 4};
        key.circularLeftShift(arr, 3);
        assertThat(arr, equalTo(new byte[]{4, 1, 2, 3}));

        arr = new byte[]{1, 2, 3, 4};
        key.circularLeftShift(arr, 5);
        assertThat(arr, equalTo(new byte[]{2, 3, 4, 1}));
    }

    @Test
    void circularRightShiftTest() {
        byte[] arr = {1, 2, 3, 4};
        key.circularRightShift(arr, 0);
        assertThat(arr, equalTo(new byte[]{1, 2, 3, 4}));

        key.circularRightShift(arr, 4);
        assertThat(arr, equalTo(new byte[]{1, 2, 3, 4}));

        key.circularRightShift(arr, 1);
        assertThat(arr, equalTo(new byte[]{4, 1, 2, 3}));

        arr = new byte[]{1, 2, 3, 4};
        key.circularRightShift(arr, 2);
        assertThat(arr, equalTo(new byte[]{3, 4, 1, 2}));

        arr = new byte[]{1, 2, 3, 4};
        key.circularRightShift(arr, 3);
        assertThat(arr, equalTo(new byte[]{2, 3, 4, 1}));

        arr = new byte[]{1, 2, 3, 4};
        key.circularRightShift(arr, 5);
        assertThat(arr, equalTo(new byte[]{4, 1, 2, 3}));
    }

    @Test
    void shiftRowsTest() {
        key = new KeyState(new int[]{
                0x87_ec_4a_8c, 0xf2_6e_c3_d8, 0x4d_4c_46_95, 0x97_90_e7_a6
        });

        key.shiftRows();

        assertThat(key.get(0, 0), is((byte)0x87));
        assertThat(key.get(1, 0), is((byte)0xf2));
        assertThat(key.get(2, 0), is((byte)0x4d));
        assertThat(key.get(3, 0), is((byte)0x97));

        assertThat(key.get(0, 1), is((byte)0x6e));
        assertThat(key.get(1, 1), is((byte)0x4c));
        assertThat(key.get(2, 1), is((byte)0x90));
        assertThat(key.get(3, 1), is((byte)0xec));

        assertThat(key.get(0, 2), is((byte)0x46));
        assertThat(key.get(1, 2), is((byte)0xe7));
        assertThat(key.get(2, 2), is((byte)0x4a));
        assertThat(key.get(3, 2), is((byte)0xc3));

        assertThat(key.get(0, 3), is((byte)0xa6));
        assertThat(key.get(1, 3), is((byte)0x8c));
        assertThat(key.get(2, 3), is((byte)0xd8));
        assertThat(key.get(3, 3), is((byte)0x95));
    }

    @Test
    void mixColumnsTest() {
        key = new KeyState(new int[]{
                0x87_6e_46_a6, 0xf2_4c_e7_8c, 0x4d_90_4a_d8, 0x97_ec_c3_95
        });

        key.mixColumns();

        assertThat(key.get(0, 0), equalTo((byte)0x47));
        assertThat(key.get(1, 0), equalTo((byte)0x40));
        assertThat(key.get(2, 0), equalTo((byte)0xa3));
        assertThat(key.get(3, 0), equalTo((byte)0x4c));

        assertThat(key.get(0, 1), equalTo((byte)0x37));
        assertThat(key.get(1, 1), equalTo((byte)0xd4));
        assertThat(key.get(2, 1), equalTo((byte)0x70));
        assertThat(key.get(3, 1), equalTo((byte)0x9f));

        assertThat(key.get(0, 2), equalTo((byte)0x94));
        assertThat(key.get(1, 2), equalTo((byte)0xe4));
        assertThat(key.get(2, 2), equalTo((byte)0x3a));
        assertThat(key.get(3, 2), equalTo((byte)0x42));

        assertThat(key.get(0, 3), equalTo((byte)0xed));
        assertThat(key.get(1, 3), equalTo((byte)0xa5));
        assertThat(key.get(2, 3), equalTo((byte)0xa6));
        assertThat(key.get(3, 3), equalTo((byte)0xbc));
    }

    @Test
    void inverseMixColumnsTest() {
        key = new KeyState(new int[]{
                0x47_37_94_ed, 0x40_d4_e4_a5, 0xa3_70_3a_a6, 0x4c_9f_42_bc
        });

        key.inverseMixColumns();

        // 0x87_6e_46_a6, 0xf2_4c_e7_8c, 0x4d_90_4a_d8, 0x97_ec_c3_95
        assertThat(key.get(0, 0), equalTo((byte)0x87));
        assertThat(key.get(1, 0), equalTo((byte)0xf2));
        assertThat(key.get(2, 0), equalTo((byte)0x4d));
        assertThat(key.get(3, 0), equalTo((byte)0x97));

        assertThat(key.get(0, 1), equalTo((byte)0x6e));
        assertThat(key.get(1, 1), equalTo((byte)0x4c));
        assertThat(key.get(2, 1), equalTo((byte)0x90));
        assertThat(key.get(3, 1), equalTo((byte)0xec));

        assertThat(key.get(0, 2), equalTo((byte)0x46));
        assertThat(key.get(1, 2), equalTo((byte)0xe7));
        assertThat(key.get(2, 2), equalTo((byte)0x4a));
        assertThat(key.get(3, 2), equalTo((byte)0xc3));

        assertThat(key.get(0, 3), equalTo((byte)0xa6));
        assertThat(key.get(1, 3), equalTo((byte)0x8c));
        assertThat(key.get(2, 3), equalTo((byte)0xd8));
        assertThat(key.get(3, 3), equalTo((byte)0x95));
    }

    @Test
    void multiplyBy1Test() {
        byte a = 2;
        final byte b = 1;
        assertThat(key.multiply(a, b), equalTo(a));
        a = 3;
        assertThat(key.multiply(a, b), equalTo(a));
        a = 0;
        assertThat(key.multiply(a, b), equalTo(a));
    }

    @Test
    void multiplyBy2Test() {
        byte a = 2;
        final byte b = 2;
        assertThat(key.multiply(a, b), equalTo((byte)4));
        a = 3;
        assertThat(key.multiply(a, b), equalTo((byte)6));
        a = 0;
        assertThat(key.multiply(a, b), equalTo(a));
        a = 62;
        assertThat(key.multiply(a, b), equalTo((byte)124));
        a = (byte)0xd4;
        assertThat(key.multiply(a, b), equalTo((byte)0xb3));
    }

    @Test
    void multiplyBy3Test() {
        byte a = 2;
        final byte b = 3;
        assertThat(key.multiply(a, b), equalTo((byte)6));
        a = (byte)0xbf;
        assertThat(key.multiply(a, b), equalTo((byte)0xda));
        a = 0;
        assertThat(key.multiply(a, b), equalTo(a));
    }

    @Test
    void setterTest() {
        key.set(0, 0, (byte)0xff);
        assertThat(key.get(0, 0), equalTo((byte)0xff));
        key.set(3, 3, (byte)0x12);
        assertThat(key.get(3, 3), equalTo((byte)0x12));
        key.set(1, 2, (byte)0x68);
        assertThat(key.get(1, 2), equalTo((byte)0x68));
    }
}