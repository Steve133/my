package com.cn.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cn.server.module.player.dao.PlayerDao;
import com.cn.server.module.player.dao.entity.Player;

/**
 * 启动函数
 * @author admin
 *
 */
public class ServerMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		PlayerDao dao = applicationContext.getBean(PlayerDao.class);
		Player playerByName = dao.getPlayerByName("admin");
		System.out.println(playerByName.toString());
		
		Player playerById = dao.getPlayerById(1L);
		System.out.println(playerById.toString());
		
		Player player = new Player();
		player.setPlayerName("aaa");
		player.setPassward("aaa");
		player.setLevel(0);
		player.setExp(0);
		Player createPlayer = dao.createPlayer(player);
		System.out.println(createPlayer.toString());
	}

}
