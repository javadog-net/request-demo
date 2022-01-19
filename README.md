#### 前言
SpringBoot**前后端接口对接**工作时，经常遇到请求500，400等问题，马虎大意经常导致时间浪费，为此总结了**4个常见的复杂请求类型**，以此为戒。
***

#### 开始
##### 1.实体嵌套List提交
**🌰例子**：提交一个老师的实体Teacher，老师管理着不同的学生，还要传入学生实体List &lt;Student&gt;
**❌常见问题**：提交异常,会报400错误
**✔️正确前端代码**
```html
<button onclick="f1();" class="btn btn-primary">f1提交测试</button>
```
```js
<script src="https://code.jquery.com/jquery.js"></script>
<script>
  const students = [{
        name: "王小明",
        age: "15",
    },{
        name: "李小红",
        age: "15",
    }]
    const teacher = {
        name: "张老师",
        age: "40",
        students:students
    }
    // 实体嵌套List提交
    function f1(){
        // 关键逻辑，需要转化成JSON字符串
        let data = JSON.stringify(teacher)
        $.ajax({
            type : 'post',
            url : '/f1',
            data : data,
            contentType:'application/json',
            success : function(resData) {
                console.log("f1调用成功" + resData)
                alert("f1调用成功" + resData);
            },
        });
    }
</script>
```
**✔️正确后台接收**
```java
	/**
     * @Description 实体嵌套List提交
     * @param teacher
     * @Return java.lang.String
     */
    @PostMapping("/f1")
    @ResponseBody
    public String f1(@RequestBody Teacher teacher){
        return teacher.toString();
    }
```
***
##### 2.普通文件上传
**🌰例子**：普通input 文件上传通过onchange事件进行数据组装后，提交后台
**❌常见问题**：提交后,后台接收到的MultipartFile为空
**✔️正确前端代码**
```html
<input type="file" name="f2" id="f2" onchange="f2()" multiple="multiple"></input>
```
```js
<script src="https://code.jquery.com/jquery.js"></script>
<script>
	// 文件上传
	function f2(){
	    let file = event.target.files[0];
	    let formData = new FormData();
	    formData.append('file', file);
	    $.ajax({
	        url: '/f2',
	        type: 'post',
	        contentType: false,  // ContentType设置成false
	        processData: false, // 不需要让浏览器对FormData进行转换
	        data: formData
	    }).done(function (resData) {
	        console.log("f2调用成功:" + resData)
	        alert("f2调用成功：" + resData)
	    })
	}
</script>
```
**✔️正确后台接收**
```java
	/**
     * @Description 文件上传
     * @param file
     * @Return java.lang.String
     */
    @PostMapping("/f2")
    @ResponseBody
    public String f2(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        return fileName;
    }
```
***
##### 3.List提交
**🌰例子**：提交时，传给后台一个List
**❌常见问题**：提交异常,会报400错误
**✔️正确前端代码**
```html
 <button onclick="f3();" class="btn btn-primary">f3提交测试</button>
```
```js
<script src="https://code.jquery.com/jquery.js"></script>
<script>
	const teachers = [{
        name: "张老师",
        age: "40"
    },{
        name: "李老师",
        age: "35"
    }]
    // 直接提交List
    function f3(){
        // 关键逻辑，需要转化成JSON字符串
        let data = JSON.stringify(teachers)
        $.ajax({
            type : 'post',
            url : '/f3',
            data : data,
            contentType:'application/json',
            success : function(resData) {
                console.log("f3调用成功" + resData)
                alert("f3调用成功" + resData);
            },
        });
    }
</script>
```
**✔️正确后台接收**
```java
	/**
     * @Description List实体上传
     * @param teachers
     * @Return java.lang.String
     */
    @PostMapping("/f3")
    @ResponseBody
    public String f3(@RequestBody List<Teacher> teachers) {
        return teachers.toString();
    }
```
***
##### 4.数组Array提交
**例子🌰**：提交时，传给后台一个Array
**❌常见问题**：提交异常,会报500错误
**✔️正确前端代码**
```html
<button onclick="f4();" class="btn btn-primary">提交f4测试</button>
```
```js
<script src="https://code.jquery.com/jquery.js"></script>
<script>
const array = [1,2,3,4]
// 直接提交数组
function f4(){
    $.ajax({
        type : 'post',
        url : '/f4',
        data : {
            array:array
        },
        dataType: 'json',
        success : function(resData) {
            console.log("f4调用成功" + resData)
            alert("f4调用成功" + resData);
        },
    });
}
</script>
```
**✔️正确后台接收**
```java
	/**
     * @Description 数组上传
     * @param array
     * @Return java.lang.String
     */
    @PostMapping("/f4")
    @ResponseBody
    public int[] f4(@RequestParam(value = "array[]")int[] array) {
        return array;
    }
```
***
#### 可视化代码
![在这里插入图片描述](https://img-blog.csdnimg.cn/4061388f01204e4eafdbf6e339188a92.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBASmF2YURvZ-eoi-W6j-eLlw==,size_20,color_FFFFFF,t_70,g_se,x_16)
##### 前端
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>请求列表</title>
    <link rel="shortcut icon" href="/static/favicon.ico" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <table class="table">
        <caption style="text-align: center;"> <h2>SpringBoot常见出错请求</h2></caption>
        <thead>
        <tr>
            <th style="min-width: 40px">No</th>
            <th style="min-width: 140px">类型</th>
            <th style="min-width: 180px">描述</th>
            <th style="min-width: 100px">常见问题</th>
            <th>参数Data</th>
            <th>请求代码</th>
            <th>后端代码</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr class="active">
            <td>f1</td>
            <td>实体嵌套List提交</td>
            <td>
                <div>
                    适用于请求时,一个实体嵌套这一个list。</br>
                    举例:提交一个老师的实体Teacher，老师管理着不同的学生，还要传入学生实体List &lt;Student&gt;
                </div>
            </td>
            <td>提交异常,会报400错误</td>
            <td>
               <pre id="f1Text">
{
  "name": "张老师",
  "age": "40",
  "students": [
    {
      "name": "王小明",
      "age": "15"
    },
    {
      "name": "李小红",
      "age": "15"
    }
  ]
}
               </pre>
            </td>
            <td>
                <pre id="f1Code">
 function f1(){
    // 关键逻辑，需要转化成JSON字符串
    let data = JSON.stringify(teacher)
    $.ajax({
        type : 'post',
        url : '/f1',
        data : data,
        contentType:'application/json',
        success : function(resData) {
            console.log("f1调用成功" + resData)
            alert("f1调用成功" + resData);
        },
    });
}
                </pre>
            </td>
            <td>
                <pre id="f1Java">
@PostMapping("/f1")
@ResponseBody
public String f1(@RequestBody Teacher teacher){
    return teacher.toString();
}
                </pre>
            </td>
            <td>
                <button onclick="f1();" class="btn btn-primary">f1提交测试</button>
            </td>
        </tr>
        <tr class="success">
            <td>f2</td>
            <td>文件上传</td>
            <td>适用于原生文件上传,onchange后数据组装</td>
            <td>提交时后台接收的MultipartFile为空</td>
            <td>
                <pre id="f2Text">file: (binary)</pre>
            </td>
            <td>
                <pre id="f2Code">
function f2(){
    let file = event.target.files[0];
    let formData = new FormData();
    formData.append('file', file);
    $.ajax({
        url: '/f2',
        type: 'post',
        contentType: false,  // ContentType设置成false
        processData: false, // 不需要让浏览器对FormData进行转换
        data: formData
    }).done(function (resData) {
        console.log("f2调用成功:" + resData)
        alert("f2调用成功：" + resData)
    })
}

                </pre>
            </td>
            <td>
                <pre id="f2Java">
@PostMapping("/f2")
@ResponseBody
public String f2(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
        return "上传失败，请选择文件";
    }
    String fileName = file.getOriginalFilename();
    return fileName;
}
                </pre>
            </td>
            <td>
                <input type="file" name="f2" id="f2" onchange="f2()" multiple="multiple"></input>
            </td>
        </tr>

        <tr class="active">
            <td>f3</td>
            <td>上传List</td>
            <td>
                <div>
                    适用于请求时,直接传一个List实体。</br>
                </div>
            </td>
            <td>提交异常,会报400错误</td>
            <td>
               <pre id="f3Text">
[{
    name: "张老师",
    age: "40"
},{
    name: "李老师",
    age: "35"
}]
               </pre>
            </td>
            <td>
                <pre id="f3Code">
 function f3(){
    // 关键逻辑，需要转化成JSON字符串
    let data = JSON.stringify(teachers)
    $.ajax({
        type : 'post',
        url : '/f3',
        data : data,
        contentType:'application/json',
        success : function(resData) {
            console.log("f3调用成功" + resData)
            alert("f3调用成功" + resData);
        },
    });
}
                </pre>
            </td>
            <td>
                <pre id="f3Java">
@PostMapping("/f3")
@ResponseBody
public String f3(@RequestBody List&lt;Teacher&gt; teachers) {
    return teachers.toString();
}
                </pre>
            </td>
            <td>
                <button onclick="f3();" class="btn btn-primary">f3提交测试</button>
            </td>
        </tr>

        <tr class="success">
            <td>f4</td>
            <td>上传数组Array</td>
            <td>适用于请求时,直接传一个数组。</td>
            <td>提交异常,会报500错误</td>
            <td>
                <pre id="f4Text">[1,2,3,4]</pre>
            </td>
            <td>
                <pre id="f4Code">
function f4(){
    $.ajax({
        type : 'post',
        url : '/f4',
        data : {
            array:array
        },
        dataType: 'json',
        success : function(resData) {
            console.log("f4调用成功" + resData)
            alert("f4调用成功" + resData);
        },
    });
}
                </pre>
            </td>
            <td>
                <pre id="f4Java">
@PostMapping("/f4")
@ResponseBody
public int[] f4(@RequestParam(value = "array[]")int[] array) {
    return array;
}
                </pre>
            </td>
            <td>
                <button onclick="f4();" class="btn btn-primary">提交测试</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<script src="https://code.jquery.com/jquery.js"></script>
<script>
    const students = [{
        name: "王小明",
        age: "15",
    },{
        name: "李小红",
        age: "15",
    }]
    const teacher = {
        name: "张老师",
        age: "40",
        students:students
    }
    // F1 Json数据格式化
    $('#f1Text').html(JsonFormat(teacher));

    // 实体嵌套List提交
    function f1(){
        // 关键逻辑，需要转化成JSON字符串
        let data = JSON.stringify(teacher)
        $.ajax({
            type : 'post',
            url : '/f1',
            data : data,
            contentType:'application/json',
            success : function(resData) {
                console.log("f1调用成功" + resData)
                alert("f1调用成功" + resData);
            },
        });
    }
    // 文件上传
    function f2(){
        let file = event.target.files[0];
        let formData = new FormData();
        formData.append('file', file);
        $.ajax({
            url: '/f2',
            type: 'post',
            contentType: false,  // ContentType设置成false
            processData: false, // 不需要让浏览器对FormData进行转换
            data: formData
        }).done(function (resData) {
            console.log("f2调用成功:" + resData)
            alert("f2调用成功：" + resData)
        })
    }

    const teachers = [{
        name: "张老师",
        age: "40"
    },{
        name: "李老师",
        age: "35"
    }]
    // 直接提交List
    function f3(){
        // 关键逻辑，需要转化成JSON字符串
        let data = JSON.stringify(teachers)
        $.ajax({
            type : 'post',
            url : '/f3',
            data : data,
            contentType:'application/json',
            success : function(resData) {
                console.log("f3调用成功" + resData)
                alert("f3调用成功" + resData);
            },
        });
    }
    const array = [1,2,3,4]
    // 直接提交数组
    function f4(){
        $.ajax({
            type : 'post',
            url : '/f4',
            data : {
                array:array
            },
            dataType: 'json',
            success : function(resData) {
                console.log("f4调用成功" + resData)
                alert("f4调用成功" + resData);
            },
        });
    }

    // Json格式化工具
    function JsonFormat(json) {
        if (typeof json != 'string') {
            json = JSON.stringify(json, undefined, 2);
        }
        json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
        return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
            var cls = 'number';
            if (/^"/.test(match)) {
                if (/:$/.test(match)) {
                    cls = 'key';
                } else {
                    cls = 'string';
                }
            } else if (/true|false/.test(match)) {
                cls = 'boolean';
            } else if (/null/.test(match)) {
                cls = 'null';
            }
            return '<span class="' + cls + '">' + match + '</span>';
        });
    }
</script>
<style>
    pre {outline: 1px solid #ccc; padding: 5px; margin: 5px; }
    .string { color: green; }
    .number { color: darkorange; }
    .boolean { color: blue; }
    .null { color: magenta; }
    .key { color: red; }
    .container{
        width: 1900px;
    }
</style>
</body>
</html>
```
##### 后端
```java
package net.javadog.requestdemo.controller;

import net.javadog.requestdemo.entity.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 请求管理
 *
 * @author hdx
 * @version V1.0
 * @date 2021年10月18日
 */
@Controller
public class RequestController {
    @GetMapping("/request")
    public String request(){
        return "request/index";
    }

    /**
     * @Description 实体嵌套List提交
     * @param teacher
     * @Return java.lang.String
     */
    @PostMapping("/f1")
    @ResponseBody
    public String f1(@RequestBody Teacher teacher){
        return teacher.toString();
    }

    /**
     * @Description 文件上传
     * @param file
     * @Return java.lang.String
     */
    @PostMapping("/f2")
    @ResponseBody
    public String f2(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        return fileName;
    }

    /**
     * @Description List实体上传
     * @param teachers
     * @Return java.lang.String
     */
    @PostMapping("/f3")
    @ResponseBody
    public String f3(@RequestBody List<Teacher> teachers) {
        return teachers.toString();
    }

    /**
     * @Description 数组上传
     * @param array
     * @Return java.lang.String
     */
    @PostMapping("/f4")
    @ResponseBody
    public int[] f4(@RequestParam(value = "array[]")int[] array) {
        return array;
    }
}

```