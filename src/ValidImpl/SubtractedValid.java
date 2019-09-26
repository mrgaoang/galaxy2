package ValidImpl;

import galaxy.BasicCell;
import galaxy.NumberCell;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 被减数校验
 * <p>
 * * I 只能做VX的减数
 * X 只能做LC的减数
 * C 只能做DM的减数
 * DLV 不能作为减数
 */
public class SubtractedValid implements AbstractValid {
    /**
     * 减数集合
     */
    public static Map<BasicCell, List<BasicCell>> subtractMap = new HashMap(4);

    static {
        subtractMap.put(BasicCell.I, Arrays.asList(BasicCell.V, BasicCell.X));
        subtractMap.put(BasicCell.X, Arrays.asList(BasicCell.L, BasicCell.C));
        subtractMap.put(BasicCell.C, Arrays.asList(BasicCell.D, BasicCell.M));
    }

    /**
     * 不能作为减数
     */
    public static List<BasicCell> notSubtracted = Arrays.asList(BasicCell.D, BasicCell.L, BasicCell.L);

    @Override
    public void valid(NumberCell input) throws Exception {
        if (input.getLength() <= 1) {
            return;
        }
        int before = 0;
        BasicCell[] cells = input.getCells();
        for (int i = 1; i < input.getLength(); i++) {
            // 前面的数小于后面的数
            if (cells[before].getValue() < cells[i].getValue()) {
                // 不能作为减数的情况
                if (notSubtracted.contains(cells[before])) {
                    throw new Exception(cells[before].getName() + " can not before " + cells[i].getName());
                }
                // M做减数的情况
                List<BasicCell> canBeSubtractedList = subtractMap.get(cells[before]);
                if (canBeSubtractedList == null) {
                    throw new Exception(cells[before].getName() + " can not before " + cells[i].getName());
                }
                // 不符合要求的减数
                if (!canBeSubtractedList.contains(cells[i])) {
                    throw new Exception(cells[before].getName() + " can not before " + cells[i].getName());
                }
            }
            before = i;
        }
    }

    public static void main(String[] args) throws Exception {
        SubtractedValid subtractedValid = new SubtractedValid();
        NumberCell numberCell = new NumberCell("DIV");
        subtractedValid.valid(numberCell);

    }


}
