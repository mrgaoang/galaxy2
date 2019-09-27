package ValidImpl;

import galaxy.BasicCell;
import galaxy.NumberCell;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符重复性校验类
 */
public class RepeatedValid implements AbstractValid {

    /**
     * 不能重复超过三次
     */
    public static int maxRepeatedCount = 3;
    /**
     * 不得重复的字符
     */
    public static List<BasicCell> disableRepeated = Arrays.asList(BasicCell.D, BasicCell.L, BasicCell.V);

    /**
     * 不能重复超过次
     * DLV不能重复
     *
     * @param input
     * @return
     */
    @Override
    public void valid(NumberCell input) throws Exception {
        if (input.getLength() <= 1) {
            return;
        }
        BasicCell[] cells = input.getCells();
        // 上一个单元
        BasicCell beforeCell = cells[0];
        // 重复计数
        int repeatedCount = 1;

        for (int i = 1; i < cells.length; i++) {
            // 上一个与当前不同
            if (!cells[i].equals(beforeCell)) {
                repeatedCount = 1;
                beforeCell = cells[i];
                continue;
            }
            // 重复次数+1
            repeatedCount++;
            if (repeatedCount >= 2 && disableRepeated.contains(beforeCell)) {
                throw new Exception("[" + beforeCell.getName() + "] can not repeated ");
            }
            if (repeatedCount > maxRepeatedCount) {
                throw new Exception("[" + beforeCell.getName() + "] can not repeated more than " + maxRepeatedCount);
            }

        }
    }

    public static void main(String[] args) throws Exception {
        RepeatedValid valid = new RepeatedValid();
        NumberCell numberCell = new NumberCell("IV");
    }

}
