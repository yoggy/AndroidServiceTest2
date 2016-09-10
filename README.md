AndroidServiceTest2
====

メモ
  - Serviceの中から他のServiceをbindService()してIBinderを取得することは可能
  - startService()したServiceを含むアプリを「最近使ったアプリ一覧」画面から消した場合、Serviceも同時に終了するが、Servie.onStartCommand()でreturn START_STICKY;していると、そのServiceは再起動される。
  - OS起動時にサービスを起動する場合は次の手順で
    - android.permission.RECEIVE_BOOT_COMPLETEDを追加
    - Intent.ACTION_BOOT_COMPLETEDを受信するBroadcastReceiver.onReceive()を作成
    - onReceive()の中からstartService()する

Copyright and license
----
Copyright (c) 2016 yoggy

Released under the [MIT license](LICENSE.txt)
