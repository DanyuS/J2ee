# 要求：请提交word版本。

一、数据库设计	
 1、ER图
 
2、数据库表（表名，各列名及数据类型，外键关系）

CREATE TABLE `approverestaurant` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL,
  `rid` varchar(45) NOT NULL,
  `restaurantName` varchar(45) NOT NULL,
  `restaurantPassword` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `keeper` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `registerTime` varchar(45) NOT NULL,
  `identity` varchar(45) NOT NULL,
  `account` double NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`aid`),
  KEY `id_idx` (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `cart` (
  `cartId` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL,
  `rid` varchar(45) NOT NULL,
  `meid` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `currentPrice` double NOT NULL,
  `stock` int(11) NOT NULL,
  `num` int(11) NOT NULL,
  `totalPrice` double NOT NULL,
  PRIMARY KEY (`cartId`),
  KEY `mid_idx` (`mid`),
  KEY `meid_idx` (`meid`),
  CONSTRAINT `mid1` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `coupon` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `couponId` varchar(45) NOT NULL,
  `couponName` varchar(45) NOT NULL,
  `minPayment` double NOT NULL,
  `discount` double NOT NULL,
  `restaurantRange` varchar(45) NOT NULL,
  `startTime` varchar(45) NOT NULL,
  `endTime` varchar(45) NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `couponusage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  `remain` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mid_idx` (`mid`),
  KEY `cid_idx` (`cid`),
  CONSTRAINT `cid` FOREIGN KEY (`cid`) REFERENCES `coupon` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mid` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `deliveryaddress` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addrId` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `province` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `finance` (
  `financeId` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(45) NOT NULL,
  `mid` int(11) NOT NULL,
  `date` varchar(45) NOT NULL,
  `account` double NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`financeId`),
  KEY `mid_idx` (`mid`),
  CONSTRAINT `mid2` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `manager` (
  `maid` int(11) NOT NULL AUTO_INCREMENT,
  `managerName` varchar(45) NOT NULL,
  `managerPassword` varchar(45) NOT NULL,
  `identity` varchar(45) NOT NULL,
  `account` double NOT NULL,
  PRIMARY KEY (`maid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `member` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `memberName` varchar(45) NOT NULL,
  `memberPassword` varchar(45) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `level` int(11) NOT NULL,
  `registerTime` varchar(45) DEFAULT NULL,
  `identity` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `account` double NOT NULL,
  `consumption` double NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

CREATE TABLE `menu` (
  `meid` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `remarks` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `currentPrice` double NOT NULL,
  `stock` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  `startTime` varchar(45) NOT NULL,
  `endTime` varchar(45) NOT NULL,
  PRIMARY KEY (`meid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

CREATE TABLE `orders` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(45) NOT NULL,
  `restaurantName` varchar(45) NOT NULL,
  `restaurantAddress` varchar(45) NOT NULL,
  `mid` int(11) NOT NULL,
  `memberName` varchar(45) NOT NULL,
  `level` int(11) NOT NULL,
  `memberAddress` varchar(45) NOT NULL,
  `remarks` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `arrivalTime` varchar(45) NOT NULL,
  `seconds` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `mid3_idx` (`mid`),
  CONSTRAINT `mid3` FOREIGN KEY (`mid`) REFERENCES `member` (`mid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `restaurant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` varchar(45) NOT NULL,
  `restaurantName` varchar(45) NOT NULL,
  `restaurantPassword` varchar(45) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `keeper` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `registerTime` varchar(45) NOT NULL,
  `identity` varchar(45) NOT NULL,
  `account` double DEFAULT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;


二、架构设计 1、工程的项目结构截图；
 
 
 
 
 
 
 
2、是否使用框架，如Spring+Hibernate 框架；
使用Hibernate
3、前端页面是否使用框架，如bootstrap框架；
前端使用AmazeUI
三、类设计
1、各包的类：名称及职责；（注：不要使用截图）
dao
CouponDao.java：增删改查优惠券相关数据信息接口
ManagerDao.java：增删改查经理相关数据信息接口
MemberDao.java：增删改查会员相关数据信息接口
MenuDao.java：增删改查菜单相关数据信息接口
OrderDao.java：增删改查订单相关数据信息接口
RestaurantDao.java：增删改查餐厅相关数据信息接口

daoImpl
CodeUtil.java：生成注册码激活码工具
CouponDaoImpl.java：增删改查优惠券相关数据信息
JDBConnect.java：连接数据库工具
MailUtil.java：发送邮件工具
ManagerDaoImpl.java：增删改查经理相关数据信息
MapUtil.java：地图类工具
MemberDaoImpl.java：增删改查会员相关数据信息
MenuDaoImpl.java：增删改查菜单相关数据信息
OrderDaoImpl.java：增删改查订单相关数据信息
RestaurantDaoImpl.java：增删改查餐厅相关数据信息
TimeOrder.java：处理未支付订单的自动退单

entity
ApproveRestaurant.java：餐厅修改信息
Cart.java：购物车
Coupon.java：优惠券
CouponUsage.java：优惠券使用情况
DeliveryAddress.java：会员收货地址
Finance.java：财务
Manager.java：经理
Member.java：会员
Menu.java：菜单
Orders.java：订单
Restaurant.java：餐厅

sevice
ApproveService.java：经理审批业务接口
GetStatisticsService.java：会员、餐厅、经理获取统计数据业务接口
LoginService.java：会员、餐厅、经理登录接口
ManageInformationService.java：会员管理信息接口
ModifyService.java：会员、餐厅修改信息接口
OrderService.java：会员、餐厅处理订单业务接口
RegisterService.java：会员、餐厅、经理登录业务接口

serviceImpl
ApproveServiceImpl.java：经理审批业务
GetStatisticsServiceImpl.java：会员、餐厅、经理获取统计数据业务
LoginServiceImpl.java：会员、餐厅、经理登录
ManageInformationServiceImpl.java：会员管理信息
ModifyServiceImpl.java：会员、餐厅修改信息
OrderServiceImpl.java：会员、餐厅处理订单业务
RegisterServiceImpl.java：会员、餐厅、经理登录业务


Servlet:
GetFinanceServlet.java：经理得到一段时间内会员、餐厅、网站财务统计
GetRegistMemberNumDivByDefServlet.java：经理得到一段时间内会员注册情况统计
GetRegistRestaurantNumDivByDefServlet.java：经理得到一段时间内餐厅注册情况统计
//GetRestaurantCategoryAndNumServlet.java：经理得到餐厅种类分布统计（废）
GetRestaurantCategoryNumServlet.java：经理得到餐厅种类分布统计
ManagerAllForbidApprovalServlet.java：经理批量驳回餐厅修改申请
ManagerAllPermitApprovalServlet.java：经理批量同意餐厅修改申请
ManagerAllPermitBalanceServlet.java：经理批量结算餐厅账务
ManagerForbidApprovalServlet.java：经理驳回某个餐厅修改申请
ManagerGetAllApprovalInfoServlet.java：经理获取全部餐厅修改申请
ManagerGetAllFinanceInfoServlet.java：经理获取全部结算餐厅账务信息
ManagerLoginServlet.java：经理登录
ManagerPermitApprovalServlet.java：经理同意某个餐厅修改信息申请
ManagerPermitBalanceServlet.java：经理结算某个餐厅财务
MemberAddAddressServlet.java：会员添加地址信息
MemberAddCouponServlet.java：会员添加优惠券
MemberAddMenuToCartServlet.java：会员将菜品加入购物车
MemberAddToCurrentOrderServlet.java：会员将购物车内菜品结算至订单
MemberAddToCurrentOrderWithoutPayServlet.java：会员将未支付购物车内菜品结算至订单
MemberConfirmOrderServlet.java：会员确认订单到货
MemberDeleteAddressServlet.java：会员删除地址信息
MemberDeleteCartServlet.java：会员删除购物车
MemberDeleteCouponServlet.java：会员删除优惠券
MemberGetAccountServlet.java：会员获取自己余额信息
MemberGetAddressServlet.java：会员获取地址详细信息
MemberGetAllCouponServlet.java：会员获取所有权限下未添加优惠券信息
MemberGetAllFinanceInfoServlet.java：会员获取所有消费详情
MemberGetAllOrderServlet.java：会员获取所有订单
MemberGetAllRestaurantServlet.java：会员获取所有餐厅信息
MemberGetCartServlet.java：会员获取购物车内详情
MemberGetCouponServlet.java：会员下订单时获取所有可以使用的优惠券信息
MemberGetEmailServlet.java：会员获取邮件激活码注册
MemberGetInfoServlet.java：会员获取个人资料
MemberGetOrderAccountServlet.java：会员根据消费降序排列订单
MemberGetOrderRestaurantServlet.java：会员根据餐厅注册码降序排列订单
MemberGetOrderServlet.java：会员获取所有已完成订单
MemberGetOrderTimeServlet.java：会员根据消费时间降序排列订单
MemberGetPasswordServlet.java：会员获取密码
MemberGetRestaurantCouponServlet.java：会员使用餐厅优惠券
MemberGetSingleAddressServlet.java：会员获取某项地址的详细信息
MemberLaterPayAccountServlet.java：会员稍后付款
MemberLoginServlet.java：会员登录
MemberLogoffServlet.java：会员注销
MemberModifyAddressServlet.java：会员修改地址信息
MemberModifyPasswordServlet.java：会员修改密码
MemberPayAccountServlet.java：会员支付订单
MemberRechargeServlet.java：会员充值
MemberRefundServlet.java：会员退款
MemberRegisterServlet.java：会员注册
MemberSearchServlet.java：会员根据关键字在店名、菜品中搜索餐厅
RestaurantAddCouponServlet.java：餐厅添加优惠券信息
RestaurantAddMenuServlet.java：餐厅添加菜品信息
RestaurantDeleteCouponServlet.java：餐厅删除优惠券
RestaurantDeleteMenuServlet.java：餐厅删除菜单
RestaurantGetAllCouponServlet.java：餐厅获取所有优惠券
RestaurantGetAllFinanceInfoServlet.java：餐厅获取入账记录
RestaurantGetAllReleasedMenuServlet.java：餐厅获取所有已发布菜单信息
RestaurantGetAllUnreleasedMenuServlet.java：餐厅获取所有待发布菜单信息
RestaurantGetInfoServlet.java：餐厅获取详细信息
RestaurantGetOrderToSendServlet.java：餐厅获取所有待派送订单
RestaurantGetSubscribeOrderAccountServlet.java：餐厅按金额降序排列所有订单
RestaurantGetSubscribeOrderMemberServlet.java：餐厅按会员等级降序排列所有订单
RestaurantGetSubscribeOrderServlet.java：餐厅降序排列所有订单
RestaurantGetSubscribeOrderTimeServlet.java：餐厅按时间降序排列所有订单
RestaurantGetUnsubscribeOrderAccountServlet.java：餐厅按金额降序排列所有退订订单
RestaurantGetUnsubscribeOrderMemberServlet.java：餐厅按会员等级降序排列所有退订订单
RestaurantGetUnsubscribeOrderServlet.java：餐厅降序排列所有退订订单
RestaurantGetUnsubscribeOrderTimeServlet.java：餐厅按时间降序排列所有退订订单
RestaurantLoginServlet.java：餐厅登录
RestaurantModifyInfoServlet.java：餐厅修改信息
RestaurantRegisterServlet.java：餐厅注册
RestaurantSendOrderServlet.java：餐厅接单且派送

2、各前端的页面：名称及功能；（注：不要使用截图）
AddAddressInfo：会员添加地址信息
AddCoupon：餐厅添加优惠券信息
AddMenu：餐厅添加菜品信息
AddPackage：餐厅添加套餐信息
Balance：经理审批结算餐厅收入
BookHall：会员选择餐厅
Cart：会员购物车页面，对加入购物车的商品增减，选择优惠券与地址结算
checkMail：会员注册后获得有注册码的邮件
CouponHall：会员领取优惠券的地方
CurrentCoupon：餐厅管理优惠券信息
CurrentOrder：餐厅查看会员尚未送达的订单，可在规则下退单
FinanceStatistics：经理查看一段时间内会员、餐厅、网站财务的折线图
GetCode：餐厅注册获取注册码
HistoryOrder：会员历史订单信息，可根据时间、金额、餐厅统计信息
Login：会员、餐厅、经理登录的界面
ManageAddress：会员管理地址的界面，可以删除地址和进入修改地址的界面
ManageCoupon：会员管理优惠券的界面，可以删除优惠券
Manager：经理审批餐厅修改的信息
Member：会员资料，可以进入修改资料的界面
MemberCurrentOrder：会员当前订单列表
MemberFinance：会员的消费列表
MemberStatistics：经理查看会员注册情况统计
Menu：会员选择餐厅后选择菜品添加
ModifyAddress：会员修改地址信息
ModifyMember：会员修改密码
ModifyRestaurant：餐厅修改自身信息
Recharge：会员充值金额界面
Register：会员注册
ReleasedMenu：餐厅查看已经发布的菜品信息
Restaurant：餐厅详细信息，可前往修改界面
RestaurantFinance：餐厅的收入列表
RestaurantRegister：餐厅注册
RestaurantStatistics：经理查看餐厅注册情况统计
Subscribe：餐厅获得订单信息，包括按时间、金额、会员统计
UnreleasedMenu：餐厅查看未发布的菜品信息
Unsubscribe：餐厅获得退订订单信息，包括按时间、金额、会员统计

