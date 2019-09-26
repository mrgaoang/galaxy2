package galaxy;

import java.util.List;

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
     * 银河积分
     */
    private double credits;

    /**
     * 构造数字对象
     *
     * @param cellString
     */
    public NumberCell(String cellString) {
        this.cellString = cellString;
        this.length = cellString.length();
        char[] chars = cellString.toCharArray();
        cells = new BasicCell[chars.length];
        for (int i = 0; i < chars.length; i++) {
            cells[i] = BasicCell.getWithChar(chars[i]);
        }
        calculationCredits();
    }

    /**
     * 构造数字对象
     * @param basicCells
     */
    public NumberCell(List<BasicCell> basicCells) {
        this.length = basicCells.size();
        this.cells = (BasicCell[]) basicCells.toArray();
        StringBuilder sb = new StringBuilder();
        for (BasicCell basicCell : basicCells) {
            sb.append(basicCell.getName());
        }
        this.cellString = sb.toString();
        calculationCredits();
    }

    /**
     * 计算积分
     */
    private  void calculationCredits() {
        if (this.length == 1) {
            this.credits = cells[0].getValue();
        }
        double sum = 0;
        BasicCell before = cells[0];
        for (int i = 1; i < cells.length; i++) {
            // 前面的数小于后面的数，求差之后累加
            if (before.getValue() < cells[i].getValue()) {
                sum = sum + (cells[i].getValue() - before.getValue());
                // 取下一个数为before 从下下个数开始遍历
                i++;
                before = cells[i];
            }
            // 累计最后一个数
            else if (i == cells.length - 1) {
                sum = sum + cells[i].getValue();
            }
            // 累计上一个数
            else {
                sum = sum + before.getValue();
            }
        }
        this.credits = sum;
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

    public double getCredits() {
        return credits;
    }
}
