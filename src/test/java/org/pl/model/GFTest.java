package org.pl.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class GFTest {
    @Test
    void multiplyBy1Test() {
        assertThat(GF.multiply(0, 1), is((byte)0));
        assertThat(GF.multiply(1, 1), is((byte)1));
        assertThat(GF.multiply(2, 1), is((byte)2));
        assertThat(GF.multiply(3, 1), is((byte)3));
        assertThat(GF.multiply(30, 1), is((byte)30));
        assertThat(GF.multiply(127, 1), is((byte)127));
        assertThat(GF.multiply(-128, 1), is((byte)-128));
    }
    @Test
    void multiplyBy2Test() {
        assertThat(GF.multiply(0, 2), is((byte)0));
        assertThat(GF.multiply(1, 2), is((byte)2));
        assertThat(GF.multiply(2, 2), is((byte)4));
        assertThat(GF.multiply(3, 2), is((byte)6));
        assertThat(GF.multiply(30, 2), is((byte)60));
        assertThat(GF.multiply(127, 2), is((byte)-2));
        assertThat(GF.multiply(-128, 2), is((byte)27));
    }
    @Test
    void multiplyBy3Test() {
        assertThat(GF.multiply(0, 3), is((byte)0));
        assertThat(GF.multiply(1, 3), is((byte)3));
        assertThat(GF.multiply(2, 3), is((byte)6));
        assertThat(GF.multiply(3, 3), is((byte)5));
    }
    @Test
    void multiplyBy9Test() {
        assertThat(GF.multiply(0, 9), is((byte)0));
        assertThat(GF.multiply(1, 9), is((byte)9));
        assertThat(GF.multiply(2, 9), is((byte)0x12));
        assertThat(GF.multiply(3, 9), is((byte)0x1b));
    }
    @Test
    void multiplyBy11Test() {
        assertThat(GF.multiply(0, 11), is((byte)0));
        assertThat(GF.multiply(1, 11), is((byte)11));
        assertThat(GF.multiply(2, 11), is((byte)0x16));
        assertThat(GF.multiply(3, 11), is((byte)0x1d));
    }
    @Test
    void multiplyBy13Test() {
        assertThat(GF.multiply(0, 13), is((byte)0));
        assertThat(GF.multiply(1, 13), is((byte)13));
        assertThat(GF.multiply(2, 13), is((byte)0x1a));
        assertThat(GF.multiply(3, 13), is((byte)0x17));
    }
    @Test
    void multiplyBy14Test() {
        assertThat(GF.multiply(0, 14), is((byte)0));
        assertThat(GF.multiply(1, 14), is((byte)14));
        assertThat(GF.multiply(2, 14), is((byte)0x1c));
        assertThat(GF.multiply(3, 14), is((byte)0x12));
    }
}