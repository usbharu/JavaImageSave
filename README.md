# JavaImageSave
[english](README_EN.md)


このプログラムはjavaでgoogle画像検索とほぼ同等の結果を得られるso-net画像検索の結果を自動で保存できます。

## 使い方　how to use

```bash
java -jar JavaImageSave.jar -t search_text -l count_of_search(default 5) -p path_to_save
```

```bash
java -jar JavaImageSave.jar -t search_text -o search_option
```

```bash
java -jar JavaImageSave.jar -t search_text -r 90
```

自動で検索を開始し、全て96dpiのjpgに変換し、横長で保存します。

## オプション

-l オプションで検索する回数を指定します。

一回の検索で20枚画像を取得します。

-t オプションで検索する文字列を指定します。スペースが入る場合引用符で囲ってください。

-p オプションで保存先パスを指定します。

-h オプションはヘルプです。ヘルプが表示されます。

-o オプションは検索時に指定するオプションを指定します。下のようになります。

-s オプションで検索結果の表示のみにすることができます。

-e オプションで保存時の拡張子を(png,jpg,jpeg)から指定できます。

-r オプションで回転方向を指定できます。このオプションを指定するだけで自動回転がなくなります。

https://www.so-net.ne.jp/search/image/?count=20&query= (-t 検索する文字) (-o オプションで指定した文字)&start=(検索回数*20)

例)tlanguage,ysp_q,size,end,imtype,format,ss_view,from,q_type,view,adult,start,size_all

## -o オプションを使用した検索

### 検索時の拡張子の指定

検索時に拡張子を(jpeg,gif,pngから)指定することができます。

拡張子の指定は検索時であって保存時には全てjpgに変換されます。

```bash
java -jar JavaSaveImage.jar -t text -o "&format=png"
```

### 大人な画像の取得

大人な画像の取得もできます。

```bash
java -jar JavaSaveImage.jar -t text -o "&adult=on"
```

### サイズの指定

このオプションは複数回指定も可能です。ただしsize_allオプションを付ける必要があります。

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



### 検索の仕方

```bash
java -jar JavaSaveImage.jar -t text -o "&q_type=and_w"
```

```bash
java -jar JavaSaveImage.jar -t text -o "&q_type=or_w"
```

```bash
java -jar JavaSaveImage.jar -t text -o "&q_type=ph_w"
```

### 画像の色

```bash
java -jar JavaSaveImage.jar -t text -o "&imtype=gray"
```

```bash
java -jar JavaSaveImage.jar -t text -o "&imtype=color"
```

