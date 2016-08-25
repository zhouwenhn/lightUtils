# lightUtils
Android Toolkit Library:一组Android常用工具类（bitmap 处理，文件操作，加密存储器，网络监测等基础功能）
#类功能说明
#一.App相关:AppUtils
1.安装指定路径下的Apk installApp
2.卸载指定包名的App uninstallApp
3.获取当前App信息 getAppInfo
4.获取所有已安装App信息 getAllAppsInfo
5.根据包名判断App是否安装 isInstallApp
6.打开指定包名的App openAppByPackageName
7.打开指定包名的App应用信息界面 openAppInfo
8.可用来做App信息分享 shareAppInfo
9.判断当前App处于前台还是后台 isApplicationBackground
10.获取服务是否开启 isRunningService
#二.设备相关:DeviceUtils
1.获取设备MAC地址 getMacAddress
2.获取设备厂商，如Xiaomi getManufacturer
3.获取设备型号，如MI2SC getModel
4.获取设备SD卡是否可用 isSDCardEnable
5.获取设备SD卡路径 getSDCardPath
6.编码解码相关→EncodeUtils.java
#三.URL编码:urlEncode
1.URL解码 urlDecode
2.Base64编码 base64Encode
3.Base64解码 base64Decode
4.Base64URL安全编码 base64UrlSafeEncode
5.Html编码 htmlEncode
6.Html解码 htmlDecode
#四.加解密相关:EncryptUtils
1.MD5加密 getMD5 encryptMD5
2.获取文件的MD5校验码 getMD5File
3.SHA加密 getSHA encryptSHA
#五.键盘相关:KeyboardUtils
1.避免输入法面板遮挡
2.动态隐藏软键盘 hideSoftInput
3.点击屏幕空白区域隐藏软键盘clickBlankArea2HideSoftInput0
4.动态显示软键盘 showSoftInput
5.切换键盘显示与否状态 toggleSoftInput
#六.网络相关:NetworkUtils
1.打开网络设置界面 openWirelessSettings
2.判断网络是否可用 isAvailable
3.判断网络是否连接 isConnected
4.判断网络是否是4G is4G
5.判断wifi是否连接状态 isWifiConnected
6.获取移动网络运营商名称 getNetworkOperatorName
7.获取移动终端类型 getPhoneType
8.获取当前的网络类型(WIFI,2G,3G,4G) getNetWorkType getNetWorkTypeName
#七.手机相关:PhoneUtils
1.判断设备是否是手机 isPhone
2.获取手机的IMIE getDeviceIMEI
3.获取手机状态信息 getPhoneStatus
4.跳至填充好phoneNumber的拨号界面 dial
5.拨打phoneNumber call
6.发送短信 sendSms
7.获取手机联系人 getAllContactInfo
8.打开手机联系人界面点击联系人后便获取该号码 getContantNum
9.获取手机短信并保存到xml中 getAllSMS
#八.正则相关:RegularUtils
正则工具类
九.屏幕相关:ScreenUtils
1.获取手机分辨率 getDeviceWidth、getDeviceHeight
2.设置透明状态栏(api >= 19方可使用) setTransparentStatusBar
3.隐藏状态栏hideStatusBar
4.获取状态栏高度 getStatusBarHeight
5.判断状态栏是否存在 isStatusBarExists
6.获取ActionBar高度 getActionBarHeight
7.显示通知栏 showNotificationBar
8.隐藏通知栏 hideNotificationBar
9.设置屏幕为横屏setLandscape
10.获取屏幕截图 snapShotWithStatusBar、snapShotWithoutStatusBar
11.判断是否锁屏 isScreenLock
#十.Shell相关:ShellUtils
1.判断设备是否root isRoot
2.是否是在root下执行命令 execCmd
#十一.尺寸相关:SizeUtils
1.dp与px转换 dp2px、px2dp
2.sp与px转换 sp2px、px2sp
3.各种单位转换 applyDimension
4.在onCreate()即可强行获取View的尺寸 forceGetViewSize
5.ListView中提前测量View尺寸measureView
#十二.SP相关:SPUtils
1.SP中写入String类型value putString
2.SP中读取String getString
3.SP中写入int类型value putInt
4.SP中读取int getInt
5.SP中写入long类型value putLong
6.SP中读取long getLong
7.SP中写入float类型value putFloat
8.SP中读取float getFloat
9.SP中写入boolean类型value putBoolean
10.SP中读取boolean getBoolean
#十三.时间相关:TimeUtils
1.将时间戳转为时间字符串 milliseconds2String
2.将时间字符串转为时间戳 string2Milliseconds
3.将时间字符串转为Date类型 string2Date
4.将Date类型转为时间字符串 date2String
5.将Date类型转为时间戳 date2Milliseconds
6.将时间戳转为Date类型 milliseconds2Date
7.毫秒时间戳单位转换（单位：unit） milliseconds2Unit
8.获取两个时间差（单位：unit） getIntervalTime
9.获取当前时间 getCurTimeMills getCurTimeString getCurTimeDate
10.获取与当前时间的差（单位：unit） getIntervalByNow
11.判断闰年 isLeapYear
#十四.Logger:日志
1.Logger.i();
2.Logger.e();
3.Logger.w();
4.Logger.v();
#十五.ToastUtil:show toast
弹出toast
#十六.ScreenShot:截屏
