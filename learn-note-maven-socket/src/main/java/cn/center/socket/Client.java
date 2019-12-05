package cn.center.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	private static Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		new Client().socketClient();
	}

	private void socketClient() {
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		Socket socket = null;
		try {
			// 创建Socket对象
			socket = new Socket("127.0.0.1", 10101);
			
			
			// 发送数据
			bos = new BufferedOutputStream(socket.getOutputStream());
			String message = "192.0.8.179         8083ocp-hgo-localproxy/rest?method=efuture.omp.event.coupongain.cpfcalc&ent_id=0                                                                                                                                      201       0019      4148                {\"bill_detail\":{\"billno\":\"201001980424214749\",\"channel\":\"pos\",\"market\":\"201\",\"term_no\":\"0019\",\"sale_date\":\"2018/04/24 21:52:15\",\"term_invoiceno\":576185,\"term_operator\":\"90001999\",\"invoice_type\":\"1\",\"consumers_id\":\"2007004597343\",\"consumers_type\":\"20\",\"consumers_cardno\":\"2007004597343\",\"ought_pay\":203.300000,\"sswr_overage\":0,\"fact_pay\":203.300000,\"change_pay\":0,\"codpay\":\"X\",\"sell_details\":[{\"rowno\":1,\"flag\":\"1\",\"mana_unit\":\"002\",\"market\":\"201\",\"itemcode\":\"117629\",\"itemname\":\"卡士原味鲜酪乳三联杯100g*3100g*3\",\"barcode\":\"6924810802248\",\"unitcode\":\"条\",\"factor\":1,\"gz\":\"20101\",\"brand\":\"9479\",\"category\":\"250100\",\"contract\":\"201\",\"qty\":1,\"price\":13,\"list_amount\":13,\"total_discount\":0,\"sale_amount\":13,\"original_billno\":\"\",\"original_rowno\":0},{\"rowno\":2,\"flag\":\"1\",\"mana_unit\":\"002\",\"market\":\"201\",\"itemcode\":\"209070\",\"itemname\":\"三全玉米马蹄猪肉水饺702g702g\\n\",\"barcode\":\"6908791102207\",\"unitcode\":\"袋\",\"factor\":1,\"gz\":\"20101\",\"brand\":\"0272\",\"category\":\"250550\",\"contract\":\"201\",\"qty\":1,\"price\":20.100000,\"list_amount\":20.100000,\"total_discount\":6.200000,\"sale_amount\":13.900000,\"original_billno\":\"\",\"original_rowno\":0,\"pop_details\":[{\"discount_amount\":6.200000,\"pop_mode\":\"0\",\"pop_policy_group\":\"GRANT\"}]},{\"rowno\":3,\"flag\":\"1\",\"mana_unit\":\"002\",\"market\":\"201\",\"itemcode\":\"239151\",\"itemname\":\"野森林精选绿豆500g500g\",\"barcode\":\"6928710601376\",\"unitcode\":\"袋\",\"factor\":1,\"gz\":\"20101\",\"brand\":\"06976\",\"category\":\"120261\",\"contract\":\"201\",\"qty\":1,\"price\":13.300000,\"list_amount\":13.300000,\"total_discount\":6.400000,\"sale_amount\":6.900000,\"original_billno\":\"\",\"original_rowno\":0,\"pop_details\":[{\"discount_amount\":6.400000,\"pop_mode\":\"0\",\"pop_policy_group\":\"GRANT\"}]},{\"rowno\":4,\"flag\":\"1\",\"mana_unit\":\"002\",\"market\":\"201\",\"itemcode\":\"294345\",\"itemname\":\"思念260克彩趣小小玉汤圆组合装（18袋）26\",\"barcode\":\"294345\",\"unitcode\":\"袋\",\"factor\":1,\"gz\":\"20101\",\"brand\":\"0111\",\"category\":\"250564\",\"contract\":\"201\",\"qty\":1,\"price\":19.800000,\"list_amount\":19.800000,\"total_discount\":6,\"sale_amount\":13.800000,\"original_billno\":\"\",\"original_rowno\":0,\"pop_details\":[{\"discount_amount\":6,\"pop_mode\":\"0\",\"pop_policy_group\":\"GRANT\"}]},{\"rowno\":5,\"flag\":\"1\",\"mana_unit\":\"002\",\"market\":\"201\",\"itemcode\":\"223483\",\"itemname\":\"金健原生态冷榨茶油500ml500ml\",\"barcode\":\"6921722714119\",\"unitcode\":\"瓶\",\"factor\":1,\"gz\":\"20101\",\"brand\":\"1972\",\"category\":\"120106\",\"contract\":\"201\",\"qty\":1,\"price\":108,\"list_amount\":108,\"total_discount\":0,\"sale_amount\":108,\"original_billno\":\"\",\"original_rowno\":0},{\"rowno\":6,\"isdzc\":\"Y\",\"flag\":\"4\",\"mana_unit\":\"002\",\"market\":\"201\",\"itemcode\":\"232522\",\"itemname\":\"通芯红莲（6294B）称重\",\"barcode\":\"2058540033406\",\"unitcode\":\"kg\",\"factor\":1,\"gz\":\"20101\",\"brand\":\"4481\",\"category\":\"260130\",\"contract\":\"201\",\"qty\":0.257700,\"price\":129.600000,\"list_amount\":33.400000,\"total_discount\":0,\"sale_amount\":33.400000,\"original_billno\":\"\",\"original_rowno\":0},{\"rowno\":7,\"flag\":\"1\",\"mana_unit\":\"002\",\"market\":\"201\",\"itemcode\":\"450748\",\"itemname\":\"裕湘香菇味挂面1000g\",\"barcode\":\"6918962004674\",\"unitcode\":\"包\",\"factor\":1,\"gz\":\"20101\",\"brand\":\"0771\",\"category\":\"120232\",\"contract\":\"201\",\"qty\":1,\"price\":13.900000,\"list_amount\":13.900000,\"total_discount\":0,\"sale_amount\":13.900000,\"original_billno\":\"\",\"original_rowno\":0},{\"rowno\":8,\"flag\":\"1\",\"mana_unit\":\"002\",\"market\":\"201\",\"itemcode\":\"470031\",\"itemname\":\"塑料袋68*(41+8.5*2)\",\"barcode\":\"470031\",\"unitcode\":\"个\",\"factor\":1,\"gz\":\"20101\",\"brand\":\"4481\",\"category\":\"330560\",\"contract\":\"201\",\"qty\":1,\"price\":0.400000,\"list_amount\":0.400000,\"total_discount\":0,\"sale_amount\":0.400000,\"original_billno\":\"\",\"original_rowno\":0}],\"sell_payments\":[{\"rowno\":1,\"flag\":\"2\",\"paytype\":\"3\",\"paycode\":\"0311\",\"payname\":\"银联接口\",\"payno\":\"6217002100001447648\",\"rate\":1,\"amount\":139,\"money\":139,\"ovzzzzzzzzzzzzzzerage\":0,\"rownoid\":\"\"}]},\"calc_mode\":\"0\",\"calc_billid\":\"\"}";
			byte[] bytes = message.getBytes();
			int off = 0;
			while (bytes.length - off > 1024) {
				bos.write(bytes, off, 1024);
				off += 1024;
			}
			bos.write(bytes, off, bytes.length - off);
			bos.flush();
			socket.shutdownOutput();//关闭输出流通道
			System.out.println("发送完毕");
			
			
			//接收数据
			bis = new BufferedInputStream(socket.getInputStream());
			
			byte[] b = new byte[1024];
			int len = 0;
			while ( true ) {
				System.out.println("客户端 输入流 阻塞");
				len = bis.read(b);
				System.out.println("读取长度："+len);
				if(len == -1) {
					break;
				}
				System.out.println(new String(b, 0, len));
			}
			bis.close();
			
			bos.close();//socket同时关闭
			socket.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
