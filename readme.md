# 银河系货币转换工具
> 在最近一次金融危机使地球上99.99％的人口拥有0.01％的财富后，您决定放弃地球。幸运的是，由于您的帐户中只剩下很少的钱，因此您有能力负担一艘宇宙飞船的租金，离开地球并在整个银河系中飞行以出售常见的金属和尘土（显然很有价值）。 
 在银河系上买卖需要您转换数字和单位，因此您决定编写程序来帮助您。
 星际交易中使用的数字遵循与罗马数字相似的约定，因此您辛苦地收集了它们之间的适当转换。  
 罗马数字基于七个符号
### 每个符号代表对于的数值
+ 符号     数值
+ I       1
+ V       5
+ X       10
+ L       50
+ C       100
+ D       500
+ M       1000

> 符合组合在一起通过加减运算形成数字 如：MMVI 为 1000 + 1000 + 5 + 1 =2006，正常情况下符号又大到小排列，如果较小的符号在较大的符号前，则用后面较大的符号减去前面较小的符号，如：MCMXLIV = 1000 +（1000 − 100）+（50 − 10）+（5 − 1）= 1944；
### 符合组合的规则：
1. 重复条件 符号“ I”，“ X”，“ C”和“ M”最多可以连续重复三次，（如果第三个和第四个之间用较小的值隔开，例如XXXIX，它们可能会出现四次，这种是允许的。） DLV永远不能重复（因为L没有必要重复，可以直接用C表示;D也没有必要重复，可以用M直接表示，V没有必要重复，可以用X表示）
2. 减数条件 I 只能做VX的减数， X 只能做LC的减数（XL、XC是允许的，XD不允许），C 只能做DM的减数（CD、CM是允许的，LD不允许），DLV 不能作为减数（如VX不允许，DM不允许、LC不允许）
3. 单一运算 任何大数值符号都只能减去一个小数值符号
4. 阿拉伯数字写成的数可以分解为符号。例如，1903由1、9、0和3组成。要写罗马数字，每个非零数字应分开对待。在上面的示例中，1,000 = M，900 = CM，3 = III。因此，1903 = MCMIII。

### 输入的句子有5种情况
1. 昵称定义： glob is I ，定义罗马符号I的昵称为glob，此后遇见glob则等价于I
2. 货币定义： glob glob Silver is 34 Credits，定义货币的积分价值， glob glob = 2 ，Silver 为银币， 34 Credits 为34 积分  ，得出 1银币=17积分；可以定义多种类型的货币，如Silver、Gold、Iron 
3. 符号转积分提问 ：how much is pish tegj glob glob ?  pish tegj glob glob = 50 -10 + 1 +1 = 42 
4. 货币积分价值提问:how many Credits is glob prok Silver ?  4-1 银币等0于多少积分，又前面的定义得知银币价值17积分，所以3*17 = 68 积分
5. 干扰句： how much wood could a woodchuck chuck if a woodchuck could chuck wood ?

### 输入样例Test input:
```
glob is I
prok is V
pish is X
tegj is L
glob glob Silver is 34 Credits
glob prok Gold is 57800 Credits
pish pish Iron is 3910 Credits
how much is pish tegj glob glob ?
how many Credits is glob prok Silver ?
how many Credits is glob prok Gold ?
how many Credits is glob prok Iron ?
how much wood could a woodchuck chuck if a woodchuck could chuck wood ? 
```
### Test Output:
```
pish tegj glob glob is 42
glob prok Silver is 68 Credits
glob prok Gold is 57800 Credits
glob prok Iron is 782 Credits
I have no idea what you are talking about 
```

## 实现思路
### MainProcess类为程序入口，接受用户输入的文本并交给SentenceSelect对象处理，SentenceSelect对象调用解析器分析语义，将4种输入进行分类，并提取出问题句型中的数值符号，并交给以下四种对象解析器执行
### 1. NikeNameBuilder 昵称构造器
    1. 通过isNikeNameSentence方法接受一文本，判断是否为昵称定义语句并提取关键词封装为SentenceModel对象
    2. 通过makeFromSentence接受一个SentenceModel对象设置昵称和罗马符号的转换
    
### 2. MoneyUnitBuilder 货币构造器 
    1. 通过isMoneyUnitSentence方法接受一文本，判断是否为货币定义语句并提取关键词封装为SentenceModel对象
    2. 通过setUnitWithSentence 接受一个SentenceModel对象，根据文本内容构造NumberCell对象转换为数值，最终计算出货币等于多少积分，然后保存货币积分映射
### 3. SimpleCreditQuestionBuilder 简单信用分问题构造器，该类继承自AbstractQuestion，有两个共有方法：isMyTypeSentence、answer
    1. isMyTypeSentence 判断是否为简单信用分问题，并提取数字关键词，如：`how much is pish tegj glob glob ?` 提取出 `pish tegj glob glob` 并封装为SentenceModel对象
    2. answer 根据SentenceModel对象解析数字对象并回答
### 4. UnitCreditQuestionBuilder 货币积分价值提问，该类也继承自AbstractQuestion，有两个共有方法：isMyTypeSentence、answer
    1. isMyTypeSentence 货币积分价值提问，并提取数字关键词，如 `how many Credits is glob prok Silver ?` 提取出 数字：`glob prok` 、货币 `Silver` 封装为SentenceModel对象
    2. answer 根据SentenceModel对象创建NumberCell对象，通过MoneyUnitBuilder获取对应货币的积分价值，计算出答案

#### BasicCell 基本符号对象
> BasicCell是一个是罗马符号对应阿拉伯数字的映射的枚举类，有name、和value两个属性
并提供getWithString、getWithChar两个查找方法
### NumberCell 符号数值对象
>NumberCell对象时BasicCell对象的一个顺序集合，用来表示一个完整数值
通过罗马符号字符串构造，如传入`IV`得到可得到阿拉伯数值
构造时调用AbstractValid检查器的所有子类，判断传入的字符是否合法
### AbstractValid 数字语法检查器抽象类 提供一个valid方法用于检查合法性
    1. RepeatedValid符号重复性检查器，继承自RepeatedValid类。该类负责检查符号是否有不合法的重复，如重复超过三次，或连续禁止重复的符号
    
    2. SubtractedValid减法检查器，继承自RepeatedValid类。 检查符号的排列是否满足规定的要求，如I只能做VX的减数
### SentenceModel对象
+ sentenceType 语句类型 1、昵称定义语句 2、货币积分定义语句 3、简单信用分问题 4、货币积分价值问题
+ nikeNameString 昵称组成的文本型数值 如 `glob prok`
+ cellString 罗马字符 如 `II`
+ credits 积分 
+ moneyUnit 货币单位 如 `Silver` 银币
### TestProcess 测试类入口
