<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/30
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script type="text/javascript" src="/js/jquery-3.4.1.min.js"></script>

<%--JS测试--%>
<script type="text/javascript">
    //让页面加载完成之后执行
    $(function() {

        //1.$.get  2.$.post  3.$.getJSON 只能获取json串  4.$.ajax 万能用法
        //1.语法  url地址,  data参数 , 回调函数    返回值类型
        //type类型:xml, html, script, json, text, _default 会自己解析返回值。
        //jQuery.get(url, [data], [callback], [type])   一般都是json串
        $.get("/findAjax2", function(data) {

            //1.获取返回值信息,之后循环遍历,之后将每个数据获取之后 拼接到table中即可
            //关于ajax参数问题  第一个参数 代表下标 ,第二个参数代表遍历的对象
            var trs = null;
            $(data).each(
                function(index, user) { //[user,user,user....]
                    //var user = data[index];
                    var id = user.id; //从对象中获取属性信息
                    var name = user.name;
                    var age = user.age;
                    var sex = user.sex;
                    trs += "<tr align='center'><td>" + id + "</td><td>"
                        + name + "</td><td>" + age + "</td><td>" + sex
                        + "</td></tr>";
                });
            //将tr标签追加到table中
            $("#tb1").append(trs);
        });

        //2.利用$.ajax方法发起ajax请求
        $.ajax({
            type : "get",   		//请求类型
            url : "/findAjax", 		//请求路径
            dataType: "json",       //指定返回值格式为json串
            //data : "name=John&location=Boston",	//请求参数
            async: false ,  //表示同步和异步问题.  默认条件下 是异步操作
            cache: false ,   //添加请求缓存
            success : function(data) {			    //回调函数
                $(data).each((index,user) => {
                    addrows(user);
                });
            },
            error : function(data){

                alert("请求失败!!!")
            }
        });

        //${user.id}  el表达式  所以取值为null
        function addrows(user){
            var tr = "<tr align='center'><td>"+user.id+"</td><td>"+user.name+"</td><td>"+user.age+"</td><td>"+user.sex+"</td></tr>";
            $("#tb1").append(tr);
        }

    });
</script>

<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
