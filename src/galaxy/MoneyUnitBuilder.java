package galaxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 货币种类价值构造
 */
public class MoneyUnitBuilder extends HashMap<String, Double> {
    public static String accessChar = "is";

    private NikeNameBuilder nikeNameBuilder;

    public NikeNameBuilder getNikeNameBuilder() {
        return nikeNameBuilder;
    }

    public MoneyUnitBuilder(NikeNameBuilder nikeNameBuilder) {
        super();
        this.nikeNameBuilder = nikeNameBuilder;
    }

    /**
     * glob glob Silver is 34 Credits
     * glob prok Gold is 57800 Credits
     * pish pish Iron is 3910 Credits
     *
     * @param sentence
     */
    public void setUnitWithSentence(String sentence) {
        String[] words = sentence.split(" ");
        int charIsIndex = 0;
        List<BasicCell> cells = new ArrayList<>();
        String unit = null;
        double price = 0;
        for (int i = 0; i < words.length; i++) {
            // 取昵称 读取到输入的数值
            if (charIsIndex == 0 && nikeNameBuilder.containsKey(words[i])) {
                cells.add(nikeNameBuilder.getCellByNikeName(words[i]));
            }
            // 取到货币种类
            else if (charIsIndex == 0 && !nikeNameBuilder.containsKey(words[i])) {
                unit = words[i];
            }
            // 区分符 "is"
            else if (accessChar.equals(words[i])) {
                charIsIndex = i;
            } else if (charIsIndex != 0 && charIsIndex + 1 == i) {
                price = Double.parseDouble(words[i]);
            }
        }
        NumberCell numberCell = new NumberCell(cells);
        this.put(unit, price);
    }
}
