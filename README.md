# WeaWorking项目简介
项目将会不定期更新，内容不和谐请谅解并忽略……
## 1.项目构建原因
WeaWroking项目起初立项的原因有两个：   
* 团队任务管理系统web版，希望能有移动端的产品
* 用于锻炼Android应用程序框架编写的能力，这里主要用到的是MVP   
### 1.1需求分析
使用人：团队成员  
使用目的：丰富WeaWroking任务管理系统的使用类型   
针对已有的WeaWroking web程序进行功能移植，使其能够在移动设备上使用。介于目前web版的WeaWroking已经可以正常使用，所以移动端的设计主要是按照已有的功能来设计。    
需求分为：
* 后端提供API接口，这部分需要先整理需要的数据，然后协调设计接口
* 移动界面设计为左右滑动抽屉。左抽屉用来展示频道和团队人员，右抽屉用来展示任务。任务分为todo，doing，done三个部分，所以此模块使用viewpager。详细的设计见代码  
### 1.2需求设计

## 2.项目结构
### 2.1 MVP
MVP是根据MVC传统设计模式的改进和强化而来。将数据和视图之间的联系进一步的减弱。Model和View的交互被隔离，必须要通过Presenter传递动作。这样的好处不言而喻，UI和数据可以分开管理，相互之间的修改几乎不会影响对方。
### 2.2 Model的设计
Model的作用是存放数据，我们姑且将Model理解为一个数据容器Dao。数据来源包括：DB和网络。  
具体的接口包括：
* model从DB中获取本地数据显示（网络异常）
* model从远端服务器获取数据（正常情况）
### 2.3 Processor的设计
Presenter的作为中间件，工作内容相对复杂。具体可以分为以下几个模块：  
* 链接服务器，获取服务器数据
* 网络异常，加载本地DB数据入缓存
* 将加载成功的数据返回给UI刷新界面，并且更新本地DB
* 缓存数据，提供数据接口给UI调用
## 3.项目后续设想
Oauth认证，结构优化，本地数据存储优化

### OAuth相关文档供参考 
  
首先了解一下OAuth 2.0的基本知识  
[理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)
   
深入了解OAuth各个不同组件，这是一个台湾人写的。内容很丰富，建议可以深入学习  
[OAuth笔记](https://blog.yorkxin.org/posts/2013/09/30/oauth2-2-cilent-registration/)
   
OAuth 2.0英文文档，相当详细  
[The OAuth 2.0 Authorization Framework](http://tools.ietf.org/html/rfc6749#section-1.3)

使用OAuth 2.0验证资源API   
[OAuth 2.0 教程: Grape API 整合 Doorkeeper](https://ruby-china.org/topics/14656)

### 目前NET263使用的OAuth

#### *OAuth常见的认证流程

* Authorization Code Grant Type Flow —— 认证码验证流程
  
* Implicit Grant Type Flow —— 简单认证流程
  
* Resource Owner Password Credentials Grant Type Flow —— 密码认证流程
  
* Client Credentials Grant Type Flow —— 客户端认证流程
  
最常见和使用较多的是前两种。Net263现在的认证机制就是第一种Authorization Code Grant Type Flow。简单粗暴的区分前两种，就是看Client是否存在Server，如果存在Server则可以通过Server来保存Confidential。因为Net263现阶段的认证都是通过Web Browser，所以传统的Authorization Code Grant Type Flow是可以满足认证的。

#### *目前设想使用的认证方式

通过测试发现调用资源服务器的认证接口是无法获取token的。通过资源服务器（WeaWorking）登陆认证流程来说明。   
* 1、C（Client-浏览器）访问R（Resource Server-资源服务器）的认证接口 /users/auth/net263。该地址为omniauth认证。
* 2、R将C重定向到A（Authorization Server-认证服务器）的认证接口 /oauth/authorize获取code，A将C重定向到R事先定义好的CallBack
* 3、R到A的/oauth/token获取token。
* 4、R通过token到A中获取用户信息
* 5、R给C返回index页面
   
根据上面的登陆流程可以看出，资源服务器直接从认证服务器获取token，Client并没用经手token。另外，现在的资源服务器直接使用token去获取数据也是不被允许的。
   
综上问题，现使用以下方案：
* 客户端直接通过认证服务器的/oauth/authorize和/oauth/token获取token
* 使用token在认证服务器获取user信息
* 客户端向资源服务器API发送数据请求。需带参数：app_key，secret，user.email，其他查询的必要参数。

app_key和secret是为了确保非法客户端访问资源服务器；user.email告知资源服务器查询用户。




