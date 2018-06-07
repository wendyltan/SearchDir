# SearchDir
Object-Oriented Analysis and design class project for directory searching and difference comparison.

## Basic Structure
- code: Store main model classes's codes and factory classes's codes,using `Factory pattern` here.
- filter: Store codes related to condition search ,using `Decorator pattern` here.
- gui：Codes about gui.Here I'm using JavaFX to build the interface
- sort: Store codes related to sorting directory files according to some rules,using `Strategy pattern` here.


## Design
- Use `SFile` to represent file object
- Use `SDirectory` to represent directory object
- Use `FileListManager`'s hashTable to store specific directory's files info,use `Singleton pattern` to avoid unneeded instance of this manager class.
- Use `Sort` interface，and let other sorting method implements it
- Use `CondictionFilter` interface，as ancient class of other condition filters
- Use `ModeFactory`，let **LogMode** and **CompareMode** to extend it
- `InfoGetter`, get directory infos and make itself a decoratee,become capable to retrieve file infos filtered by`Condiction`

## Usage
- Run `SearchGui` for most of the functions with nice interface
- Or `InfoGetter.main()` for only search by some criterias
## Screenshots

## Running screenshots

### Basic：

- Before Search

![before](/screenshot/beforesearch.png)

In log mode,you can search directories for result,but you can't use compare button.

- First Search

![first](/screenshot/searchfirst.png)

The output will show in left window.Of course you can choose whichever side of window you want by toggling the combobox "left/right"

- Second Search

![second](/screenshot/changedirandsearch.png)

Now that that directory you search on your disk has changed,it will show new infos at right window at default.

Till now,the program has automatically write info into logs.Now switch to compare mode:

![compare](/screenshot/comparemode.png)

Then click compare button on main screen,then you might see something like this:

![compare_result](/screenshot/compare_result1.png)

![compare_result](/screenshot/compare_result2.png)

Then we know some files were removed from original place.

*If there is any new file added in,you can see the changes too in `differ` log*

### Extra and command mode

You can use other sort method while searching for directories infos:

![sort_method](/screenshot/useothersort.png)

And you can search for some files with some condition under command line mode:

![command1](/screenshot/searchbycriteria.png)

![command2](/screenshot/searchbycriteria2.png)

### About：
![about](/screenshot/about.png)


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
    - According to *fileSize*，*fileType*，*fileLastEdit* to search for matches and output in the console.

## Noted
I tried to make `SFile` class integrated with `builder pattern`,but I soon found it unnecessary and remove the code.

If you want to see more about how design pattern is implemented in Java,check out my repo here: [JavaDPImpl](https://github.com/wendyltan/JavaDPImpl)
