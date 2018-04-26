# SearchDir
OOA&D 课程设计目录搜索差异比较程序

## 构成
- code:存放主要的代码
- gui：存放于界面有关的代码

## 设计
- 使用SFile代表文件
- 使用SDirectory代表目录
- 使用FileListManager中的哈希表存储目录下的文件
- 使用Sort接口，并让其他排序方法实现它
- 使用模式工厂ModeFactory，并让**日志模式**和**差异模式**继承它

## 使用方法（目前）
- 运行`SearchGui`

## 运行截图

主界面：
![main](/screenshot/main.png)

关于界面：
![about](/screenshot/about.png)

差异比较：
![compare](/screenshot/compare.png)

## 当前功能
- 给出目录，可以遍历目录并打印排序后的目录结构
- 可以存储目录信息到日志文件
- 可以随时读取已有的日志文件
- 可以进行新旧日志文件的差异比较并输出到`differ`文件中

## TODO
+ ~~完善界面的功能，使其完全~~
+ ~~重新审视SearchPanel的功能，让其结构更好~~
+ 打包程序并且生成可执行的exe文件