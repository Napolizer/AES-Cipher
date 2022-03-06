package org.pl.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class KeyState extends State {

    public KeyState() {
        super();
    }

    public KeyState(int[] values) {
        this();
        convertKey(values);
    }

    public void convertKey(int[] newValue) {
        validateSize(newValue.length);

        for (int y = 0; y < size; ++y) {
            for (int x = 0; x < size; ++x) {
                set(x, y, (byte)((newValue[x] >> (8 * Math.abs(y-3))) & 0xff));
            }
        }
    }
}
