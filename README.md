# scala-lambda-layer

### Requirements
* scala: 2.13.3
* aws-sdk: 2.13.70

### Layerを作成する方法
1. java/libディレクトリを作成する
2. java/lib配下に[sbt assemblyAll]で作成した.jarファイルを置く
3. javaディレクトリをzipにする

```
cd ~/GitHub
zip -r java ./java **
```


### 参考情報
fat jar
https://www.greptips.com/posts/907/

JavaでLambdaLayerを使う
https://qiita.com/kakuka3594/items/03b6b97478e8a29b97a4
