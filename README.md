<img align="right" src="https://github.com/CatalinaPicco/ShopByMe/blob/master/personal-shop.PNG">
<br clear="right"/>

### Personal Shop
A personal experiment to improve searching products

# First definitions

[Color palette](https://www.materialpalette.com/yellow/light-blue)

[Architecture](www.google.com)

[UX Research](https://31pia6e0.optimalworkshop.com/optimalsort/a5h60h30-0/sort)

# Installing project

```console
foo@bar:~$ git clone https://github.com/CatalinaPicco/ShopByMe.git
foo
```
Open project in Android Studio and sync files

# Features

* Managing RxJava’s Disposables: <br>
I decided to try using disposables for making this app more resistent to the intense use of searchService. I've choosen these "disposable" objects because they are short lived and they are meant to be discarded after use.

* OnScrollListerner to manage pagination of results and use of EndlessRecyclerViewScrollListener class: <br><br>
**HomeResultsFragment.kt**
```
       productRecycler.recyclerview.apply {
            addOnScrollListener(object: EndlessRecyclerViewScrollListener(layoutManager as StaggeredGridLayoutManager){
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    viewModel?.loadMore()
                }
            })
        }
        
```
* ¿How to make search experience easier and take less vertical space? They are two toolbars which alternate depending if the user needs to search or to use toolbar items.<br><br>

**activity_main.xml**
```
    <include
        android:id="@+id/app_bar"
        layout="@layout/toolbar"
        app:layout_constraintBottom_toTopOf="@+id/frame_layout"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <include
        layout="@layout/search_toolbar"
        android:visibility="gone"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

**MainActivity.kt**

```
 item_search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    // Do something when collapsed
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(R.id.searchtoolbar, 1, true, false)
                    } else
                        searchtoolbar.visibility = View.GONE
                    return true
                }
            })

```

