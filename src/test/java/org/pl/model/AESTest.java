package org.pl.model;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class AESTest {
    private final AES aes;

    public AESTest() {
        aes = new AES();
    }

    @Test
    void rotWordTest() {
        int word = 0xff_ff_ff_fa;
        assertThat(aes.rotWord(word), is(0xff_ff_fa_ff));

        word = 0xaf_fa_ff_ff;
        assertThat(aes.rotWord(word), is(0xfa_ff_ff_af));

        word = 0;
        assertThat(aes.rotWord(word), is(0));

        word = 0x00_01_aa_ac;
        assertThat(aes.rotWord(word), is(0x01_aa_ac_00));
    }

    @Test
    void subWordTest() {
        int word = 0xff_aa_00_3c;
        assertThat(aes.subWord(word), is(0x16_ac_63_eb));

        word = 0x00_00_00_00;
        assertThat(aes.subWord(word), is(0x63_63_63_63));
    }

    @Test
    void applySboxTest() {
        int b = 0xb3;
        assertThat(aes.applySbox(b), is(0x6d));

        b = 0x00;
        assertThat(aes.applySbox(b), is(0x63));

        b = 0xff;
        assertThat(aes.applySbox(b), is(0x16));

        b = 0x3c;
        assertThat(aes.applySbox(b), is(0xeb));
    }

    @Test
    void expandKeyTest() {
        String key = "Thats my Kung Fu";
        int[] keys = aes.expandKey(key);

        assertThat(keys.length, is(44));

        // Round0
        assertThat(keys[0], equalTo(0x54_68_61_74));
        assertThat(keys[1], equalTo(0x73_20_6d_79));
        assertThat(keys[2], equalTo(0x20_4b_75_6e));
        assertThat(keys[3], equalTo(0x67_20_46_75));

        // Round1
        assertThat(keys[4], equalTo(0xe2_32_fc_f1));
        assertThat(keys[5], equalTo(0x91_12_91_88));
        assertThat(keys[6], equalTo(0xb1_59_e4_e6));
        assertThat(keys[7], equalTo(0xd6_79_a2_93));

        // Round2
        assertThat(keys[8], equalTo(0x56_08_20_07));
        assertThat(keys[9], equalTo(0xc7_1a_b1_8f));
        assertThat(keys[10], equalTo(0x76_43_55_69));
        assertThat(keys[11], equalTo(0xa0_3a_f7_fa));

        // Round3
        assertThat(keys[12], equalTo(0xd2_60_0d_e7));
        assertThat(keys[13], equalTo(0x15_7a_bc_68));
        assertThat(keys[14], equalTo(0x63_39_e9_01));
        assertThat(keys[15], equalTo(0xc3_03_1e_fb));

        // Round4
        assertThat(keys[16], equalTo(0xa1_12_02_c9));
        assertThat(keys[17], equalTo(0xb4_68_be_a1));
        assertThat(keys[18], equalTo(0xd7_51_57_a0));
        assertThat(keys[19], equalTo(0x14_52_49_5b));

        // Round5
        assertThat(keys[20], equalTo(0xb1_29_3b_33));
        assertThat(keys[21], equalTo(0x05_41_85_92));
        assertThat(keys[22], equalTo(0xd2_10_d2_32));
        assertThat(keys[23], equalTo(0xc6_42_9b_69));

        // Round6
        assertThat(keys[24], equalTo(0xbd_3d_c2_87));
        assertThat(keys[25], equalTo(0xb8_7c_47_15));
        assertThat(keys[26], equalTo(0x6a_6c_95_27));
        assertThat(keys[27], equalTo(0xac_2e_0e_4e));

        // Round7
        assertThat(keys[28], equalTo(0xcc_96_ed_16));
        assertThat(keys[29], equalTo(0x74_ea_aa_03));
        assertThat(keys[30], equalTo(0x1e_86_3f_24));
        assertThat(keys[31], equalTo(0xb2_a8_31_6a));

        // Round10
        assertThat(keys[40], equalTo(0x28_fd_de_f8));
        assertThat(keys[41], equalTo(0x6d_a4_24_4a));
        assertThat(keys[42], equalTo(0xcc_c0_a4_fe));
        assertThat(keys[43], equalTo(0x3b_31_6f_26));
    }

    @Test
    void cipherTest() {
        byte[] text = "Two One Nine Two".getBytes(StandardCharsets.UTF_8);
        String key = "Thats my Kung Fu";
        byte[] cipherText = new byte[]{
                0x29, (byte)0xc3, 0x50, 0x5f, 0x57, 0x14, 0x20, (byte)0xf6,
                0x40, 0x22, (byte)0x99, (byte)0xb3, 0x1a, 0x02, (byte)0xd7, 0x3a
        };

        InputStream output = aes.cipher(new ByteArrayInputStream(text), key);

        assertDoesNotThrow(() -> {
            assertThat(output.readAllBytes(), equalTo(cipherText));
        });
    }

    @Test
    void transformBufferTest() {
        byte[] originalText = "Two One Nine Two".getBytes(StandardCharsets.UTF_8);
        String key = "Thats my Kung Fu";
        byte[] cipherText = new byte[]{
                0x29, (byte)0xc3, 0x50, 0x5f, 0x57, 0x14, 0x20, (byte)0xf6,
                0x40, 0x22, (byte)0x99, (byte)0xb3, 0x1a, 0x02, (byte)0xd7, 0x3a
        };

        byte[] output = aes.encryptBuffer(originalText, key);

        assertThat(output, equalTo(cipherText));
    }

    @Test
    void decryptBufferTest() {
        byte[] originalText = "Two One Nine Two".getBytes(StandardCharsets.UTF_8);
        String key = "Thats my Kung Fu";
        byte[] cipherText = new byte[]{
                0x29, (byte)0xc3, 0x50, 0x5f, 0x57, 0x14, 0x20, (byte)0xf6,
                0x40, 0x22, (byte)0x99, (byte)0xb3, 0x1a, 0x02, (byte)0xd7, 0x3a
        };

        byte[] output = aes.decryptBuffer(cipherText, key);

        assertThat(output, equalTo(originalText));
    }
}