package redis;

import java.io.IOException;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig; 

public class JedisUtil {
	public static JedisPool pool;

	public static void main(String[] args) {
	 Jedis jedis = getPool().getResource();

//	 String info = jedis.info("Memory");
//	 System.out.println(info);
	 jedis.select(2);
//	 jedis.expire("a",20); 
//	 System.out.println(jedis.ttl("a"));
	}

	public static JedisPool getPool() {
		synchronized (JedisPool.class) {
			if(pool==null) {
				try {
					pool=jedisPool();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return pool;
		}
	}

	private  static  JedisPool jedisPool() throws IOException {
		Properties props = new Properties();

		props.load(JedisUtil.class.getClassLoader().getResourceAsStream("redis.properties"));

		// 创建jedis池配置实例

		JedisPoolConfig config = new JedisPoolConfig();

		// 设置池配置项值
		config.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxActive")));

		config.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));

		config.setMaxWaitMillis(Long.valueOf(props.getProperty("jedis.pool.maxWait")));

		config.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));

		config.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));

		// 根据配置实例化jedis池
		JedisPool pool = new JedisPool(config, props.getProperty("redis.ip"),
				Integer.valueOf(props.getProperty("redis.port")), 20000, "1");
		return pool;

	}
}
