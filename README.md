# MyBookApp
自己做的一个图书扫描显示的app,使用豆瓣api把实验所有的书都详情都显示出来。
##效果图
![book](https://github.com/reallin/MyBookApp/blob/master/book1.png)
![book](https://github.com/reallin/MyBookApp/blob/master/book2.png)
##使用到的技术点
###google新控件的使用
  * toolbar
  * CoordinatorLayout
  * CoordinatorLayout
  * RecyclerView
  * FloatingActionButton等。

###豆瓣api的使用
  没什么说的，就是导入。
###gson解析数据
  google亲生的方法，解析json相当简洁。本项目利用它进行结构复杂的json的读取，想了解的可以看下源码。
### picasso进行图片的加载
  自带图片的二次缓存，代码也相当简洁。
###buffernife框架的注入
  加入它可以省略了绑定控件的代码。android studio加入它也很方便，只需导入包并下载相关插件。
###二维码扫描
  扫描书本条形码，自动识别图书信息。
###ActiveAndroid
使用ActiveAndroid操作Sqlite数据库，存储图书信息，方便快捷。
