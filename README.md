# JavaImageSave

このプログラムはjavaでgoogle画像検索とほぼ同等の結果を得られるso-net画像検索の結果を自動で保存できます。

This program can automatically save the results of so-net image search, which gives almost the same results as google image search in java.

## 使い方　how to use

```bash
java -jar JavaImageSave.jar -t search_text -l count_of_search(default 5) -p path_to_save
```

```bash
java -jar JavaImageSave.jar -t search_text -o search_option
```

自動で検索を開始し、全て96dpiのjpgに変換し、横長で保存します。

Auto start search and auto convert 96 dpi jpeg image. If get image is height > width , rotate it 90°.

## オプション　option

-l オプションで検索する回数を指定します。

一回の検索で20枚画像を取得します。

-l option is search count

one count 20 image 

-t オプションで検索する文字列を指定します。スペースが入る場合引用符で囲ってください。

-t option is search word. If it word has space,please use double quotation.

-p オプションで保存先パスを指定します。

-p option is set save path.

-h オプションはヘルプです。ヘルプが表示されます。

-h option is help. print help message.

-o オプションは検索時に指定するオプションを指定します。下のようになります。

https://www.so-net.ne.jp/search/image/?count=20&query= (-t 検索する文字) (-o オプションで指定した文字)&start=(検索回数*20)

-o option is set option for search engin.

https://www.so-net.ne.jp/search/image/?count=20&query= (-t option word) (-o option strings)&start=(count of search*20)

例)tlanguage,ysp_q,size,end,imtype,format,ss_view,from,q_type,view,adult,start,size_all

ex) tlanguage,ysp_q,size,end,imtype,format,ss_view,from,q_type,view,adult,start,size_all

## -o オプションを使用した検索　Search using the -o option

### 検索時の拡張子の指定　Specifying the extension when searching

検索時に拡張子を(jpeg,gif,pngから)指定することができます。

You can specify the extension when searching.(only jpeg,gif,png)

拡張子の指定は検索時であって保存時には全てjpgに変換されます。

The extension is specified at the time of search and is converted to jpeg at the time of saving.

```bash
java -jar JavaSaveImage.jar -t text -o "&format=png"
```

### 大人な画像の取得　get adult image

大人な画像の取得もできます。

You can also get adult images.

```bash
java -jar JavaSaveImage.jar -t text -o "&adult=on"
```

### サイズの指定　set image size

このオプションは複数回指定も可能です。ただしsize_allオプションを付ける必要があります。

Can be specified multiple times, but use size_all option.

```bash
java -jar JavaSaveImage.jar -t text -o "&size=large"
```
```bash
java -jar JavaSaveImage.jar -t text -o "&size=small"
```

```bash
java -jar JavaSaveImage.jar -t text -o "&size=medium"
```

```bash
java -jar JavaSaveImage.jar -t text -o "&size_all=parts&size=small&size=medium"
```



### 検索の仕方　set search type

```bash
java -jar JavaSaveImage.jar -t text -o "&q_type=and_w"
```

```bash
java -jar JavaSaveImage.jar -t text -o "&q_type=or_w"
```

```bash
java -jar JavaSaveImage.jar -t text -o "&q_type=ph_w"
```

### 画像の色　image color

```bash
java -jar JavaSaveImage.jar -t text -o "&imtype=gray"
```

```bash
java -jar JavaSaveImage.jar -t text -o "&imtype=color"
```

