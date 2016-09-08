AndroidServiceTest2
====

メモ
  - Serviceの中から他のServiceをbindService()してIBinderを取得することは可能
  - startService()したServiceを含むアプリを「最近使ったアプリ一覧」画面から消した場合、Serviceも同時に終了するが、Servie.onStartCommand()でreturn START_STICKY;していると、そのServiceは再起動される。

Copyright and license
----
Copyright (c) 2016 yoggy

Released under the [MIT license](LICENSE.txt)
