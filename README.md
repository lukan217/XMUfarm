# 游戏说明

1. Java程序设计课程大作业，农场游戏，由五个Java菜鸟选手(szp,wzd,ypt,,zkb,ljy,)开发，使用了LibGDX游戏框架（因为没有使用Gradle因此只支持桌面端运行）

2. 游戏原型为一个农场游戏，可以在主界面实现种菜、收菜、浇水、施肥等各种功能，与一般的农场游戏无异

   ![image-20200116230216351](https://zachary66.oss-cn-shenzhen.aliyuncs.com/img/image-20200116230216351.png)

3. 在游戏开始前需要登录注册

   ![image-20200116230128608](https://zachary66.oss-cn-shenzhen.aliyuncs.com/img/image-20200116230128608.png)

4. 游戏有计时系统，主要用来实现昼夜交替、人物作息、作物生长

   - 昼夜交替：不同时段游戏界面不一样，明暗度会随时间变化

   - 人物作息：到了晚上需要睡觉，睡觉可以恢复体力，否则会晕倒，晕倒会损失体力和金币

     ![image-20200116230722603](https://zachary66.oss-cn-shenzhen.aliyuncs.com/img/image-20200116230722603.png)

     ![image-20200116230741099](https://zachary66.oss-cn-shenzhen.aliyuncs.com/img/image-20200116230741099.png)

     ![image-20200116230954680](https://zachary66.oss-cn-shenzhen.aliyuncs.com/img/image-20200116230954680.png)

   - 作物生长：作物浇水时开始生长，浇水buff持续一天，一天后消失，作物将停止生长，一天没有浇水的作物将死亡

5. 地图中加入了一些厦大元素，还设置了几个简单的NPC

   ![image-20200116230834442](https://zachary66.oss-cn-shenzhen.aliyuncs.com/img/image-20200116230834442.png)

   ![image-20200116230903896](https://zachary66.oss-cn-shenzhen.aliyuncs.com/img/image-20200116230903896.png)

# 运行环境要求

1. JRE版本13.0或以上
2. 电脑分辨率大于1600*900，否则可能会有显示问题
3. 数据存储使用了腾讯云数据库（2020年2月3日到期），需要联网运行，没联网或者数据库过期的情况下也可正常运行，数据则存储到本地的user文件夹

# 其他说明

1. 因为是第一次接触游戏开发，而且是在大部分团队成员的代码水平仅仅是学过一门C语言和一门数据结构的基础上开发的，所以代码质量可能有点差，如有不当之处，还请指正（虽然估计我也不会再碰这玩意了）
2. 因为这只是一个课程作业，DDL赶得也比较紧，很多功能都还没有完善，游戏的一些指标也可能设置的不太恰当，因此游戏的可玩性很差，仅供学习参考使用