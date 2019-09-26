package galaxy;

import java.util.HashMap;

public class NikeNameBuilder extends HashMap {

    /**
     * 根据昵称条件添加昵称
     *
     * @param sentence
     * @return
     */
    public boolean makeFromSentence(String sentence) {
        String[] worlds = sentence.split(" ");
        this.put(worlds[0], BasicCell.getWithString(worlds[2]));
        return true;
    }

    /**
     * 根据昵称获取单位对象
     *
     * @param nikeName
     * @return
     */
    public BasicCell getCellByNikeName(String nikeName) {
        return (BasicCell) this.get(nikeName);
    }
}
