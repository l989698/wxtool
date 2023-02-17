package com.tool.wxtoolproject.api.common.message;

/**
 * Description: 全局静态常量类<br>
 * Why & What is modified：<br>
 * Copyright (C) 2017 boco.com.cn All Right Reserved.<br>
 *
 * @version 1.0
 */
public class Constants {

	// 成功code
	public static final String SUCCESS = "000000";
	// 用户地址默认状态
	public static final String ADDRESS_STATUS_1 = "1";
	public static final String ADDRESS_STATUS_0 = "0";

	public static final class  SIGNINTEGRAL {
		public static final String SIGN_100 = "100"; // 连续签到8天 100 积分
		public static final int SIGN_FIRST = 1; // 连续签到8天 100 积分
		public static final Integer SIGN_8 = 8; // 连续签到8天
	}

	/**
	 * 商品状态
	 */
	public static final class  COMMODITY_STATUS {
		public static final String commodity_status_01= "1"; // 在售商品
		public static final String commodity_status_99= "99"; // 下架商品
	}

	/**
	 * 请求地址固定参数
	 */
	public static final class RequestParams {

		// "data":"xxxxxxx"//xxxxxx对{"header":{xxx},"body":{xxx}}AES加密后数据
		public static final String DATA = "data";

		// appid
		public static final String APPID = "appid";
	}


	/***config redis 相关**/
	public static final class SysConfigAndRedisClass{
		/**微信sessionKey 前缀**/
		public static final String SESSION_KEY_FLEX = "_sessionKey";
		/***微信sessionKey有效期*/
		public static final long SESSION_KEY_EXPIRE = 7200L;
	}

	/****
	 * 生活服务订单类型
	 */
	public static final class LifeOrderStatusClass {
		// 待支付--1，待消费--2，待评价--3，已取消--5，已完成--4，售后中--6，已退款--7，拒绝退款--8
		public static final String LIFE_ORDER_STATUS_1 = "1";
		public static final String LIFE_ORDER_STATUS_2 = "2";
		public static final String LIFE_ORDER_STATUS_3 = "3";
		public static final String LIFE_ORDER_STATUS_4 = "4";
		public static final String LIFE_ORDER_STATUS_5 = "5";
		public static final String LIFE_ORDER_STATUS_6 = "6";
		public static final String LIFE_ORDER_STATUS_7 = "7";
		public static final String LIFE_ORDER_STATUS_8 = "8";
		// 售后审核 1审核中 2通过 3拒绝
		public static final String AUDIT_STATUS_1 = "1";
		public static final String AUDIT_STATUS_2 = "2";
		public static final String AUDIT_STATUS_3 = "3";
	}

	/****
	 * 订单
	 */
	public static final class LifeProductClass {
		// 普通超值购=1 爆款=2
		public static final String SET_MERAL = "1";
		public static final String HOT_SET_MEAL = "2";
		// 状态
		public static final String PRODUCT_STATUS_1 = "1";
		public static final String PRODUCT_STATUS_99 = "99";
		public static final String PRODUCT_STATUS_2 = "2";
		// 用餐状态 1 堂食 2 外卖 3 都支持
		public static final String DINNER_TYPE_1 = "1";
		public static final String DINNER_TYPE_2 = "2";
		public static final String DINNER_TYPE_3 = "3";
	}

	/** 编号生成相关 */
	public static final class SerialNumberRuleClass {
		/** 客户编号首位类型 */
		public static final String USER_CODE_FLEX = "U";
		/** 客户编号随机数长度 */
		public static final int USER_CODE_SN_LENGTH = 4;
		/** 新用户邀请码随机数长度 */
		public static final int INVITE_CODE = 10;
	}

	/**
	 * 用户身份和状态
	 */
	public static final class UserIdentityClass {
		/** 有效 1--有效，0--无效**/
		public static final String VALIDATE_STATE_1 = "1";
		/** 无效 1--有效，0--无效**/
		public static final String VALIDATE_STATE_0= "0";
		/** 是否已实名 1--是，0--否**/
		public static final String IS_AUTHENTICATION = "1";
		/**新用户标识==1 老用户==0**/
		public static final String IS_NEW_USER = "1";
		/** 自购省用户 **/
		public static final String IS_SELF_BUY_USER = "1";
		//可以成为自购省用户的数量
		public static final Integer IS_SELF_BUY_USER_TOTAL =19 ;
		/** 共创股东用户 **/
		public static final String IS_DEALER = "1";
		public static final String DEALER_LEVEL_1 = "1";
		public static final String DEALER_LEVEL_2 = "2";
		public static final String DEALER_LEVEL_3 = "3";

		/** 非星饭儿掌柜用户 **/
		public static final String NO_XINGFANER_ZG = "2";
	}

	public static final class TokenJedisParam {
		public static final String SSO = "SSO";
		public static final String TOKEN_KEY = "token";
		// 验证码输入失败计数失效时间
		public static final long COUNT_EXPIRE_TIME = 1 * 24 * 60 * 60;
		// 登录token 失效时间
		public static final long EXPIRE_TIME = 24 * 60 * 60;// 24 * 60 * 60
		public static final long EXPIRE_TIME_DATE = 10 * 60;
		public static final int DEAD_LINE_DAY = 7;

	}

	public static final class ShopStatus {
		public static final String APPROVE_ING = "1";// 待审核
		public static final String APPROVE_REFUSE = "2";// 审核拒绝
		public static final String ONLINE = "3";// 营业中
		public static final String OFFLINE = "4";// 下架
		public static final String DRAFT = "5";// 草稿
	}

	public static final class PermissionEnum {
		public static final String TYPE_1 = "1";// 菜单文件夹，无具体操作权限
		public static final String TYPE_2 = "2";// 菜单页面，具体操作权限
		public static final String TYPE_3 = "3";// 页面中按钮级别，具有操作权限
		public static final String CHECK_1 = "1";// 已勾选
		public static final String CHECK_0 = "0";// 不勾选
		public static final int ROOT_PID = 0;// 根目录父级id
		public static final String IS_CHILD = "0";// 无子级
		public static final String IS_CHILD_1 = "1";// 有子级
	}

	public static final class ShopGoodsStatus {
		public static final int TO_BE_SALE_STATUS = 2;// 待上架
		public static final int SALE_STATUS = 1;// 已上架
		public static final int UNDER_STATUS = 3;// 已下架
		public static final Integer GOODS_TYPE = 1;// 商品类型
	}

	/**
	 * 发送短信验证码相关
	 */
	public static final class SMCodeParam {
		/**
		 * 商户快捷登录发送短信码类型
		 */
		public static final String SM_MERCHANTS_LOGIN = "81";

		/**
		 * 商户修改手机号——校验原手机号
		 */
		public static final String SM_MERCHANTS_CHECK_MOBILE = "82";

		/**
		 * 商户修改手机号——新手机号验证码
		 */
		public static final String SM_MERCHANTS_UPDATE_MOBILE = "83";
		/**
		 * 商户添加银行卡手机号校验
		 */
		public static final String MERCHANT_ADDBANK_MOBILE_PHONE_NUMBER_VERIFICATION = "3";
		/**
		 * 商户提现手机号校验
		 */
		public static final String MERCHANT_WITHDRAW_MOBILE_PHONE_NUMBER_VERIFICATION = "84";

		/**
		 * 商户用户修改密码短信
		 */
		public static final String SM_MERCHANTS_UPDATE_PASSWORD = "85";
		/**
		 * 发送失败
		 */
		public static final String SM_SEND_FAIL = "0";

		/**
		 * 发送成功
		 */
		public static final String SM_SEND_SUCCESS = "1";

		/**
		 * 短信验证码有效时间（分）
		 */
		public static final int SM_CODE_ADDMINUTE_1 = 10;

		/**
		 * 两次发送间隔（分）
		 */
		public static final int NEXT_SEND_ADDMINUTE_1 = 1;

		/**
		 * 两次发送间隔（分）
		 */
		public static final int NEXT_SEND_ADDMINUTE_2 = 60;

		/**
		 * 验证码发送次数5
		 */
		public static final int CUR_SEND_COUNT_1 = 5;

		/**
		 * 验证码发送次数10
		 */
		public static final int CUR_SEND_COUNT_2 = 10;

		/**
		 * 验证码发送次数20
		 */
		public static final int CUR_SEND_COUNT_3 = 20;

		/**
		 * 短信验证码长度
		 */
		public static final int SM_CODE_NUM_1 = 6;

		/**
		 * 忘记密码时短信验证码长度
		 */
		public static final int SM_CODE_NUM_2 = 6;

		/** 业务流水号随机数长度 */
		public static final int SERIAL_NO_LENGTH = 3;

	}

	public static final class ShopAfterSaleStatus {
		public static final String STATUS11 = "买家已申请退款，待商家处理";
		public static final String STATUS12 = "商家已拒绝";
		public static final String STATUS13 = "退款成功";
		public static final String STATUS14 = "超时未处理，系统处理退款成功";
		public static final String STATUS15 = "买家已撤销申请";
		public static final String STATUS31 = "买家已申请退货，待商家处理";
		public static final String STATUS32 = "商家已拒绝";
		public static final String STATUS33 = "待买家发货";
		public static final String STATUS34 = "买家已发货，待商家确认收货并退款";
		public static final String STATUS35 = "退款完成";
		public static final String STATUS36 = "买家超时未发货，系统关闭售后单";
		public static final String STATUS37 = "超时未处理，系统自动退款完成";
		public static final String STATUS38 = "买家已撤销申请";
		public static final String STATUS41 = "买家已申请换货，待商家处理";
		public static final String STATUS42 = "商家已拒绝";
		public static final String STATUS43 = "待买家发货";
		public static final String STATUS44 = "买家已发货，待商家确认收货并发货";
		public static final String STATUS45 = "待买家确认收货";
		public static final String STATUS46 = "换货完成";
		public static final String STATUS47 = "商家超时未处理，系统关闭订单并退款";
		public static final String STATUS48 = "买家超时未发货，系统关闭售后单";
		public static final String STATUS49 = "换货完成";
		public static final String STATUS40 = "买家已撤销申请";
	}

	/**
	 * 用户余额变动描述
	 */
	public static final class CustBalancesChangeMessage {
		public static final String CUST_RECHARGE_STR = "充值"; // 用户充值
		public static final String CUST_ORDER_CONSUME_STR = "订单支付"; // 订单消费
		public static final String CUST_SHOPORDER_CONSUME_STR = "商城订单支付"; // 订单消费
		public static final String CUST_WITHDRAW = "提现"; // 提现
		public static final String CUST_WITHDRAW_FEE_STR = "提现手续费"; // 提现手续费
		public static final int CUST_SHARE_FEE_STR = 5; // 分享提成
		public static final String CUST_PARTNER_FEE_STR = "合伙人提成"; // 合伙人提成
		public static final String PARTNER_FEE_STR = "合伙人推广收益"; // 合伙人推广收益
		public static final String CUST_FIRST_LEVEL_FEE_STR = "分享佣金"; // 上级分销提成
		public static final String CUST_SECOND_LEVEL_FEE_STR = "分享佣金"; // 上级的上级分销提成
		public static final String VIP_FIRST_LEVEL_FEE_STR = "会员卡推广直接收益"; // 上级分销提成
		public static final String VIP_SECOND_LEVEL_FEE_STR = "会员卡推广间接收益"; // 上级的上级分销提成
		public static final String ORDER_CANCEL_STR = "订单退还"; // D订单退还
	}

	// 商户类型
	public static final class MERCHANTSINFO {
		public static final String COMPANY = "1"; // 企业
		public static final String XIAODIAN = "2"; // 小店
	}

	/** 商城商家余额变动类型 */
	public static final class merChangeForeignType {
		public static final int SETTLE_20 = 20;
		public static final int SETTLE_24 = 24;
	}

	/** 商城商家余额变动消息描述 */
	public static final class merBalancesChangeMessage {
		public static final String SETTLE_20 = "商城订单商家结算";
		public static final String SETTLE_24 = "商城订单运费结算";
	}

	// 商城商品信息
	public static final class GOODSINFO {
		// 1 已上架 2 待上架 3 已下架 4 回收站 5 草稿箱
		public static final String ALREADY = "1";
		public static final String PENDING = "2";
		public static final String REMOVED = "3";
		public static final String GARBAGE = "4";
		public static final String DRAFTBOX = "5";
		// 有效sku状态
		public static final String SKU_STATUS = "1";
		// 无效sku状态
		public static final String INVALID_SKU_STATUS = "0";
		// 商品是否临期 0 否 1是
		public static final String IS_NOT__ON_TIME = "0";
		public static final String IS_ON_TIME = "1";
	}

	// 支付类型
	public static final class PayType {
		public static final String WECHAT_PAY = "1"; // 微信支付
		public static final String BALANCE_PAY = "2"; // 余额支付
	}

	// 售后类型
	public static final class AfterSaleType {
		public static final String ONLYREFUND = "1"; // 仅退款
		public static final String REFUND = "3"; // 退货退款
		public static final String EXCHANGEGOODS = "4"; // 换货
	}

	// 订单状态
	public static final class OrderStatus {
		public static final String WAIT_PAY = "1"; // 待付款
		public static final String ALREADY_PAY = "2"; // 已付款
		public static final String MER_DELIVERYED = "3"; // 商家已发货
		public static final String FINISHED = "4"; // 已完成
		public static final String CLOSED = "6"; // 已取消/已关闭
	}

	// 售后流程状态
	public static final class AfterSaleFlowStatus {
		public static final String EXECUTEING = "1"; // 售后流程执行中（未完成）
		public static final String CLOSE = "2"; // 售后流程关闭
		public static final String FINISH = "3"; // 售后流程成功
	}

	// 售后状态
	public static final class AfterSaleStatus {
		public static final String APPLY = "997"; // 买家申请售后，待商家处理
		public static final String REFUSEAPPLY = "996"; // 商家拒绝申请，待买家撤销或修改
		public static final String AGREEAPPLY = "995"; // 商家同意申请
		public static final String MER_OVERTIME_SUCCEED = "994"; // 商家超时未处理买家申请，系统自动处理同意申请
		public static final String USER_REVOCATION = "998"; // 买家撤销
		public static final String AFTERSALE_CLOSED = "985"; // 售后关闭
		public static final String USER_DELIVERY = "993"; // 买家发货，待商家处理
		public static final String AFTERSALE_SUCCEED = "992"; // 售后成功
		public static final String WAIT_USER_RECEIPT = "991"; // 商家收货并发货，待买家收货
		public static final String MER_REFUSE_RECEIPT = "987"; // 商家拒绝收货，待买家撤销或修改
		public static final String AFTERSALE_OVERTIME_SUCCEED = "984"; // 售后超时成功
	}

	public static final class BANKCARDINFO {
		// 银行卡长度 至少16位
		public static final Integer ACCOUNTLENGTH = 16;
		// 默认银行卡
		public static final String Y_DEFAULT = "1";
		public static final String N_DEFAULT = "0";
		// 企业
		public static final String COMPANY = "1";
		// 个人
		public static final String ONESELF = "2";
	}

	// 优惠券
	public static final class Coupon {
		//优惠券范围 1全场通用 2指定商品 3指定分类
		public static final String ALLCOMMON = "1";
		public static final String SPECIFYGOODS = "2";
		public static final String SPECIFYCATEGORY = "3";
		//1 生效 2 失效 3 已结束  999删除
		public static final String STATUS = "1";
		public static final String INVALIDSTATUS = "2";
		public static final String STATUS_3 = "3";
	}

	// 优惠券 用户领取的优惠券
	public static final class UserCoupon {
		//（0：未使用；1：已使用;  2:已过期 3 失效）
		public static final String NOUSED = "1";
		public static final String ALREADYUSED = "2";
		public static final String ALREADYEXPIRED = "3";
		public static final String INVALIDSTATUS = "4";
	}
}
