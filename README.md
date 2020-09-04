# spring-nobita
参考spring的设计理念和实现，自定义ioc,aop,mvc实现，仅提供基础的容器能力实现简单，为了简单实现，体现Spring设计，具有以下特征
- 不识别jar包内资源，不识别定义的网络资源，即仅支持本地启动和识别和加载.class
- 不支持构造函数注入
- 装配时不考虑并发问题
- 仅支持单例模式
- 动态代理选择时不做动态判断，即要么全部jdk动态代理，要么全部cglib动态代理
- 不提供mvc模块的参数实例动态解析能力，项目中解析为默认参数
- ...

（一切为体现ioc,aop,mvc的理念，造轮子只为更好地利用轮子，从思想上升华）

博客地址
- [自研Spring IOC(一)](https://www.chenx.tech/spring-ioc/)
- [自研Spring AOP(二)](https://www.chenx.tech/spring-aop/)
- [自研Spring AOP2.0(三)](https://www.chenx.tech/spring-aop2/)
- [自研Spring MVC(四)](https://www.chenx.tech/spring-mvc/)

#### 自研IOC 1.0实现
该IOC提供以下功能
- 支持.Class文件的类资源识别和加载功能，暂时未实现jar包资源和网络资源识别
- 提供 @Controller @Service @Repository @Component 注解的bean定义
- 提供 @Autowired 注解实现注入能力，但仅支持通过反射并调用Set方法方式注入，且要求注入目标类的构造函数均为无参构造函数
- 提供基础的容器管理能力

详情请参考ioc-1.0分支的spring-ioc，demo模块

#### 自研AOP 1.0实现
该aop 1.0实现简易版aop能力，对于切点的定义较为简单，适合于新手理解aop的定义和实现方式，该版本中不支持AspectJ语法级别的切点定义，
仅对某一特定注解标识的类生效，该aop模块提供jdk动态代理和cglib动态代理能力，但是并没有实现spring中动态选择jdk动态代理还是cglib动态代理
能力，而是只能在jdk动态代码或者cglib动态代理中选其一

详情参考aop1.0分支的spring-aop,spring-ioc,demo模块

#### 自研AOP 2.0实现
该aop2.0在aop1.0的基础上引入AspectJ全系列语法树支持，完成近乎和Spring相同的切点定义。

详情参考aop2.0分支的spring-aop,spring-ioc,demo模块

#### 自研MVC实现
该mvc模块实现与spring boot相同，内嵌了embedTomcat以支持web service,实现自定义的requestHandleMapping以实现请求转发

详情请参考mvc-1.0分支的spring-core,spring-mvc,spring-ioc,spring-aop,demo模块