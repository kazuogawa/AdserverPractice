
console.log("hello javascript");
//TODO:動的にwidget_idを静的に自動生成する仕組みを作成する
var widget_id = 1

//Jsonpを取得する処理 https://widget_1.js
//"callback({"slots":[
//{
//    slot:1,
//        title:"タイトル名",
//    creative_url:"画像のurl",
//    link_url:""押した先のurl""
//},
//{
//    slot:2,
//        title:""タイトル名"",
//    creative_url:""画像のurl"",
//    link_url:""押した先のurl""
//},・・・
//]
//})"
//下記はサンプルデータ
var title1 = "タイトル1";
var creative_url1 = "https://placehold.jp/150x150.png?text={タイトル1}";
var link_url1 = "https://google.com";
//<a href="'+ link_url1 +'">
//<div><img src="creative_url1"><p>title1</p>
//</div></a>';
//TODO:変数名を整理
var widget_child_div_child_img = document.createElement('img');
widget_child_div_child_img.src = creative_url1;
var widget_child_div_child_p_text = document.createElement(title1);
var widget_child_div_child_p = document.createElement('p');
widget_child_div_child_p.appendChild(widget_child_div_child_p_text);
var widget_child_div = document.createElement('div');
widget_child_div.appendChild(widget_child_div_child_img);
widget_child_div.appendChild(widget_child_div_child_p);
var widget_a = document.createElement('a');
widget_a.href = link_url1;
widget_a.appendChild(widget_child_div);
var recommend_widget_div = document.getElementById('recommend_widget');
recommend_widget_div.appendChild(widget_a);