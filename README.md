<img align="right" src="https://github.com/CatalinaPicco/ShopByMe/blob/master/personal-shop.PNG">
<br clear="right"/>

### Personal Shop
A personal experiment to improve searching products

# First definitions

* Choosing a [Color palette](https://www.materialpalette.com/yellow/light-blue) and a family font (Roboto Sans)

* Making some [UX Research](https://31pia6e0.optimalworkshop.com/optimalsort/a5h60h30-0/sort) with relatives opinions.

* Choosing an Architecture pattern: in this case I chose MVVM with some modifications, I have model clases with the response model data, the Main Activity and its posible fragment options for manage Views (HomeResults, CategoryFragment, Empty States) and ViewModel for the logic. In this case I did not use repositories. I use livedata to listen to changes in querys or categories changes.

* Generic Adapters and Recyclers for re-using and scaling the app: this will facilitate in the future managing other types of search: shops, categories, last visited, and all of them could be organized with these generic tools. Also ads or promotions which could have its own viewHolder and ViewType items.

* Taking care of visuals: the app tries to be the most accurate for users to search products and see details. No extra information or distracting designs.

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
* Managing the hight resolution pictures in search: <br>

Results service brings low quality images for products and similar case ocurs in category endpoint. So I manage to bring those high quality pictures by batches "tandas" of 10 units as the paginations goes. As the service brings the results, I call for the details of the images and an aux result list fills with complete information.

```
 disposable = searchService.searchProducts(searchString, offset, itemsPerPage, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { SearchResponse ->
                    run {
                        if (SearchResponse.results.isEmpty() && query.value?.isNotEmpty()!!){
                            onEmpty?.invoke()
                        } else if (SearchResponse.results.isEmpty()) {
                            onCleaned?.invoke()
                        }
                        SearchResponse.results.forEach {
                            getImage(it.id)
                        }
                        doPaginate(SearchResponse.results)
                    }
                },
                { error -> println("error: " + error.message) }
            )
```

```
  private fun getImage(id: String) {
        disposable = searchService.getDetail(id, "all")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> run{
                    fullImages(result)
                }
                },
                { error -> println("error: " + error.message) }
            )
    }
```

* Finally, use of picasso library "Target" to help with loaders for images. 

```
if (!data.image.isNullOrEmpty()){
            picasso.load(data.image)
                .into(object : Target {
                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            container.visibility = View.GONE
                        }
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            image.setImageBitmap(bitmap)
                            progressBar.visibility = View.GONE
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            
                        }

                    })
        }
```



