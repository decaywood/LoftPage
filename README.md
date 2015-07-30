#LoftPage 2048
LoftPage以当前流行的2048游戏为蓝本，将其进行改造，使其可进行网络对战，增强竞技性，并更有可玩性。
其采用流行的websocket通信技术，作为浏览器与服务器之间通信的媒介，打破原有的请求响应机制，提高
了通信效率，服务器底层进行了较大的优化，包括多消息队列汇聚设计减少并发冲突，单生产者多消费者
线程模型提高吞吐量、函数式编程提高系统整体可拓展性等等。
与其说本项目是一个游戏，把它当作各种最新技术的实践以及设计思想的技术验证更为恰当。
本文着重讨论设计思想和所用技术本身，并不对项目应用以及前景作过多探讨。
##为什么使用Java8：
* 逻辑参数化：通过传递不同的函数解决不同的问题，避免整体框架的改动，复用最大化。
* 并行化优化：Java8 提供了底层由ForkJoin框架支撑的并行流Api，如果有必要，
仅仅需要改动几行代码就能使整个服务器代码逻辑并行化，充分榨取cpu资源.
更重要的是无需维护由于可变变量带来的线程同步问题，函数式风格确保了参数状态的不可变，
减少了代码异味。 
* 迭代隐藏：集合以及流内置foreach迭代方法，避免了向客户端暴露集合的内部结构的丑陋代码。
* 兼容泛型：早在JDK1.5就支持泛型，使用泛型能够在编译期就能发现潜在的类型转换错误。
* Java8的重构：
  * 能用表达式就不用匿名类
  * 能用方法引用就不用表达式
  * 能用Stream流处理数据就不用迭代
* Stream API:
  * Declarative — 语义更明确，更具可读性
  * Composable — 更好的灵活性
  * Parallelizable — 更强大的性能

##效果图(gif文件，加载时间有点长)：
![](https://github.com/decaywood/LoftPage/blob/master/Info/2048.gif)
##后台结构以及说明：
![](https://github.com/decaywood/LoftPage/blob/master/Info/Structure.png)
##前后台交互时序图：
![](https://github.com/decaywood/LoftPage/blob/master/Info/SequenceDiagram.png)
