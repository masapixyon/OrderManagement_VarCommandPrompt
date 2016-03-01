# OrderManagement_VarCommandPrompt

受注管理システム(コマンドプロンプト版)
本プログラムの構成される機能要素は、検索、登録、削除の3つです。データベースとjava言語を使用してコマンドプロンプトにて実行することができます。なお、Web版とコマンドプロンプト版どちらも開発しており、こちらは後者となります。


* カテゴリ : ツール
* プログラム内容 : 基幹システム(受注管理システム)
* 互換性 : Windrow7,MacOSで動作確認済み 
* 開発環境 : MacOS,Eclipce
* 使用言語 : Java, Mysql


受注管理システムを作成するにあたって、大学教授のカリキュラムに従いながら、DFDやER図などを要件の洗い出しなどを行い、データベースの設計をしました。また、データベースの仕様書を読み取り、データベースの構築をしております。

##Demo

####受注検索
顧客名のキーワードでの検索

検索キーワードに引っかかる全ての顧客を表示
また、受注に関する詳細情報を表示

表示される要素は、受注NO、受注受付日、顧客名(顧客コード)、担当者名(担当者コード)、受注商品一覧(商品コード、商品名、数量)
<img src="https://github.com/masapixyon/OrderManagement_VarCommandPrompt/blob/master/gif/Search.gif" width="700">

####受注登録
登録情報の要素は登録年月日、顧客コード、担当者コード、商品コード、数量
<img src="https://github.com/masapixyon/OrderManagement_VarCommandPrompt/blob/master/gif/Registration.gif" width="700">

####受注削除
受注No.の入力にて削除
<img src="https://github.com/masapixyon/OrderManagement_VarCommandPrompt/blob/master/gif/Deletion.gif" width="700">


##Install
　1.Mysqlとjava環境で実行可能。

　2.データベースは_orderdb_で、これをMysqlのDataファイルにダウンロード

　3.本プログラムをダウンロードして頂き、コマンドプロンプトで実行
