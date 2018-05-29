# SearchDir
Object-Oriented Analysis and design class project for directory searching and difference
comparison.

## Basic Structure
- code: Store main model class's codes and factory class's codes,using `Factory pattern` here.
- filter: Store codes related to condition search ,using `Decorator pattern` here.
- gui：Codes about gui.Here I'm using JavaFX to build the interface
- sort: Store codes related to sorting directory files according to some rules,using `Strategy pattern` here.


## Design
- Use SFile to represent file object
- Use SDirectory to represent directory object
- Use FileListManager's hashTable to store specific directory's files info
- Use Sort interface，and let other sorting method implements it
- Use CondictionFilter interface，as ancient class of other condition filters
- Use ModeFactory，let**LogMode**and**CompareMode**to extend it
- InfoGetter, get directory infos and make itself a decoratee,become capable to retrieve file infos filtered by`Condiction`

## Usage
- Run `SearchGui` for most of the functions with nice interface
- Or `InfoGetter.main()` for only search by some criterias
## Screenshots

### 主界面：
![main](/screenshot/main.png)

### 关于界面：
![about](/screenshot/about.png)

### 差异比较：
![compare](/screenshot/compare.png)

## What can it do?
+ Normal mode:
    - Given a directory path,outputs all directories and files' info on gui.
    - Automatically save above infos into a log file.
    - Be able to choose sorting method through a combobox.
    - Read log files that are already exist through gui.
    - If enter the same path twice into search box to search infos, it will 
    build a `new.txt` log file.
    - Entering different path to search in this mode won't build new log file(or can't update new log)

+ Compare mode:
    - If info of searched dirs have changed on your machine,when in compared mode,
    it will generate a `differ.txt` file to show the old and new structure's difference
    - Make sure to have an old and new log file of same path first!(Otherwise compare will be meaningless)

+ Command line mode(InfoGetter):
    - Accoring to *fileSize*，*fileType*，*fileLastEdit* to search for matches and output in the console.

## Noted
I tried to make `SFile` class integrated with `builder pattern`,but I soon found it unnecessary and remove the code.
