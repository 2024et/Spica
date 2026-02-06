# Spica

<p>学生団体向け会計管理Webアプリケーション</p>

![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)

## 目次
- [プロジェクトについて](#プロジェクトについて)
- [企画](#企画)
- [画面遷移図](#画面遷移図)
- [ER図](#er図)
- [ディレクトリ構成](#ディレクトリ構成)
- [ライセンス](#ライセンス)

## プロジェクトについて
<p>大学の課題解決実習(ゼミ)で学んだWebアプリ開発の経験を活かし、個人開発に挑戦したプロジェクトです。</p>

<p>部活動の会計担当として感じた課題を解決するため、学生団体向けの会計管理Webアプリを開発しています。企画から設計、実装、テスト、デプロイまで、実践的な開発フローに沿って実施しています。</p>

### 開発スケジュール
| 時期 | フェーズ | 内容 |
|------|---------|------|
| 2025年11月 | 企画・要件定義 | 企画書、機能要件、画面遷移図、ER図、画面設計書を作成 |
| 2025年12月 | レビュー① | 現役SE 1名によるレビュー |
| 2026年1月 | レビュー② | 担当教授 + 現役SE 1名によるレビュー |
| 2026年2月 | 開発 | 実装作業中 (**現在**) |
| 2026年3月 | テスト | テスト仕様書作成、自己レビュー実施 |
| 2026年4月 | 外部レビュー | 公開サーバーへデプロイ、外部レビュー実施 |


## 企画
<p>会計管理を行う従来の方法としては、Excelやスプレッドシートを活用することができるが、これらの方法においてはいくつかの課題や問題が存在している。</p>

<ol>
  <li><b>支出比較・確認の負担</b></li>
  <p>学生団体の会計においては、「例年と比較して妥当な支出となっているか」といった収支の流れを確認する必要がある。</p>
  <p>特に私が通う大学では、一斉監査の際に過去5年分の会計記録を遡って実施されるため、複数年にわたる支出の比較および確認が特に重要。</p>
  
  <p>しかし、Excelやスプレッドシートを用いた管理では、年度ごとにファイルやシートを分けて管理するケースが多く、複数のウィンドウやタブ、シートを行き来しながら作業する必要が生じます。その結果、必要なデータの収集や集計に時間を要し、負担が大きくなるという課題がある。</p>
  
  <li><b>管理リスク</b></li>
  <p>Excel/スプレッドシートによる管理では、編集権限を持つユーザーが誤って操作したり、複数人で同時に操作を行ったことによって、セルの上書きや意図しない機能が作動するといった可能性がある。</p>
  
  <p>また、これらの変更された内容について、気づく手段がなくそのまま保存してしまうことで管理上の課題が存在する。</p>
  
  <li><b>表計算ソフトスキル</b></li>
  <p>新規に立ち上げられた学生団体などでは、会計管理用のExcelやスプレッドシートを一から作成する必要がある。その際、VLOOKUP、COUNTIF、FILTER などの関数など、作成において一定の表計算ソフトの知識が求められる。</p>
  <p>その結果、会計管理システムの作成に時間を要したり、誤った構造のまま運用が始まってしまい問題が後々に発生してしまうリスクがある。</p>
  </ol>
<p>そのため、上記で述べた課題や問題を解決、解消または軽減するために、部活動・サークル・同好会などの学生団体を対象とした会計管理Webアプリを開発することにした。</p>

## 画面遷移図
<p>2026年2月6日時点</p>
<img width="1321" height="1451" alt="scene" src="https://github.com/user-attachments/assets/7d7d5a34-e3d6-46ce-946d-390b150bddb9" />

## ER図
<p>2026年2月6日時点</p>
<img width="1091" height="1611" alt="er" src="https://github.com/user-attachments/assets/d017fcf8-5ce8-48f0-bf75-471827923a26" />

## ディレクトリ構成
<p>2026年2月6日時点</p>
<pre>
.
│  .classpath
│  .gitignore
│  .project
│  
├─.settings
│      .jsdtscope
│      org.eclipse.core.resources.prefs
│      org.eclipse.jdt.core.prefs
│      org.eclipse.wst.common.component
│      org.eclipse.wst.common.project.facet.core.xml
│      org.eclipse.wst.jsdt.ui.superType.container
│      org.eclipse.wst.jsdt.ui.superType.name
│      
├─build
│  └─classes
│      │  db.properties
│      │  mail.properties
│      │  
│      ├─Beans
│      │      accountBeans.class
│      │      balanceBeans.class
│      │      categoryBeans.class
│      │      projectBeans.class
│      │      
│      ├─Dao
│      │      accountDao.class
│      │      categoryDao.class
│      │      DBUtil.class
│      │      financialDao.class
│      │      logDao.class
│      │      organizationsDao.class
│      │      projectDao.class
│      │      transactionDao.class
│      │      
│      ├─Logic
│      │      change_passwordLogic.class
│      │      confirmLogic.class
│      │      financialLogic.class
│      │      MailUtil$1.class
│      │      MailUtil.class
│      │      requestLogic.class
│      │      select_groupLogic.class
│      │      signinLogic.class
│      │      signupLogic.class
│      │      
│      └─Servlet
│              change_passwordServlet.class
│              confirmServlet.class
│              financialServlet.class
│              requestServlet.class
│              select_groupServlet.class
│              signinServlet.class
│              signupServlet.class
│              
└─src
    └─main
        ├─java
        │  ├─Beans
        │  │      accountBeans.java
        │  │      balanceBeans.java
        │  │      categoryBeans.java
        │  │      projectBeans.java
        │  │      
        │  ├─Dao
        │  │      accountDao.java
        │  │      categoryDao.java
        │  │      DBUtil.java
        │  │      financialDao.java
        │  │      logDao.java
        │  │      organizationsDao.java
        │  │      projectDao.java
        │  │      transactionDao.java
        │  │      
        │  ├─Logic
        │  │      change_passwordLogic.java
        │  │      confirmLogic.java
        │  │      financialLogic.java
        │  │      MailUtil.java
        │  │      requestLogic.java
        │  │      select_groupLogic.java
        │  │      signinLogic.java
        │  │      signupLogic.java
        │  │      
        │  └─Servlet
        │          change_passwordServlet.java
        │          confirmServlet.java
        │          financialServlet.java
        │          requestServlet.java
        │          select_groupServlet.java
        │          signinServlet.java
        │          signupServlet.java
        │          
        ├─resources
        │      db.properties
        │      mail.properties
        │      
        └─webapp
            │  change_password.jsp
            │  financial.jsp
            │  header.jsp
            │  request.jsp
            │  select_group.jsp
            │  signin.jsp
            │  signup.jsp
            │  
            ├─css
            │      change_password.css
            │      financial.css
            │      header.css
            │      request.css
            │      select_group.css
            │      signin.css
            │      signup.css
            │      
            ├─META-INF
            │      MANIFEST.MF
            │      
            └─WEB-INF
                └─lib
                        jakarta.activation-1.2.1.jar
                        jakarta.mail-1.6.7.jar
                        jakarta.servlet.jsp.jstl-3.0.1.jar
                        jakarta.servlet.jsp.jstl-api-3.0.0.jar
                        mysql-connector-j-8.4.0.jar
</pre>

## ライセンス
MIT License
このプロジェクトは個人開発プロジェクトです。

©2026 EBATA TAKUMI
