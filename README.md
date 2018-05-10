# SearchDir
OOA&D 课程设计目录搜索差异比较程序

## 构成
- code:存放主要的代码
- filter :存放过滤筛选有关的代码
- gui：存放于界面有关的代码
- sort : 存放排序有关的代码

## 设计
- 使用SFile代表文件
- 使用SDirectory代表目录
- 使用FileListManager中的哈希表存储目录下的文件
- 使用Sort接口，并让其他排序方法实现它
- 使用CondictionFilter接口，作为装饰器模式的祖先类。
- 使用模式工厂ModeFactory，并让**日志模式**和**差异模式**继承它
- InfoGetter获取文件目录信息，让其作为被装饰者，可以获得经过过滤条件`Condiction`过滤后的文件信息。

## 使用方法（目前）
- 运行`SearchGui`
- 或者`InfoGetter.main()`
## 运行截图

### 主界面：
![main](/screenshot/main.png)


### 关于界面：
![about](/screenshot/about.png)

### 差异比较：
![compare](/screenshot/compare.png)

## 当前功能
- 给出目录，可以遍历目录并打印排序后的目录结构
- 可以存储目录信息到日志文件
- 可以随时读取已有的日志文件
- 可以进行新旧日志文件的差异比较并输出到`differ`文件中
- 可以根据筛选条件*文件大小*，*文件类型*，*文件最后修改时间*对信息排序后在控制台输出

