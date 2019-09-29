package number;

import java.util.Arrays;
import java.util.Objects;

import validImpl.AbstractValid;
import validImpl.RepeatedValid;
import validImpl.SubtractedValid;

/**
 * 银河系数字对象
 */
public class NumberCell {
    /**
     * 字符型数字
     */
    private String cellString;
    /**
     * 枚举集合
     */
    private BasicCell[] cells;

    /**
     * 长度
     */
    private int length;

    /**
     * 数值
     */
    private double number;
    /**
     * 最大可表示的数
     */
    private static Integer maxNumber = 2000;

    /**
     * 构造数字对象
     *
     * @param cellString
     */
    public NumberCell(String cellString) throws Exception {
        this.cellString = cellString;
        this.length = cellString.length();
        char[] chars = cellString.toCharArray();
        this.cells = new BasicCell[chars.length];
        for (int i = 0; i < chars.length; i++) {
            BasicCell basicCell = BasicCell.getWithChar(chars[i]);
            if (Objects.isNull(basicCell)) {
                throw new Exception(String.format(" '%s' is a invalid char ", chars[i]));
            }
            cells[i] = basicCell;
        }
        AbstractValid repeatedValid = new RepeatedValid();
        AbstractValid subtractedValid = new SubtractedValid();
        repeatedValid.valid(this);
        subtractedValid.valid(this);
        calculationCredits();
    }

    /**
     * TODO 阿拉伯数字转银河系字符
     *
     * @param number
     * @throws Exception
     */
    /*public NumberCell(int number) throws Exception {
        if (number >= maxNumber) {
            throw new Exception(number + " greater than " + maxNumber);
        }
        if (number <= 0) {
            throw new Exception(number + " less than 0 ");
        }
        this.number = new Integer(number).doubleValue();
        String numberStr = String.valueOf(number);
        numberStr = String.format("%04d", number);

    }*/
    public static void main(String[] args) {
        NumberCell A = null;
        try {
            A = new NumberCell("MMMMCCCXXXIII");
            System.out.println(A.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 计算积分
     */
    private void calculationCredits() {
        if (this.length == 0) {
            this.number = 0;
            return;
        }
        double sum = 0;
        for (int i = 0; i < cells.length; i++) {
            // 顺序累加
            if (i == 0 || cells[i].getValue() <= cells[i - 1].getValue()) {
                sum += cells[i].getValue();
            }
            // 当后的数大于前面的数时，用2倍的前面的数减去后面的数
            else {
                sum += cells[i].getValue() - 2 * cells[i - 1].getValue();
            }
        }
        this.number = sum;
    }

    public String getCellString() {
        return cellString;
    }

    public BasicCell[] getCells() {
        return cells;
    }

    public int getLength() {
        return length;
    }

    public double getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "NumberCell{" +
                "cellString='" + cellString + '\'' +
                ", cells=" + Arrays.toString(cells) +
                ", length=" + length +
                ", number=" + number +
                '}';
    }

}
