package com.cn.server.module.player.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cn.common.core.exception.ErrorCodeException;
import com.cn.common.core.model.ResultCode;
import com.cn.common.core.session.Session;
import com.cn.common.core.session.SessionManager;
import com.cn.common.module.player.response.PlayerResponse;
import com.cn.server.module.player.dao.PlayerDao;
import com.cn.server.module.player.dao.entity.Player;


/**
 * 玩家服务
 * 
 * @author -琴兽-
 * 
 */
@Component
public class PlayerServiceImpl implements PlayerService {
	private static Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

	@Autowired
	private PlayerDao playerDao;

	@Override
	public PlayerResponse registerAndLogin(Session session, Player player) {
		logger.info(" registerAndLogin {} :playerName"+player.toString());
		Player existplayer = playerDao.getPlayerByName(player.getPlayerName());

		// 玩家名已被占用
		if (existplayer != null) {
			logger.info("玩家名已被占用:"+existplayer.toString());
			throw new ErrorCodeException(ResultCode.PLAYER_EXIST);
		}

		// 创建新帐号
		player = playerDao.createPlayer(player);

		// 顺便登录
		return login(session, player);
	}

	@Override
	public PlayerResponse login(Session session, Player player) {
		logger.info(" login {} :playerName"+player.toString());
		
		// 判断当前会话是否已登录
		if (session.getAttachment() != null) {
			throw new ErrorCodeException(ResultCode.HAS_LOGIN);
		}

		// 玩家不存在
		Player findPlayer = playerDao.getPlayerByName(player.getPlayerName());
		if (player == null) {
			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
		}

		// 密码错误
		if (!findPlayer.getPassward().equals(player.getPassward())) {
			throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
		}

		// 判断是否在其他地方登录过
		boolean onlinePlayer = SessionManager.isOnlinePlayer(player.getPlayerId());
		if (onlinePlayer) {
			Session oldSession = SessionManager.removeSession(player.getPlayerId());
			oldSession.removeAttachment();
			// 踢下线
			oldSession.close();
		}

		// 加入在线玩家会话
		if (SessionManager.putSession(player.getPlayerId(), session)) {
			session.setAttachment(player);
		} else {
			throw new ErrorCodeException(ResultCode.LOGIN_FAIL);
		}

		// 创建Response传输对象返回
		PlayerResponse playerResponse = new PlayerResponse();
		playerResponse.setPlayerId(player.getPlayerId());
		playerResponse.setPlayerName(player.getPlayerName());
		playerResponse.setLevel(player.getLevel());
		playerResponse.setExp(player.getExp());
		return playerResponse;
	}

}
