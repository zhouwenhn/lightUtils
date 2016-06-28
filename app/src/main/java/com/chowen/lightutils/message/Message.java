package com.chowen.lightutils.message;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA. User: xiejm Date: 3/4/13 Time: 11:46 AM
 */
public class Message {
	public final static int PRIORITY_NORMAL = 1;
	public final static int PRIORITY_HIGH = 2;
	public final static int PRIORITY_EXTREMELY_HIGH = 3;

	private static ConcurrentLinkedQueue<Message> mCachedMessagePool = new ConcurrentLinkedQueue<Message>();
	private final static int MAX_CACHED_MESSAGE_OBJ = 15;

	public static enum Type {
		NONE,
		// this message is used to destroy the message pump,
		// we use the "Poison Pill Shutdown" approach, see:
		// http://stackoverflow.com/a/812362/668963
		DESTROY_MESSAGE_PUMP,

		NETWORK_STATE_CHANGED, // 网络状态变化

		RECEIVE_NEW_MESSAGE, // 收到聊天室推送消息

		SEND_CHAT_MSG_FAILED, // 消息发送失败
		SEND_CHAT_MSG_SUCCEED, // 消息发送成功
		SEND_CHAT_MSG_START_RESENDING, // 开始重发消息

		// RECOMMENDED_GAMES_LOADED, // 推荐游戏加载完成（用于加入战队和创建战队）

		JOIN_GUILD, // 加入战队
		QUIT_GUILD, // 退出战队
		QUIT_GUILD_PASSIVE, // 被动退出战队
		UPDATE_MESSAGE_UNREAD_COUNT, // 最新消息列表的消息的未读数置为0
		SET_MESSAGE_UNREAD_COUNT, // 未读消息数
		SET_OPACTIVITY_UNREAD_COUNT, // 未读运营活动数
		CHANGE_RECEIVE_NOTIFICATION_SETTING, // 修改是否接受新消息提醒
		FORCE_POLLING_STATUS_FINISHED, // 手动触发拉取最新个人状态信息结束
		REFRESH_MAIN_RECENT_MESSAGE_LIST, // 刷新最近消息列表
		RECEIVED_RECENT_MESSAGE, // 接收到最近消息（RecentMessage）
		ZERO_OUT_RECENT_MESSAGE_UNREAD_COUNT, // 把最近消息列表的某一个 item 的 unread count
												// 置为 0
		DELETE_RECENT_MESSAGE, // 删除消息列表的某一个 item

		DELETED_FRIEND, // 删除好友

		SESSION_TIMEOUT, // session过期了，用户需要重新登陆

		ENTER_EDIT_MODE, // 进入编辑模式
		EXIT_EDIT_MODE, // 退出编辑模式
		GIFT_PACKS_GAME_DETAIL_REFRESH, // 刷新游戏详情里面的礼包
		CHAT_GROUP_GAME_DETAIL_REFRESH, // 刷新游戏详情里面的聊天群
		GIFT_PACKS_GUILD_REFRESH, // 刷新战队里面的礼包

		PERSONAL_IMAGE_REFRESH, // 用户头像变更刷新
		PERSONAL_INFO_CHANGED, // 用户资料改变
		GUILD_LOGO_REFRESH,

		ON_CHAT_MESSAGE_LINK_CLICKED, // 聊天消息中的链接被点击了

		MESSAGE_JOIN_GUILD_SUCCESS, // 加入战队成功，进行页面跳转到战队详情页面
		MESSAGE_CREATE_GUILD_SUCCESS, // 创建战队成功，进行页面跳转到战队详情页面
		MESSAGE_REFRESH_GUILD_INFO, // 强制刷新战队详情页面
		MESSAGE_REFRESH_PERSONAL_ATTENTION, // 刷新我的关注列表界面
		MESSAGE_PERSONAL_ATTENTION_ACCESSED, // 当有我的关注游戏被访问
		MESSAGE_REFRESH_PERSONAL_GROUP, // 刷新我的群列表界面
		MESSAGE_REFRESH_CREATE_GROUP_SUCCESS, // 创建群成功
		MESSAGE_REFRESH_GIFT_HALL, // 刷新礼包大厅数据
		MESSAGE_REFRESH_GIFT_PACK_APPLY, // 刷新礼包申请页面

		ENTER_CHAT_GROUP_IN_WINDOW, // 在浮窗上进入聊天群

		QUIT_GROUP, // 退出聊天群
		DISSOLVE_GROUP, // 解散聊天群
		QUIT_GROUP_PASSIVE, // 被动退出聊天群

		REFRESH_FRIEND_LIST, // 刷新好友列表

		DEVICES_SCREEN_ON, //
		DEVICES_SCREEN_OFF, //
		DEVICES_SCREEN_UNLOCK, // 解锁

		RECEIVE_UNREAD_STATUS_MY_POST_UPDATE, // poll收到 我发表的 未读状态更新
		RECEIVE_UNREAD_STATUS_MY_TAKE_PART_UPDATE, // poll收到 我参与的 未读状态更新
		RECEIVE_UNREAD_STATUS_LIKED_ME_UPDATE, // poll收到 我赞我的 未读状态更新

		CLEAR_UNREAD_STATUS_STATE, // 清除未读状态消息的状态
		COIN_COUNT_CHANGED, // 铜币数量改变

		CHANGED_GROUP_NAME, // 成功修改群名称
		GROUP_INFO_CHANGED, // 群基本信息变更，参数是群ID

		CLEAR_CHAT_HISTORY, // 清空群/单聊，聊天记录
		SEND_CHAT_MESSAGE_FROM_PLACES_OTHER_THAN_CHAT_PAGE, // 从浮窗发出新消息要通知客户端的聊天界面
		POLL_BOOK_CODE_STATUS, // poll预号状态

		ON_SYSTEM_SNAP_SHOT_START, // 开始从系统截屏中加载图片

		ON_SYSTEM_SNAP_SHOT_FINISH, // 完成从系统截屏中加载图片

		GUILD_SLOGAN_UPDATED, // 编辑战队宣言成功
		GUILD_INTRODUCTION_UPDATED, // 编辑战队简介成功
		GUILD_REQUEST_REFRESH_INDEX_UI, // 请求刷新战队首页信息的UI，data参数为JsonObject，来自/ghcm/guild.guildBarIndex

		MESSAGE_GIFT_BOOK_LIST_CHANGED,

		GUILD_SETTLED_GAME_LIST_CHANGED, // 战队入驻游戏列表变更

		DOWNLOAD_SUCCESSFUL, // 下载器下载某个文件成功，data参数为Pair<下载地址(String),
								// 本地地址(String)>
		DOWNLOAD_FAIL, // 下载器下载某个文件失败，data参数为下载地址(String)

		GUILD_SHARE_ADDED, // 成功添加一个战队分享文件，data参数为ShareSpaceItem对象
		GUILD_SHARE_DELETED, // 成功删除一个战队分享文件，data参数为ShareSpaceItem对象
		MESSAGE_GROUP_MEMBER_CHANGED, // 群成员改变了，data参数为Bundle (group_id,
										// group_member_number)
		MESSAGE_ROOM_MEMBER_CHANGED, // 房间成员改变了，data参数为Bundle (room_id,
										// room_member_number)
		MESSAGE_FRIEND_LIST_CHANGED, // 好友列表改变了
		MESSAGE_RETRY_ERROR, // 重试错误
		MESSAGE_RETRY_TIME_OUT, // 重试超时
		MESSAGE_RETRY_SUCCESS, // 重试成功
		MESSAGE_RETRY_BIZ_ERROR, // 业务错误

		MESSAGE_GIFT_TRANSFER_SUCCESS,
		MESSAGE_DELETE_ALBUM_PICTURE,
		MESSAGE_ALBUM_PICTURES_CHANGED,
		MESSAGE_ALBUM_PICTURE_UPLOAD_COMPLETE,
		MESSAGE_FORCE_REFRESH_ALBUM_LIST,
		MESSAGE_TOPIC_POST_A_COMMENT,
		MESSAGE_TOPIC_POST,
		MESSAGE_TOPIC_POST_A_LIKE, // 帖子点赞

		GUILD_ORGANIZATION_CHANGED, // 战队组织架构改变

		RECEIVE_NEW_PLAYMATE_MESSAGE, // 推荐好友
		PLAYMATE_ADD_STATUS_CHANGED, // 合拍玩伴列表按钮点击状态

		NOTIFY_CHAT_PAGE_ITEM_DATA_CHANGED, // 更改群名片后，通知更新聊天Item

		MESSAGE_BECOME_FRIEND, // 成为好友的消息通知

		MESSAGE_RECORD_SUCCESS, // 录音完成
		MESSAGE_PLAYER_SHOW_DELETE, // 删除玩家秀

		MESSAGE_CLICK_CHAT_NUMBER_SEARCH,

		ROOM_CHAT_TYPE_CHANGED, ROOM_CHAT_DISMISS, // 房间解散
		ROOM_CHAT_OWNER_TRANSFER, ROOM_CHAT_ROLE_CHANGED, ROOM_CHAT_QUIT_PASSIVE, // 被动退出房间
		ROOM_CHAT_PASSWORD_CHANGED, ROOM_CHAT_LIVE_CHANGED, ROOM_CHAT_ACCESS_SUCCESS,

		CHAT_PIC_LIST_CHANGED, // 加载聊天图片（slide show）
		ROOM_CHAT_GAGGED_STATE_CHANGED, // 禁言状态改变

		TEAM_FOLLOW_SUCCESS, // 关注战队成功
		TEAM_CANCEL_FOLLOW_SUCCESS, // 取消关注战队
		ALBUM_PICTURE_PICK, // 相册选中图片
		TEAM_CHECK_IN_SUCCESS, // 签到成功
		RECEIVE_LIVE_MESSAGE, //收到直播// 消息
		UPDATE_LIVE_ROOM,  //更新直播室消息
		LOGIN_SUCCESS,  //登陆成功
        SEND_LIVE_CHAT_MSG_FAILED, // 消息发送失败
        SEND_LIVE_CHAT_MSG_SUCCEED, // 消息发送成功

		GROUP_GAGED_STATE//全员禁言开启

	}

	public Message(Type type, Object data, int priority, Object sender) {
		this.type = type;
		this.data = data;
		this.priority = priority;
		this.sender = sender;
	}

	public Message(Type type, Object data, int priority) {
		this(type, data, priority, null);
	}

	public Message(Type type, Object data) {
		this(type, data, PRIORITY_NORMAL, null);
	}

	public Message(Type type, int priority) {
		this(type, null, priority);
	}

	public void reset() {
		type = Type.NONE;
		data = null;
		priority = PRIORITY_NORMAL;
		sender = null;
	}

	public void recycle() {
		if (mCachedMessagePool.size() < MAX_CACHED_MESSAGE_OBJ) {
			reset();
			mCachedMessagePool.add(this);
		}
	}

	public static Message obtainMessage(Type messageType, Object data,
			int priority, Object sender) {
		Message message = mCachedMessagePool.poll();

		if (message != null) {
			message.type = messageType;
			message.data = data;
			message.priority = priority;
			message.sender = sender;

		} else {
			message = new Message(messageType, data, priority, sender);
		}

		return message;
	}

	public Type type;
	public Object data;
	public int priority;
	public Object sender;

	public int referenceCount;
}
