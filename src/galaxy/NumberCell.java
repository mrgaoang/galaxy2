package galaxy;

import java.util.Arrays;

import ValidImpl.AbstractValid;
import ValidImpl.RepeatedValid;
import ValidImpl.SubtractedValid;

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
     * 构造数字对象
     *
     * @param cellString
     */
    public NumberCell(String cellString) throws Exception {
        this.cellString = cellString;
        this.length = cellString.length();
        char[] chars = cellString.toCharArray();
        cells = new BasicCell[chars.length];
        for (int i = 0; i < chars.length; i++) {
            cells[i] = BasicCell.getWithChar(chars[i]);
        }
        AbstractValid repeatedValid = new RepeatedValid();
        AbstractValid subtractedValid = new SubtractedValid();
        repeatedValid.valid(this);
        subtractedValid.valid(this);
        calculationCredits();
    }

    /**
     * 计算积分
     */
    private void calculationCredits() {
        if (this.length == 1) {
            this.number = cells[0].getValue();
        }

        BasicCell before = cells[0];
        double sum = 0;
        for (int i = 1; i < cells.length; i++) {
            // 前面的数小于后面的数，求差之后累加
            if (before.getValue() < cells[i].getValue()) {
                sum = sum + (cells[i].getValue() - before.getValue());
                // 取下一个数为before 从下下个数开始遍历
                i++;
                if (cells.length <= i) {
                    break;
                }
                before = cells[i];
            }
            // 累计上一个数
            else if (i != cells.length - 1) {
                sum = sum + before.getValue();
            } else {
                // 累计最后一个数
                sum = sum + cells[i].getValue() + cells[i].getValue();
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
