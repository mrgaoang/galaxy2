package number;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 罗马符号价值枚举
 */
public enum BasicCell {

    I("I", 1d),
    V("V", 5d),
    X("X", 10d),
    L("L", 50d),
    C("C", 100d),
    D("D", 500d),
    M("M", 1000d);

    /**
     * 罗马符号set
     */
    public static Set<BasicCell> cellSet;

    static {
        cellSet = new HashSet<>(Arrays.asList(BasicCell.values()));
    }

    private String name;
    private double value;

    BasicCell(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    /**
     * 获取基础符号
     *
     * @param cell
     * @return
     */
    public static BasicCell getWithChar(char cell) {
        for (BasicCell basicCell : BasicCell.values()) {
            if (basicCell.getName().equals(String.valueOf(cell))) {
                return basicCell;
            }
        }
        return null;
    }

    /**
     * 获取基础符号
     *
     * @param cell
     * @return
     */
    public static BasicCell getWithString(String cell) {
        for (BasicCell basicCell : BasicCell.values()) {
            if (basicCell.getName().equals(cell)) {
                return basicCell;
            }
        }
        return null;
    }

}
